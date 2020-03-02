package com.jairbarzola.humantechtest.presenters;

import android.content.Context;
import android.content.res.Resources;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.activities.LoginActivity;
import com.jairbarzola.humantechtest.contracts.LoginContract;
import com.jairbarzola.humantechtest.data.entities.UserEntity;
import com.jairbarzola.humantechtest.data.local.SessionManager;
import com.jairbarzola.humantechtest.data.remote.ServiceFactory;
import com.jairbarzola.humantechtest.data.remote.request.AuthenticationRequest;
import com.jairbarzola.humantechtest.utils.HeadersUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private Context context;
    private LoginContract.View view;
    private Resources resources;
    private SessionManager sessionManager;

    public LoginPresenter(LoginActivity view){
        this.context=view.getApplicationContext();
        this.view = view;
        this.sessionManager = new SessionManager(context);
        this.resources = view.getApplicationContext().getResources();

    }

    @Override
    public void login(String email, String password) {
        view.setLoading(resources.getString(R.string.signing_in), true);
        HashMap<String, Object> body = new HashMap<>();
        body.put("login", email);
        body.put("password", password);

        AuthenticationRequest request = ServiceFactory.createService(AuthenticationRequest.class);
        Call<ResponseBody> call = request.login(HeadersUtils.generateHeadersWithOutToken(), body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseStr);
                        UserEntity userEntity = new UserEntity(jsonObject.getString("email"),jsonObject.getString("name"));
                        sessionManager.openSession(jsonObject.getString("user-token"),userEntity);
                        view.next();
                    } catch (IOException e) {
                        view.showModalMessage(e.getMessage(),2);
                    } catch (JSONException e) {
                        view.showModalMessage(e.getMessage(),2);
                    }

                } else if(response.code()==500) {
                    view.showModalMessage(resources.getString(R.string.failure_retrofit),2);
                }else{
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject( errorBody);
                        view.showModalMessage(jsonObject.getString("message"),2);
                    } catch (IOException e) {
                        view.showModalMessage(e.getMessage(),2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                view.setLoading("", false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showModalMessage(resources.getString(R.string.failure_retrofit),2);
                view.setLoading("", false);
            }
        });
    }
}
