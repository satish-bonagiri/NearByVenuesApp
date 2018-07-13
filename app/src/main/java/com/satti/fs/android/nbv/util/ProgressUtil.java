package com.satti.fs.android.nbv.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager.BadTokenException;

public class ProgressUtil {

    private ProgressDialog progressDialog;

    public void displayProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        try {
            progressDialog.show();
        } catch (BadTokenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (BadTokenException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog = null;
            }
        }
    }
}