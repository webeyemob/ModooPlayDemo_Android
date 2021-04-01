package com.tgcenter.demo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {

    public static void toast(final Context context, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void toast(final Context context, final int textId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), textId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void toastLong(final Context context, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void toastLong(final Context context, final int textId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), textId, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void runOnUiThread(Runnable action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            new Handler(Looper.getMainLooper()).post(action);
        }
    }
}