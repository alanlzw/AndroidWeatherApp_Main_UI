package com.example.akchen.main_ui.others.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.akchen.main_ui.others.CreateDBAndTable;
import com.example.akchen.main_ui.others.wheelview.StrericWheelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 2016/7/7.
 */
public class WeatherDB {
    public static final String DB_NAME = "Weather_DB";
    public static final int VERSION = 5;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;

    private WeatherDB(Context context) {
        CreateDBAndTable helper = new CreateDBAndTable(context, DB_NAME, null, VERSION);
        db = helper.getWritableDatabase();
    }
//初始化实例
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }
    //插入出行计划的实例
    public void savePlan(Plan plan)
    {
        if(plan!=null)
        {
            ContentValues values = new ContentValues();
            values.put("plan_name",plan.getPlanName());
            values.put("user_id",plan.getUserId());
            values.put("plan_content",plan.getPlanContent());
            values.put("time_start",plan.getTimeStart());
            values.put("time_end",plan.getTimeEnd());
            db.insert("Plan",null,values);
        }
    }

    //读取所有出行计划
    public List<Plan> loadPlan(int userId)
    {
        List<Plan> list=new ArrayList<Plan>();
        Cursor cursor=db.query("Plan",null,"user_id=?",new String[]{String.valueOf(userId)},null,null,null);
        if (cursor.moveToFirst())
        {
            do {
                Plan plan = new Plan();
                plan.setId(cursor.getInt(cursor.getColumnIndex("id")));
                plan.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                plan.setPlanName(cursor.getString(cursor.getColumnIndex("plan_name")));
                plan.setPlanContent(cursor.getString(cursor.getColumnIndex("plan_content")));
                plan.setTimeStart(cursor.getString(cursor.getColumnIndex("time_start")));
                plan.setTimeEnd(cursor.getString(cursor.getColumnIndex("time_end")));
                list.add(plan);
            }while (cursor.moveToNext());
        }
        if(cursor!=null)
        {
            cursor.close();
        }
        return list;
    }

    //用id查询出行计划
    public List<String> queryPlan(int id)
    {
        List<String> list=new ArrayList<String>();
        Cursor cursor=db.query("Plan",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst())
        {
            list.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));//0
            list.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("user_id"))));//1
            list.add(cursor.getString(cursor.getColumnIndex("plan_name")));
            list.add(cursor.getString(cursor.getColumnIndex("plan_content")));
            list.add(cursor.getString(cursor.getColumnIndex("time_start")));
            list.add(cursor.getString(cursor.getColumnIndex("time_end")));
        }
        if (cursor!=null)
        {
            cursor.close();
        }
        return list;
    }
    //删除记录
    public void delete(String table,int id)
    {
        int result = db.delete(table,"id=?",new String[]{String.valueOf(id)});
    }

    //查询每个用户的
    public List<User> queryUser(String account)
    {
        List<User> list=new ArrayList<User>();
        Cursor cursor = db.query("User",null,"user_account=?",new String[]{account},null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUserAccount(cursor.getString(cursor.getColumnIndex("user_account")));
                user.setUserPassword(cursor.getString(cursor.getColumnIndex("user_password")));
                list.add(user);
            }while (cursor.moveToNext());
        }
        if(cursor!=null)
        {
            cursor.close();
        }
        return list;
    }

    public void saveUser(User user)
    {
        if(user!=null)
        {
            ContentValues values=new ContentValues();
            values.put("user_account",user.getUserAccount());
            values.put("user_password",user.getUserPassword());
            db.insert("User",null,values);
        }
    }

    public List<Plan> queryFromTime(String time,int userId)
    {
        List<Plan> list =new ArrayList<Plan>();
        Cursor cursor = db.query("Plan",null,"time_start=? and user_id=?",new String[]{time,String.valueOf(userId)},null,null,null);
        if(cursor.moveToFirst())
        {
           do {
               Plan plan=new Plan();
               plan.setPlanContent(cursor.getString(cursor.getColumnIndex("plan_content")));
               plan.setTimeEnd(cursor.getString(cursor.getColumnIndex("time_end")));
               plan.setTimeStart(cursor.getString(cursor.getColumnIndex("time_start")));
               plan.setPlanName(cursor.getString(cursor.getColumnIndex("plan_name")));
               plan.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
               plan.setId(cursor.getInt(cursor.getColumnIndex("id")));
               list.add(plan);
           }while (cursor.moveToNext());

        }
        if(cursor!=null)
        {
            cursor.close();
        }
        return list;
    }

    public void update(Plan plan)
    {
        ContentValues values =new ContentValues();
        values.put("time_start",plan.getTimeStart());
        values.put("time_end",plan.getTimeEnd());
        values.put("plan_content",plan.getPlanContent());
        values.put("plan_name",plan.getPlanName());
        db.update("Plan",values,"id=?",new String[]{String.valueOf(plan.getId())});
    }
}
