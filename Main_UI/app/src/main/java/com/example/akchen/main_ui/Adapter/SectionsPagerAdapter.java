package com.example.akchen.main_ui.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.akchen.main_ui.Fragment.MainUIFragment;
import com.example.akchen.main_ui.R;
import com.thinkpage.lib.api.TPCity;
import com.thinkpage.lib.api.TPListeners;
import com.thinkpage.lib.api.TPWeatherDaily;
import com.thinkpage.lib.api.TPWeatherManager;
import com.thinkpage.lib.api.TPWeatherNow;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AkChen on 2016/7/9 0009.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private static ArrayList<String> locationList=new ArrayList<>();
private static  TPWeatherManager weatherManager;
    private MainUIFragment currentFragment = null;

    public static ArrayList<String> getLocationList() {
        return locationList;
    }

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        //获取北京的天气
        weatherManager = TPWeatherManager.sharedWeatherManager();
        //使用心知天气官网获取的key和用户id初始化WeatherManager
        weatherManager.initWithKeyAndUserId("voxdfkclylzg3hjf", "U3D4EAF09E");


    }

    @Override
    public Fragment getItem(int position) {


        String location=locationList.get(position);
       final  MainUIFragment mainFragment = MainUIFragment.newInstance();
        mainFragment.setLocation(location);

        // 获取北京当前天气，使用简体中文、摄氏度
        weatherManager.getWeatherNow(new TPCity(location)
                , TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese
                , TPWeatherManager.TPTemperatureUnit.kCelsius
                , new TPListeners.TPWeatherNowListener() {
                    @Override
                    public void onTPWeatherNowAvailable(TPWeatherNow weatherNow, String errorInfo) {
                        if (weatherNow != null) {
                            //weatherNow 就是返回的当前天气信息
                            mainFragment.SetWeatherNow(weatherNow);
                            mainFragment.FreshFragment();
                        } else {

                        }
                    }
                });
        //未来3天
        weatherManager.getWeatherDailyArray(new TPCity(location),
                TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese,
                TPWeatherManager.TPTemperatureUnit.kCelsius,
                new Date(),
                3,
                new TPListeners.TPWeatherDailyListener() {
                    @Override
                    public void onTPWeatherDailyAvailable(TPWeatherDaily[] tpWeatherDailies, String s) {
                        if (tpWeatherDailies != null) {
                            //weatherNow 就是返回的当前天气信息
                            mainFragment.SetFutureWeather(tpWeatherDailies);
                            mainFragment.FreshFragment();
                        }
                    }
                }

        );
        return mainFragment;
    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentFragment =(MainUIFragment)object ;
        super.setPrimaryItem(container, position, object);
    }

    public MainUIFragment getCurrentFragment() {
        return currentFragment;
    }
}
