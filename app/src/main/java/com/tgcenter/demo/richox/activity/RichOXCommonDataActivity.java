package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.base.RichOXCommon;
import com.richox.base.RichOXData;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

public class RichOXCommonDataActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;
    // data
    private static TextView mSaveDataButton;
    private static TextView mQueryDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_common_data);
        initView();
    }

    private void initView() {
        mSaveDataButton = findViewById(R.id.richox_demo_data_save_user_data);
        mSaveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonDataActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXData.startStoreUserKV2Data("grade", "777", new CommonCallback<Boolean>() {

                    @Override
                    public void onSuccess(Boolean response) {
                        Log.d(TAG, "the response is :" + response);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });

            }
        });

        mQueryDataButton = findViewById(R.id.richox_demo_data_query_user_data);
        mQueryDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonDataActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXData.startGetUserKVData2("grade", new CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "the response is :" + s);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });
    }

}
