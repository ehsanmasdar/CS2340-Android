package com.ehsandev.cs2340.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Access {
    public static boolean isHigherThanUser(Context c){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
        String level = p.getString("level", null);
        if (level.equals("manager") || level.equals("worker") || level.equals("admin")){
            return true;
        }
        return false;
    }
    public static boolean isHigherThanWorker(Context c){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
        String level = p.getString("level", null);
        if (level.equals("manager") || level.equals("admin")){
            return true;
        }
        return false;
    }
    public static boolean isHigherThanManager(Context c){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
        String level = p.getString("level", null);
        if (level.equals("admin")){
            return true;
        }
        return false;
    }
    public static boolean isWorker(Context c){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
        String level = p.getString("level", null);
        if (level.equals("worker")){
            return true;
        }
        return false;
    }
    public static boolean isManager(Context c){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
        String level = p.getString("level", null);
        if (level.equals("manager")){
            return true;
        }
        return false;
    }
}
