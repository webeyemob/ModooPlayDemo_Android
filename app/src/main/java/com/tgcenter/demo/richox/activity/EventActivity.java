package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.base.RichOX;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.util.ToastUtil;

public class EventActivity extends BaseActivity {
    private EditText mEventValue;
    private EditText mEventName;
    private TextView mSubmitWithValue;
    private TextView mSubmitNoValue;

    private EditText mQueryEventName;
    private TextView mQueryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_event);
        initView();
    }


    private void initView() {
        mEventName = (EditText) findViewById(R.id.event_name);
        mEventValue = (EditText) findViewById(R.id.event_value);

        mSubmitWithValue = (TextView) findViewById(R.id.submit_with_value);
        mSubmitWithValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = mEventName.getText().toString();
                String eventValue = mEventValue.getText().toString();
                if (TextUtils.isEmpty(eventName) || TextUtils.isEmpty(eventValue)) {
                    ToastUtil.showToast(EventActivity.this, "名称和属性不能为空");
                } else {
                    RichOX.reportAppEvent(eventName, eventValue);
                }
            }
        });
        mSubmitNoValue = (TextView) findViewById(R.id.submit_no_value);
        mSubmitNoValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = mEventName.getText().toString();
                if (TextUtils.isEmpty(eventName)) {
                    ToastUtil.showToast(EventActivity.this, "名称不能为空");
                } else {
                    RichOX.reportAppEvent(eventName);
                }

            }
        });

        mQueryEventName = findViewById(R.id.query_event_value_key);
        mQueryButton = findViewById(R.id.query_event_value);
        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mQueryEventName.getText().toString();
                RichOX.queryEventValue(key, new CommonCallback<String>() {
                    @Override
                    public void onSuccess(String value) {
                        ToastUtil.showToast(EventActivity.this, "Value: " + value);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtil.showToast(EventActivity.this, "code: " + code + " msg: " + msg);
                    }
                });
            }
        });
    }


}
