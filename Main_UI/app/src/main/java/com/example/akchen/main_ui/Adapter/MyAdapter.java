package com.example.akchen.main_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.akchen.main_ui.R;
import com.example.akchen.main_ui.others.utils.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 2016/7/19.
 */
public class MyAdapter extends ArrayAdapter<Plan>{

    private Context context;
    private List<Plan> list;
    private int resourceId;

    public MyAdapter(Context context,int resourceId,List<Plan> list)
    {
        super(context,resourceId,list);
        this.context=context;
        this.resourceId=resourceId;
        this.list=list;
    }

    public View getView(int possition, View convertView, ViewGroup parent)
    {
        Plan plan=getItem(possition);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView timeStart=(TextView)view.findViewById(R.id.timeStart);
        TextView planName=(TextView)view.findViewById(R.id.newPlanName);
        timeStart.setText(plan.getTimeStart());
        planName.setText(plan.getPlanName());
        return view;
    }

}
