package com.jairbarzola.humantechtest.presenters;

import android.content.Context;
import android.content.res.Resources;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.activities.HomeActivity;
import com.jairbarzola.humantechtest.contracts.HomeContract;
import com.jairbarzola.humantechtest.data.entities.UserEntity;
import com.jairbarzola.humantechtest.data.local.SessionManager;
import com.jairbarzola.humantechtest.data.remote.ServiceFactory;
import com.jairbarzola.humantechtest.data.remote.request.AuthenticationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private Context context;
    private HomeContract.View view;
    private Resources resources;
    private SessionManager sessionManager;

    public HomePresenter(HomeActivity view){
        this.context=view.getApplicationContext();
        this.view = view;
        this.sessionManager = new SessionManager(context);
        this.resources = view.getApplicationContext().getResources();

    }
    @Override
    public void logout() {
        view.setLoading(resources.getString(R.string.logging_out), true);

        AuthenticationRequest request = ServiceFactory.createService(AuthenticationRequest.class);
        Call<ResponseBody> call = request.logout(sessionManager.getUserToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    sessionManager.closeSession();
                    view.next();
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

    @Override
    public UserEntity getUser() {
        return sessionManager.getUser();
    }
}
