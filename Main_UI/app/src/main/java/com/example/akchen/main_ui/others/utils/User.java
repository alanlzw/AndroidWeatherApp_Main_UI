package com.example.akchen.main_ui.others.utils;

/**
 * Created by alan on 2016/7/12.
 */
public class User {
    private int id;
    private String userAccount;
    private String userPassword;

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }

    public String getUserAccount()
    {
        return userAccount;
    }
    public void setUserAccount(String userAccount)
    {
        this.userAccount=userAccount;
    }

    public String getUserPassword()
    {
        return userPassword;
    }
    public void setUserPassword(String userPassword)
    {
        this.userPassword=userPassword;
    }
}
