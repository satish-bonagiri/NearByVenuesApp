package com.satti.fs.android.nbv.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager.BadTokenException;

public class ProgressUtil {
    
	public interface DialogListener {
		void onButtonPressed();
	}
	
	private ProgressDialog sDialog;
	
	public void displayProgressDialog(Context context, final DialogListener listener,String message) {
		sDialog = new ProgressDialog(context){
		
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
					hideProgressDialog();
					listener.onButtonPressed();
					return true;
					
				}
				else{
					return super.onKeyDown(keyCode, event);
				}
			}
		};
		sDialog.setMessage(message);
		try {
			sDialog.show();
		} catch (BadTokenException e) {
			e.printStackTrace();
		}
	}
	
	public void hideProgressDialog() {
		if(sDialog != null && sDialog.isShowing()) {
			sDialog.dismiss();
			sDialog = null;
		}
	}
}