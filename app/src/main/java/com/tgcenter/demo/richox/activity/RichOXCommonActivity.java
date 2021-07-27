package com.tgcenter.demo.richox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;

public class RichOXCommonActivity extends BaseActivity {
    private final String TAG = "FissionDemo";

    private TextView mUserActivity;
    private TextView mROXUserActivity;
    private TextView mTaskActivity;
    private TextView mWithdrawActivity;
    private TextView mDataActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_common);
        initView();
    }


    private void initView() {
        mUserActivity = findViewById(R.id.demo_activity_richox_common_user);
        mUserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RichOXCommonActivity.this, RichOXCommonUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mROXUserActivity = findViewById(R.id.demo_activity_richox_common_rox_user);
        mROXUserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RichOXCommonActivity.this, ROXUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mTaskActivity = findViewById(R.id.demo_activity_richox_common_task);
        mTaskActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RichOXCommonActivity.this, RichOXCommonTaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mWithdrawActivity = findViewById(R.id.demo_activity_richox_common_withdraw);
        mWithdrawActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RichOXCommonActivity.this, RichOXCommonWithdrawActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mDataActivity = findViewById(R.id.demo_activity_richox_common_data);
        mDataActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RichOXCommonActivity.this, RichOXCommonDataActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


}
