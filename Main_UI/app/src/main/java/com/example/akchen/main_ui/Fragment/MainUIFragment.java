package com.example.akchen.main_ui.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.akchen.main_ui.Activity.EditPlanActivity;
import com.example.akchen.main_ui.Activity.MainUIActivity;
import com.example.akchen.main_ui.Activity.PlanScheduleActivity;
import com.example.akchen.main_ui.Adapter.MyShowAdapter;
import com.example.akchen.main_ui.R;
import com.example.akchen.main_ui.others.utils.Plan;
import com.example.akchen.main_ui.others.utils.WeatherDB;
import com.thinkpage.lib.api.TPAirQuality;
import com.thinkpage.lib.api.TPWeatherDaily;
import com.thinkpage.lib.api.TPWeatherNow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainUIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//通过new Instance 然后 set一系列参数 然后 FreshFregment方法
public class MainUIFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String location;
    private TPWeatherNow mWeatherNow = null;           //今天的这个城市的天气
    private TPWeatherDaily[] mfutureWeathers = null;  //未来三天的天气 后面会根据这个现实版 长度一定要保证是3
    private TPAirQuality mAirQuaity = null; //天气质量
    private View mView = null;
    private List<Plan> dataList=new ArrayList<Plan>();
    private SwipeRefreshLayout fresher = MainUIActivity.getFresher();  //刷新控件
    private List<Plan> planList;
    private Plan selectedPlan;
    private WeatherDB weatherDB;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton addPlanButton = null;
    private Button calendarButton = null;

    public MainUIFragment() {


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void SetWeatherNow(TPWeatherNow m) {
        this.mWeatherNow = m;
    }

    public void SetAirQuality(TPAirQuality a) {
        this.mAirQuaity = a;
    }

    public void SetFutureWeather(TPWeatherDaily[] fut) {
        this.mfutureWeathers = fut;
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public void FreshFragment() {
        //........
        if (mWeatherNow == null)
            return;
        if (mfutureWeathers == null)
            return;
        if (mView == null)
            return;
        ((TextView) mView.findViewById(R.id.textView5)).setText("     "+MainUIActivity.getLocatinMap().get(location).toString());
        //显示日期
        Date time = mWeatherNow.lastUpdateDate;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(time);

        String strDate = "    "+dateString + "\r\n             " + getWeekOfDate(time);
        TextView txtDate = (TextView) mView.findViewById(R.id.id_weather_time);
        txtDate.setText(strDate);
        //显示天气icon
        ImageView weatherIconView = (ImageView) mView.findViewById(R.id.id_weather_icon);
        String code = mWeatherNow.code;
        String filename = "w" + code;
        // weatherIconView.setImageDrawable(getResources().getDrawable(R.drawable.w5));
        String pkgname = this.getActivity().getBaseContext().getPackageName();
        int id = getResources().getIdentifier(filename, "drawable", this.getActivity().getBaseContext().getPackageName());
        weatherIconView.setImageDrawable(getResources().getDrawable(id));
        //显示天气温度

        TextView minus = (TextView) mView.findViewById(R.id.id_tp_icon_0);
        ImageView tp_icon_1 = (ImageView) mView.findViewById(R.id.id_tp_icon_1);
        ImageView tp_icon_2 = (ImageView) mView.findViewById(R.id.id_tp_icon_2);
        int temp = mWeatherNow.temperature;
        if (temp >= 0)
            minus.setVisibility(View.INVISIBLE);
        else
            minus.setVisibility(View.VISIBLE);
        int ten = temp / 10;
        int ge = temp % 10;
        filename = "nw" + ten;
        id = getResources().getIdentifier(filename, "drawable", this.getActivity().getBaseContext().getPackageName());
        tp_icon_1.setImageDrawable(getResources().getDrawable(id));
        filename = "nw" + ge;
        id = getResources().getIdentifier(filename, "drawable", this.getActivity().getBaseContext().getPackageName());
        tp_icon_2.setImageDrawable(getResources().getDrawable(id));

        //显示天气字符串
        //.....
        String weather_txt = mWeatherNow.windDirection + mWeatherNow.windScale + "级" + mWeatherNow.windSpeed + "km/h 湿度:" + mWeatherNow.humidity;
        TextView weatherTxt = (TextView) mView.findViewById(R.id.id_weather_txt);
        //因为 API功能收费 所以直接就模拟了
        // weatherTxt.setText(weather_txt);

        //未来三天
        String day1 = "  " + getWeekOfDate(mfutureWeathers[0].date) + "\r\n     " + mfutureWeathers[0].textDay + "\r\n" + mfutureWeathers[0].lowTemperature + "℃/" + mfutureWeathers[0].highTemperature + "℃";
        String day2 = "  " + getWeekOfDate(mfutureWeathers[1].date) + "\r\n     " + mfutureWeathers[1].textDay + "\r\n" + mfutureWeathers[1].lowTemperature + "℃/" + mfutureWeathers[1].highTemperature + "℃";
        String day3 = "  " + getWeekOfDate(mfutureWeathers[2].date) + "\r\n     " + mfutureWeathers[2].textDay + "\r\n" + mfutureWeathers[2].lowTemperature + "℃/" + mfutureWeathers[2].highTemperature + "℃";
        TextView txtDay1 = (TextView) mView.findViewById(R.id.id_future_1);
        TextView txtDay2 = (TextView) mView.findViewById(R.id.id_future_2);
        TextView txtDay3 = (TextView) mView.findViewById(R.id.id_future_3);
        txtDay1.setText(day1);
        txtDay2.setText(day2);
        txtDay3.setText(day3);
        //显示记事内容
        final ListView listView = (ListView) mView.findViewById(R.id.id_list);
        planList=weatherDB.loadPlan(1);
        //用于显示那天干嘛 什么时候 String内省 自己构造一个ArrayList就行 然后自己在GetView里加监听器
        List<String> list = new ArrayList<String>();
        if(planList!=null)
        {
            list.clear();
            for(Plan plan:planList)
            {
                list.add(plan.getTimeStart()+"   "+plan.getPlanName());
            }
        }
        MyShowAdapter madapter = new MyShowAdapter(this.getActivity(), list);
        listView.setAdapter(madapter);

//        int totalHeight = 0;
//        for (int i = 0; i < madapter.getCount(); i++) {
//            View listItem = madapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }

       // ViewGroup.LayoutParams params = listView.getLayoutParams();
        //params.height = totalHeight + (listView.getDividerHeight() * (madapter.getCount()-1));
        //((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        //listView.setLayoutParams(params);

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                listView.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPlan=planList.get(i);
               Intent intent = new Intent(getActivity(),EditPlanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("plan",selectedPlan);
                intent.putExtras(bundle);
                intent.putExtra("CURRENT_LEAVE",1);
                getActivity().startActivity(intent);
            }
        });
     weatherIconView.setFocusable(true);
        weatherIconView.setFocusableInTouchMode(true);
        weatherIconView.requestFocus();

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainUIFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainUIFragment newInstance(String param1, String param2) {
        MainUIFragment fragment = new MainUIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the planschedulerow for this fragment
        View rt = inflater.inflate(R.layout.fragment_main_ui, container, false);

        weatherDB=WeatherDB.getInstance(getActivity());
        mView = rt;
        addPlanButton = (ImageButton) rt.findViewById(R.id.id_add_btn);
       // addPlanButton.setEnabled(false);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditPlanActivity.class);
                intent.putExtra("CURRENT_LEAVE",2);
                getActivity().startActivity(intent);

            }
        });
        calendarButton = (Button) rt.findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlanScheduleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        FreshFragment();
        ScrollView scrollView =( ScrollView) rt.findViewById(R.id.id_fragment_scollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        int scrollY=view.getScrollY();
                        if(scrollY==0) {
                            //顶部
                            fresher.setEnabled(true);
                        }
                        else
                        {
                            fresher.setEnabled(false);
                        }
                }

                return false;
            }
        });
        return rt;
    }

    public static MainUIFragment newInstance() {
        MainUIFragment fragment = new MainUIFragment();

        return fragment;
    }

    public String getWeatherInfo()
    {
        String weatherInfo="";
        weatherInfo=mWeatherNow.text+" "+mWeatherNow.temperature+" ℃";
        return weatherInfo;
    }

}
