package com.jairbarzola.humantechtest.data.remote.request;

import com.jairbarzola.humantechtest.data.remote.Constants;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AuthenticationRequest {


    @POST(Constants.USER_LOGIN)
    Call<ResponseBody> login(@HeaderMap HashMap<String,String > headers, @Body HashMap<String,Object> body);

    @POST(Constants.USER_REGISTER)
    Call<ResponseBody> registerUser(@HeaderMap HashMap<String,String > headers, @Body HashMap<String,Object> body);

    @GET(Constants.USER_LOGOUT)
    Call<ResponseBody> logout(@Header("user-token") String token);
}
