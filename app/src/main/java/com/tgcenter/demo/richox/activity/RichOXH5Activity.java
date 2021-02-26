package com.tgcenter.demo.richox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.richox.sdk.RichOX;
import com.richox.sdk.core.InfoUpdateCallback;
import com.richox.sdk.core.WeChatRegisterCallback;
import com.richox.sdk.core.WeChatResultCallback;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.we.modoo.ModooHelper;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.core.LoginType;

public class RichOXH5Activity extends BaseActivity implements View.OnClickListener {

    private String mUserId;
    private String mDeviceId;
    private WeChatResultCallback mWeChatResultCallback;

    private TextView mFloatView;
    private TextView mDialogView;
    private TextView mEnterView;
    private TextView mNativeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5);
        initRox();
        initView();
    }

    private void initRox() {
        RichOX.init(getApplicationContext());

        RichOX.registerWeChatCallback(new WeChatRegisterCallback() {
            @Override
            public void registerWeChat(WeChatResultCallback callback) {
                loginWeChat(callback);
            }
        });

        RichOX.registerInfoUpdateCallback(new InfoUpdateCallback() {
            @Override
            public void updateInfo(int i, String s, int i1) {
            }
        });
    }

    private void loginWeChat(WeChatResultCallback callback) {
        mWeChatResultCallback = callback;
        ModooHelper.setLoginCallback(new LoginCallback() {
            @Override
            public void loginSuccess(String info) {
                mWeChatResultCallback.onResult(true, "绑定成功");
            }

            @Override
            public void loginCancel(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                mWeChatResultCallback.onResult(false, "绑定取消");
            }

            @Override
            public void loginFailed(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                mWeChatResultCallback.onResult(false, result);
            }
        });
        ModooHelper.login(LoginType.Wechat);
    }


    private void initView() {
        mFloatView = findViewById(R.id.richox_demo_float);
        mFloatView.setOnClickListener(this);
        mDialogView = findViewById(R.id.richox_demo_dialog);
        mDialogView.setOnClickListener(this);
        mEnterView = findViewById(R.id.richox_demo_native);
        mEnterView.setOnClickListener(this);
        mNativeView = findViewById(R.id.richox_demo_entry);
        mNativeView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.richox_demo_float:
                intent.setClass(RichOXH5Activity.this, RichOXH5FloatActivity.class);
                break;
            case R.id.richox_demo_dialog:
                intent.setClass(RichOXH5Activity.this, RichOXH5DialogActivity.class);
                break;
            case R.id.richox_demo_native:
                intent.setClass(RichOXH5Activity.this, RichOXH5NativeActivity.class);
                break;
            case R.id.richox_demo_entry:
                intent.setClass(RichOXH5Activity.this, RichOXH5EntryActivity.class);
                break;
            default:
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
