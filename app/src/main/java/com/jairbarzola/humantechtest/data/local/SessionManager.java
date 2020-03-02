package com.jairbarzola.humantechtest.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jairbarzola.humantechtest.data.entities.UserEntity;

public class SessionManager {
    private static final String PREFERENCE_NAME = "HumanTechTest";
    private static final int PRIVATE_MODE = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String IS_LOGIN = "user_login";
    private static final String USER_TOKEN = "user_token";
    private static final String USER_INFO = "user_info";


    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }


    public void openSession(String token, UserEntity userEntity) {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(USER_TOKEN, token);
        if(userEntity!=null){
            editor.putString(USER_INFO,new Gson().toJson(userEntity));
        }
        editor.apply();
    }


    public void closeSession() {
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(USER_TOKEN, null);
        editor.apply();
    }

    public UserEntity getUser(){
        return new Gson().fromJson(preferences.getString(USER_INFO,""),UserEntity.class);
    }


    public String getUserToken() {
        if (isLogin()) {
            return preferences.getString(USER_TOKEN, "");
        } else {
            return "";
        }
    }

    public boolean isLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }


}
