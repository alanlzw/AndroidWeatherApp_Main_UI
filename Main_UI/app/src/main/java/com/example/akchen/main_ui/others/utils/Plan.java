package com.example.akchen.main_ui.others.utils;

import java.io.Serializable;

/**
 * Created by alan on 2016/7/10.
 */
public class Plan implements Serializable {

    private int id;
    private String planName;
    private String planContent;
    private String timeStart;
    private String timeEnd;
    private int userId;

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public String getPlanName()
    {
        return planName;
    }
    public void setPlanName(String planName)
    {
        this.planName=planName;
    }
    public String getPlanContent()
    {
        return planContent;
    }
    public void setPlanContent(String planContent)
    {
        this.planContent=planContent;
    }

    public String getTimeStart()
    {
        return timeStart;
    }
    public void setTimeStart(String timeStart)
    {
        this.timeStart=timeStart;
    }

    public String getTimeEnd()
    {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd)
    {
        this.timeEnd=timeEnd;
    }

    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId=userId;
    }
}
