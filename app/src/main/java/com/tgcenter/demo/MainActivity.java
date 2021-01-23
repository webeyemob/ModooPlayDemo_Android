package com.tgcenter.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nefarian.privacy.policy.IPrivacyPolicyCallback;
import com.nefarian.privacy.policy.PrivacyPolicyHelper;
import com.tgcenter.demo.ads.NetworkAdActivity;
import com.tgcenter.unified.sdk.api.Day1Retention;
import com.tgcenter.unified.sdk.api.InitConfig;
import com.tgcenter.unified.sdk.api.TGCenter;

public class MainActivity extends AppCompatActivity {

    // 产品渠道
    public static final String Channle = "channel";

    private Button mDebugPageButton;
    private Button mAdTestButton;
    private Button mUserAgreementButton;
    private Button mPrivacyPolicyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 检查用户是否同意了《用户协议和隐私政策》，如果同意则直接初始化，否则需要弹窗征得用户同意
        if (TGCenter.isUserAgreePolicy(this)) {
            // 用户已同意，初始化
            initModooPlay();
        } else {
            // 用户未同意
            // 展示默认的对话框
            showDefaultPolicyDialog();
            // 或者：展示 App 根据产品风格自定义的对话框
            // showCustomPolicyDialog();
        }
    }

    /**
     * 初始化 ModooPlay，必须在用户同意《用户协议和隐私政策》之后才可以调用。
     * init 方法内部初始化 Umeng、AppsFlyer、RangersAppLog、TaurusX，
     * 初始化的参数会从 ModooPlay 后台获取。
     * 如果应用之前有初始化上述 SDK 的逻辑，请先移除，统一由 ModooPlay 初始化。
     */
    private void initModooPlay() {
        TGCenter.init(MainActivity.this,
                InitConfig.newBuilder()
                        .setDebugMode(BuildConfig.DEBUG)
                        .setChannel(Channle)
                        .build());
    }

    // 展示默认的《用户协议和隐私政策》对话框
    private void showDefaultPolicyDialog() {
        PrivacyPolicyHelper policyHelper = new PrivacyPolicyHelper.Builder(this)
                .callback(new IPrivacyPolicyCallback() {
                    @Override
                    public void onUserAgree() {
                        dealDialogAgreeResult(true);
                    }

                    @Override
                    public void onUserDisagree() {
                        dealDialogAgreeResult(false);
                    }
                }).build();
        policyHelper.showDialog();
    }

    // App 也可以自定义《用户协议和隐私政策》对话框
    private void showCustomPolicyDialog() {
        final CustomPrivacyDialog dialog = new CustomPrivacyDialog(this);
        dialog.setAgreeCallback(new IPrivacyPolicyCallback() {
            @Override
            public void onUserAgree() {
                dealDialogAgreeResult(true);
            }

            @Override
            public void onUserDisagree() {
                dealDialogAgreeResult(false);
            }
        });

        PrivacyPolicyHelper policyHelper = new PrivacyPolicyHelper.Builder(this).dialog(dialog).build();
        policyHelper.showDialog();
    }

    /**
     * 处理用户点击对话框按钮的结果。
     * 用户同意，初始化；用户不同意，进行提示。
     */
    private void dealDialogAgreeResult(boolean agree) {
        if (agree) {
            initModooPlay();
        } else {
            Toast.makeText(MainActivity.this, "您需要阅读并同意后才可以使用本应用", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mAdTestButton = findViewById(R.id.button_ad_test);
        mAdTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NetworkAdActivity.class);
                startActivity(intent);
            }
        });

        mDebugPageButton = findViewById(R.id.button_debug_page);
        mDebugPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TGCenter.showDebugPage(MainActivity.this);
            }
        });

        mUserAgreementButton = findViewById(R.id.button_user_agreement);
        mUserAgreementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrivacyPolicyHelper.Builder(MainActivity.this).build().jumpToUserAgreement();
            }
        });

        mPrivacyPolicyButton = findViewById(R.id.button_privacy_policy);
        mPrivacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrivacyPolicyHelper.Builder(MainActivity.this).build().jumpToPrivacyPolicy();
            }
        });
    }
}