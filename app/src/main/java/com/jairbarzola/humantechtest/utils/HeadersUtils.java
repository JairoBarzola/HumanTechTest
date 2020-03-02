package com.jairbarzola.humantechtest.utils;

import android.content.Context;

import com.jairbarzola.humantechtest.data.local.SessionManager;
import com.jairbarzola.humantechtest.data.remote.Constants;

import java.util.HashMap;

public class HeadersUtils {


    public static HashMap<String, String> generateHeadersWithOutToken() {
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Content-type", Constants.CONTENT_TYPE_JSON);
        headers.put("Accept", Constants.CONTENT_TYPE_JSON);
        headers.put("app-version", "");
        headers.put("app-name", "");
        headers.put("app-platform", "android");

        return headers;
    }

    public static HashMap<String, String> generateHeadersWithToken(Context context) {
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization", new SessionManager(context).getUserToken());
        headers.put("Content-type", Constants.CONTENT_TYPE_JSON);
        headers.put("Accept", Constants.CONTENT_TYPE_JSON);
        headers.put("app-version", "");
        headers.put("app-name", "");
        headers.put("app-platform", "android");

        return headers;
    }

}
