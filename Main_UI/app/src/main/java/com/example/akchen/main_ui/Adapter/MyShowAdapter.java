package com.example.akchen.main_ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.akchen.main_ui.R;

import java.util.List;

/**
 * Created by AkChen on 2016/7/10 0010.
 */
public class MyShowAdapter extends BaseAdapter {

    private Context context;
    private List list;
    public MyShowAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.adaper_line, null);
            final TextView edit = (TextView) convertView.findViewById(R.id.id_list_item);
            edit.setText((String)list.get(position));
        }

        return convertView;
    }

}
