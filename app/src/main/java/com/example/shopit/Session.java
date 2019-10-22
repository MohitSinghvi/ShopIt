package com.example.shopit;

import android.content.Context;
import android.content.SharedPreferences;
//import android.preference.PreferenceManager;

public class Session {


//    private SharedPreferences prefs;

    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";


//    public Session(Context cntx) {
//
//        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
////        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//
//    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getusername(Context context) {
        return getPrefs(context).getString("username", null);
    }
//    public static String getuserId(Context context) {
//        return getPrefs(context).getString("user_id",null);
//    }


    public static void setuser(Context context, String username, String email) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("username", username);
//        editor.putInt("user_id",user_id);
        editor.putString("email", email);
        editor.commit();
    }


//    public void setuser(String email,String name) {
//        prefs.edit().putString("email", email).apply();
//
//        prefs.edit().putString("name", name).apply();
//    }

//    public String getusername() {
//        String name = prefs.getString("name","");
//        return name;
//    }
//
//    public String getemail(){
//        String email = prefs.getString("email","");
//        return email;
//
//    }
    public static void endsession(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();


    }





}
