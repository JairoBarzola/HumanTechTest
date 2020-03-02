package com.jairbarzola.humantechtest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.contracts.LoginContract;
import com.jairbarzola.humantechtest.dialogs.LoadingDialog;
import com.jairbarzola.humantechtest.dialogs.MessageDialog;
import com.jairbarzola.humantechtest.presenters.LoginPresenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity  implements Validator.ValidationListener, LoginContract.View {


    @Email(message = "Email incorrecto")
    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @Password(min = 6, message = "Ingrese minimo 6 caracteres")
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.btnRegisterUser)
    Button btnRegisterUser;


    Validator validator;
    LoadingDialog loadingDialog;
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initInstances();
        initEvents();
    }

    private void initEvents() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(LoginActivity.this, null, RegisterUserActivity.class, false);
            }
        });
    }

    private void initInstances() {
        presenter = new LoginPresenter(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public void setLoading(String message, boolean active) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(LoginActivity.this, message);
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
        nextActivity(LoginActivity.this, null, HomeActivity.class, true);
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

    @Override
    public void onValidationSucceeded() {
        presenter.login(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
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
