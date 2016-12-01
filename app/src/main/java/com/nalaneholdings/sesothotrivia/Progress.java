package com.nalaneholdings.sesothotrivia;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class Progress {

    protected final Context context;
    @VisibleForTesting
    private ProgressDialog mProgressDialog;
    
    public Progress(Context context){
        this.context = context;
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
