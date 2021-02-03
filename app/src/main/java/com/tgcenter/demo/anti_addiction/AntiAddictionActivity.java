package com.tgcenter.demo.anti_addiction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.antiaddiction.sdk.AntiAddictionKit;
import com.taurusx.ads.core.api.utils.LogUtil;
import com.tgcenter.demo.BuildConfig;
import com.tgcenter.demo.R;

public class AntiAddictionActivity extends AppCompatActivity {

    private final String TAG = "AntiAddictionActivity";

    // 记录是否登录了
    private static boolean mHasLogin;

    private Button mLoginButton;
    private Button mLogoutButton;
    private Button mPayButton;
    private Button mChatButton;
    private Button mOpenRealNameButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anti_addiction);
        initView();

        // 为保证用户的时长统计准确，游戏需要在运行的主 Activity 的 onResume() 和 onStop() 的方法中调用如下接口
        // 请看 MainActivity 中的 onResume() 和 onStop() 代码

        // 功能开关配置（采用默认值可跳过）
        setFunctionConfig();
        // 功能参数配置（采用默认值可跳过）
        setCommonConfig();
        // 初始化
        initAntiAddiction();
    }

    // 功能开关配置（采用默认值可跳过）
    // 请根据实际的需求设置（默认全部开启）
    private void setFunctionConfig() {
        AntiAddictionKit.getFunctionConfig()
                .useSdkRealName(true) // 是否使用 SDK 实名认证功能
                .useSdkPaymentLimit(true) // 是否使用 SDK 付费限制
                .useSdkOnlineTimeLimit(true) // 是否使用 SDK 在线时长限制
                .showSwitchAccountButton(true); // 是否显示切换账号按钮，单机游戏无账号系统可设置为 NO 隐藏该按钮
    }

    // 功能参数配置（采用默认值可跳过）
    // 请根据实际的需求调整参数；请注意参数的单位：时间是秒，货币是分
    private void setCommonConfig() {
        AntiAddictionKit.getCommonConfig()
                .gusterTime(60 * 60) // 游客每日游戏时长，默认 1 小时，单位 秒
                .nightStrictStart(22 * 60 * 60) // 未成年宵禁起始时间，默认晚上 10 点，单位 秒
                .nightStrictEnd(8 * 60 * 60) // 未成年宵禁截止时间，默认次日 8 点， 单位 秒
                .childCommonTime(90 * 60) // 未成年人非节假日每日游戏时长，默认 1.5 小时，单位 秒
                .childHolidayTime(3 * 60 * 60) // 未成年人节假日每日游戏时长，默认 3 小时，单位 秒
                .teenPayLimit(50 * 100) // 未成年人（8-15岁）单次付费限额，默认 50 元，单位 分
                .teenMonthPayLimit(200 * 100) // 未成年人（8-15岁）每月付费限额，默认 200 元，单位 分
                .youngPayLimit(100 * 100)// 未成年人（16-17岁）单次付费限额，默认 100 元，单位 分
                .youngMonthPayLimit(400 * 100) // 未成年人（16-17岁）每月付费限额，默认 400 元， 单位 分
                .dialogBackground("#ffffff") // sdk 弹窗背景颜色
                .dialogContentTextColor("#999999") // sdk 弹框内容字体颜色
                .dialogTitleTextColor("#2b2b2b") // 弹框标题字体颜色
                .dialogButtonBackground("#000000") // 弹框按钮背景颜色
                .dialogButtonTextColor("#ffffff") // 弹框按钮字体颜色
                .dialogEditTextColor("#000000") // 弹框输入框字体颜色
                .popBackground("#cc000000") // 倒计时浮窗背景颜色
                .popTextColor("#ffffff") // 倒计时浮窗字体颜色
                .tipBackground("#ffffff") // 提示浮窗背景颜色
                .tipTextColor("#000000"); // 提示浮窗字体颜色
    }

    // 初始化 AntiAddiction
    private void initAntiAddiction() {
        AntiAddictionKit.init(this, new AntiAddictionKit.AntiAddictionCallback() {
            @Override
            public void onAntiAddictionResult(int resultCode, String msg) {
                switch (resultCode) {
                    case AntiAddictionKit.CALLBACK_CODE_TIME_LIMIT:
                        // 时间受限
                        // 未成年人或游客游戏时长已达限制，通知游戏
                        logAndToast("Time Limit");
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_AAK_WINDOW_SHOWN:
                        // 额外弹窗显示，包括时间受限等
                        // 当用户操作触发额外窗口显示时通知游戏
                        logAndToast("AAk Window Show");
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_AAK_WINDOW_DISMISS:
                        // 额外弹窗消失，包括时间受限等
                        // 额外窗口消失时通知游戏
                        logAndToast("AAk Window Dismiss");
                        break;

                    case AntiAddictionKit.CALLBACK_CODE_LOGIN_SUCCESS:
                        // 登录成功
                        // 当游戏调用 login 后，直接进入游戏或完成实名认证后触发
                        logAndToast("Login Success");
                        mHasLogin = true;
                        switchUI(true);
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_SWITCH_ACCOUNT:
                        // 登出、切换账号
                        // 当用户因防沉迷机制受限时，登录认证失败或选择切换账号时会触发
                        logAndToast("Switch Account");
                        mHasLogin = false;
                        switchUI(false);
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_USER_TYPE_CHANGED:
                        // 用户类型变更
                        // 完成实名认证会触发
                        logAndToast("User Type Changed");
                        break;

                    case AntiAddictionKit.CALLBACK_CODE_PAY_NO_LIMIT:
                        // 付费不受限
                        logAndToast("Pay No Limit");
                        // 可以执行付费逻辑，单位为分
                        dealPay(10 * 100);
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_PAY_LIMIT:
                        // 付费受限
                        // 包括游客未实名或付费额达到限制等
                        logAndToast("Pay Limit");
                        break;

                    case AntiAddictionKit.CALLBACK_CODE_CHAT_NO_LIMIT:
                        // 聊天无限制
                        // 用户已通过实名，可进行聊天
                        logAndToast("Chat No Limit");
                        gotoChatPage();
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_CHAT_LIMIT:
                        // 聊天限制
                        // 用户未通过实名，不可进行聊天
                        logAndToast("Chat Limit");
                        break;

                    case AntiAddictionKit.CALLBACK_CODE_REAL_NAME_SUCCESS:
                        // 实名成功
                        // 仅当主动调用 openRealName() 时，如果成功会触发
                        logAndToast("Real Name Success");
                        break;
                    case AntiAddictionKit.CALLBACK_CODE_REAL_NAME_FAIL:
                        // 实名失败
                        // 仅当主动调用 openRealName() 时，如果用户取消会触发
                        logAndToast("Real Name Fail");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void logAndToast(String text) {
        LogUtil.d(TAG, "onAntiAddictionResult: " + text);
        if (BuildConfig.DEBUG) {
            // 仅本地测试时弹出 toast，以了解 SDK 流程
            Toast.makeText(AntiAddictionActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    }

    // 登录
    private void login() {
        // userId 类型为字符串，用于表示用户唯一标识，除 null 和 "" 等特殊字符串外无其他限制
        // type 固定为 USER_TYPE_UNKNOWN
        AntiAddictionKit.login("userId", AntiAddictionKit.USER_TYPE_UNKNOWN);
    }

    // 登出
    private void logout() {
        // 当用户在游戏内点击登出或退出账号时调用该接口
        AntiAddictionKit.logout();
    }

    // 付费
    private void pay() {
        // 游戏在收到用户的付费请求后，调用 SDK 的对应接口来判断当前用户的付费行为是否被限制
        // 如果没有实名，会弹出实名认证界面
        // 参数表示付费的金额，单位为分
        AntiAddictionKit.checkPayLimit(10 * 100);
    }

    // 付费并更新用户状态
    private void dealPay(int num) {
        // 执行具体的付费流程
        // ...

        // 当用户完成付费行为时，游戏需要通知 SDK，更新用户状态
        // 参数为本次充值的金额，单位为分
        AntiAddictionKit.paySuccess(num);
    }

    // 聊天
    private void chat() {
        // 游戏在需要聊天时，调用 SDK 接口判断当前用户是否实名
        // 如果没有实名，会弹出实名认证界面
        AntiAddictionKit.checkChatLimit();
    }

    // 进入聊天页面
    private void gotoChatPage() {
        // ...
    }

    // 打开实名认证
    private void openRealName() {
        // 除了付费、聊天、时长限制时，有其他场景需要主动打开实名窗口，则可以通过该接口让用户进行实名，否则不需要调用该接口
        // 如果用户实名过了，调用 openRealName() 会直接回调 CALLBACK_CODE_REAL_NAME_SUCCESS
        AntiAddictionKit.openRealName();
    }

    private void initView() {
        mLoginButton = findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mLogoutButton = findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        mPayButton = findViewById(R.id.button_pay);
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        mChatButton = findViewById(R.id.button_chat);
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat();
            }
        });

        mOpenRealNameButton = findViewById(R.id.button_open_real_name);
        mOpenRealNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRealName();
            }
        });

        switchUI(mHasLogin);
    }

    private void switchUI(boolean hasLogin) {
        if (hasLogin) {
            mLoginButton.setEnabled(false);
            mLogoutButton.setEnabled(true);
            mPayButton.setEnabled(true);
            mChatButton.setEnabled(true);
            mOpenRealNameButton.setEnabled(true);
        } else {
            mLoginButton.setEnabled(true);
            mLogoutButton.setEnabled(false);
            mPayButton.setEnabled(false);
            mChatButton.setEnabled(false);
            mOpenRealNameButton.setEnabled(false);
        }
    }
}
