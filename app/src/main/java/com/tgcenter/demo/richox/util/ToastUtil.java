package com.tgcenter.demo.richox.util;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
