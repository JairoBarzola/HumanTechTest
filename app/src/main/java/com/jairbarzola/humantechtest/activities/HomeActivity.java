package com.jairbarzola.humantechtest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.contracts.HomeContract;
import com.jairbarzola.humantechtest.data.entities.UserEntity;
import com.jairbarzola.humantechtest.dialogs.LoadingDialog;
import com.jairbarzola.humantechtest.dialogs.MessageDialog;
import com.jairbarzola.humantechtest.presenters.HomePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    LoadingDialog loadingDialog;
    HomeContract.Presenter presenter;
    UserEntity _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initInstances();
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logout();
            }
        });
    }

    private void initViews() {
        txtMessage.setText("¡Bienvenido! \n"+_user.getName()+" a HumanTechTest App");
    }

    private void initInstances() {
        presenter = new HomePresenter(this);
        _user = presenter.getUser();
    }

    @Override
    public void setLoading(String message, boolean active) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(HomeActivity.this, message);
        }
        if (active && loadingDialog != null) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void next() {
        nextActivity(HomeActivity.this, null, LoginActivity.class, true);
    }

    @Override
    public void showModalMessage(String message, int flag) {
        MessageDialog messageDialog = MessageDialog.newInstance();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putInt("flag", flag);
        messageDialog.setArguments(args);
        messageDialog.show(getSupportFragmentManager(), "MessageDialog");
    }

    private void nextActivity(Activity context, Bundle bundle, Class<?> activity, boolean destroy) {
        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (destroy) context.finish();
    }
}
