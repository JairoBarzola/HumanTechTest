package com.jairbarzola.humantechtest.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

import com.jairbarzola.humantechtest.R;

public class LoadingDialog extends ProgressDialog {
    public LoadingDialog(Context context, String text) {
        super(context);
        setIndeterminate(true);
        setMessage(text);
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setCancelable(false);
        setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.circle_progress));
    }
}
