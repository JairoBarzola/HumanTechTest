package com.jairbarzola.humantechtest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.contracts.RegisterUserContract;
import com.jairbarzola.humantechtest.dialogs.LoadingDialog;
import com.jairbarzola.humantechtest.dialogs.MessageDialog;
import com.jairbarzola.humantechtest.presenters.RegisterUserPresenter;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterUserActivity extends AppCompatActivity implements Validator.ValidationListener,  RegisterUserContract.View {

    @BindView(R.id.imBack)
    ImageView imBack;

    @BindView(R.id.btnAccept)
    Button btnAccept;

    @NotEmpty(message = "Ingresar nombre")
    @BindView(R.id.edtName)
    EditText edtName;

    @Password(min = 6, message = "Ingrese minimo 6 caracteres")
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @Email(message = "Email incorrecto")
    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @ConfirmPassword(message = "Contraseña no coincide")
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;


    Validator validator;

    LoadingDialog loadingDialog;
    RegisterUserContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);
        iniInstances();
        initEvents();
    }

    private void initEvents() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    private void iniInstances() {
        presenter = new RegisterUserPresenter(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void setLoading(String message, boolean active) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(RegisterUserActivity.this, message);
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
        nextActivity(RegisterUserActivity.this, null, LoginActivity.class, true);
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

    @Override
    public void onValidationSucceeded() {
        presenter.registerUser(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim(),edtName.getText().toString().trim());
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
}
