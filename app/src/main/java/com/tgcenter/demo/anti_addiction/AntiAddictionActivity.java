package com.tgcenter.demo.anti_addiction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tgcenter.demo.R;
import com.tgcenter.demo.util.ToastUtil;
import com.tgcenter.unified.antiaddiction.api.AntiAddiction;
import com.tgcenter.unified.antiaddiction.api.realname.RealNameCallback;
import com.tgcenter.unified.antiaddiction.api.timelimit.TimeLimit;
import com.tgcenter.unified.antiaddiction.api.timelimit.TimeLimitCallback;
import com.tgcenter.unified.antiaddiction.api.user.RealNameResult;
import com.tgcenter.unified.antiaddiction.api.user.User;

public class AntiAddictionActivity extends AppCompatActivity {

    private final String TAG = "AntiAddictionActivity";

    private TextView mUserTextView;

    private Button mRealNameButton;
    private Button mRealNameCustomUIButton;
    private Button mUpdateUserButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anti_addiction);
        initView();

        // 设置事件限制
        customTimeLimit();
    }

    private void toast(String msg) {
        ToastUtil.toast(this, msg);
    }

    private void initView() {
        mUserTextView = findViewById(R.id.textView_user);

        mRealNameButton = findViewById(R.id.button_real_name);
        mRealNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = AntiAddiction.getInstance().getUser();
                if (user.isTourist()) {
                    if (user.getRealNameResult().isProcessing()) {
                        toast("RealName is processing, please wait...");
                    } else {
                        realName();
                    }
                } else {
                    toast("RealName Success");
                }
            }
        });

        mRealNameCustomUIButton = findViewById(R.id.button_real_name_custom_ui);
        mRealNameCustomUIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AntiAddictionActivity.this, CustomRealNameActivity.class);
                startActivity(intent);
            }
        });

        mUpdateUserButton = findViewById(R.id.button_update_user);
        mUpdateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo(AntiAddiction.getInstance().getUser());
            }
        });
    }

    // 设置时间限制
    private void customTimeLimit() {
        // 是否允许 SDK 自动弹出时间限制页面，默认自动弹出
        AntiAddiction.getInstance().setAutoShowTimeLimitPage(false);
        // 关闭自动弹出时间限制页面后，App 可以注册 TimeLimitCallback，并在 onTimeLimit() 回调中进行提示
        AntiAddiction.getInstance().registerTimeLimitCallback(new TimeLimitCallback() {
            @Override
            public void onTimeLimit(TimeLimit timeLimit) {
                toast("onTimeLimit: " + timeLimit);
                if (!CustomTimeLimitActivity.sIsShown) {
                    CustomTimeLimitActivity.start(AntiAddictionActivity.this, timeLimit);
                }
            }
        });
    }

    // 实名认证，使用 SDK 默认 UI
    private void realName() {
        AntiAddiction.getInstance().realName(new RealNameCallback() {
            @Override
            public void onFinish(User user) {
                toast("realName onFinish: " + user);
                updateUserInfo(user);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 更新用户信息
        updateUserInfo(AntiAddiction.getInstance().getUser());
    }

    // 更新用户信息
    private void updateUserInfo(User user) {
        String result;
        RealNameResult realNameResult = user.getRealNameResult();
        if (realNameResult.isSuccess()) {
            result = "实名认证成功";
        } else if (realNameResult.isFail()) {
            result = "实名认证失败";
        } else if (realNameResult.isProcessing()) {
            result = "实名认证中...";
        } else {
            result = "初始状态";
        }

        String info;
        if (user.isTourist()) {
            info = "游客";
        } else if (user.isChild()) {
            info = "未成年人";
        } else if (user.isAdult()) {
            info = "成年人";
        } else {
            info = "游客";
        }

        int age = user.getAge();

        String userInfo = "实名认证结果: " + result + "\n"
                + ", 身份：" + info + "\n"
                + ", 年龄：" + age;
        mUserTextView.setText(userInfo);
    }
}