package com.jairbarzola.humantechtest.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.jairbarzola.humantechtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDialog extends DialogFragment {

    public static MessageDialog newInstance() {
        return new MessageDialog();
    }


    @BindView(R.id.txtMessage)
    TextView txtMessage;
    @BindView(R.id.btnAccept)
    Button btnAccept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogTheme);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_message, container, false);
        ButterKnife.bind(this,v);

        initViews();
        initEvents();

        return v;
    }

    private void initViews() {
        txtMessage.setText(getArguments().getString("message"));
    }

    private void initEvents() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
