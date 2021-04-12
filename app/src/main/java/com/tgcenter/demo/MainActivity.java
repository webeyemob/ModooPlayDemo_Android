package com.tgcenter.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nefarian.privacy.policy.IPrivacyPolicyCallback;
import com.nefarian.privacy.policy.PrivacyPolicyHelper;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.tgcenter.demo.ads.NetworkAdActivity;
import com.tgcenter.demo.anti_addiction.AntiAddictionActivity;
import com.tgcenter.unified.antiaddiction.api.AntiAddiction;
import com.tgcenter.unified.sdk.api.InitConfig;
import com.tgcenter.unified.sdk.api.TGCenter;
import com.tgcenter.unified.sdk.h.UdeskHelper;
import com.tgcenter.unified.sdk.h.WeChatHelper;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.core.LoginType;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    // 产品渠道
    public static final String Channle = "channel";

    private Button mDebugPageButton;
    private Button mWeChatButton;
    private Button mAdTestButton;
    private Button mUserAgreementButton;
    private Button mPrivacyPolicyButton;
    private Button mAntiAddictionButton;
    private Button mUdeskButton;
    private Button mLogoutButton;

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
        mWeChatButton = findViewById(R.id.button_wechat);
        mWeChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weChatLogin();
            }
        });

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

        mAntiAddictionButton = findViewById(R.id.button_anti_addiction);
        mAntiAddictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AntiAddictionActivity.class);
                startActivity(intent);
            }
        });

        mUdeskButton = findViewById(R.id.button_udesk);
        mUdeskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterUdesk();
            }
        });

        mLogoutButton = findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    // 进入客服页面
    private void enterUdesk() {
        UdeskHelper.enterUdesk(MainActivity.this);
    }

    // 微信登录
    private void weChatLogin() {
        // 设置微信登录回调
        WeChatHelper.setLoginCallback(new LoginCallback() {
            @Override
            public void loginSuccess(String code) {
                // 登录成功
                LogUtil.d(TAG, "loginSuccess, code: " + code);
            }

            @Override
            public void loginFailed(String result) {
                // 登录失败
                LogUtil.d(TAG, "loginFailed, result: " + result);
            }

            @Override
            public void loginCancel(String result) {
                // 取消登录
                LogUtil.d(TAG, "loginCancel, result: " + result);
            }
        });
        // 登录微信，参数固定为 LoginType.Wechat
        WeChatHelper.login(LoginType.Wechat);
    }

    // 清除 SDK 的所有数据，包括《用户协议与隐私政策》的授权状态、用户信息等
    private void logout() {
        TGCenter.clearCache(MainActivity.this);
        AntiAddiction.getInstance().logout();
    }
}