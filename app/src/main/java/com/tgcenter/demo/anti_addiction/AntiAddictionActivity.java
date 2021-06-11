package com.tgcenter.demo.anti_addiction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.taurusx.ads.core.api.utils.LogUtil;
import com.tgcenter.demo.R;
import com.tgcenter.demo.util.ToastUtil;
import com.tgcenter.unified.antiaddiction.api.AntiAddiction;
import com.tgcenter.unified.antiaddiction.api.agetip.AgeTipsListener;
import com.tgcenter.unified.antiaddiction.api.event.EventCallback;
import com.tgcenter.unified.antiaddiction.api.event.EventManager;
import com.tgcenter.unified.antiaddiction.api.event.RealNameEvent;
import com.tgcenter.unified.antiaddiction.api.event.TimeLimitEvent;
import com.tgcenter.unified.antiaddiction.api.healthgametip.HealthGameTipsListener;
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

    private FrameLayout mAgeTipsContainer;
    private Button mAgeTipsBtn;
    private Button mHealthGameBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anti_addiction);
        initView();

        // 设置事件限制
        customTimeLimit();
        // 注册事件回调
        listenEvent();
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
                    realName();
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

        mAgeTipsContainer = findViewById(R.id.age_tips_container);

        mAgeTipsBtn = findViewById(R.id.btn_age_tips);
        mAgeTipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AntiAddiction.getInstance().showAgeTipsIcon(mAgeTipsContainer, new AgeTipsListener() {
                    @Override
                    public void onIconShowSuccess() {
                        Log.d(TAG, "onIconShowSuccess");
                        mAgeTipsContainer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onIconShowFail(String s) {
                        Log.d(TAG, "onIconShowFail, error message is : " + s);
                    }

                    @Override
                    public void onIconClick() {
                        Log.d(TAG, "onIconClick");
                    }

                    @Override
                    public void onAgeTipsOpen() {
                        Log.d(TAG, "onAgeTipsOpen");
                    }

                    @Override
                    public void onAgeTipsClose() {
                        Log.d(TAG, "onAgeTipsClose");
                        mAgeTipsContainer.setVisibility(View.GONE);
                    }
                });
            }
        });

        mHealthGameBtn = findViewById(R.id.btn_health_game);
        mHealthGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, AntiAddiction.getInstance().getGameComplianceInfo().getAgeTips());
                AntiAddiction.getInstance().showHealthGamePage(new HealthGameTipsListener() {
                    @Override
                    public void onHealthGameTipsOpen() {
                        Log.d(TAG, "onHealthGameTipsOpen");
                    }

                    @Override
                    public void onHealthGameTipsOpenFail(String s) {
                        Log.d(TAG, "onHealthGameTipsOpenFail, error message is : " + s);
                    }

                    @Override
                    public void onHealthGameTipsClose() {
                        Log.d(TAG, "onHealthGameTipsClose");
                    }
                });
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

    // 注册事件回调
    private void listenEvent() {
        EventManager.INSTANCE.registerCallback(new EventCallback() {
            // 实名认证事件回调
            @Override
            public void onRealName(RealNameEvent realNameEvent) {
                Log.d(TAG, "EventManager onRealName: " + realNameEvent);

                /*
                 * 实名认证的来源，参考 RealNameEvent.Source
                 * RealNameEvent.Source.SDK_UI：使用 SDK 的默认 UI
                 * RealNameEvent.Source.Custom_UI：使用 App 自定义 UI
                 * RealNameEvent.Source.TimeLimit：从时间限制弹窗进入实名认证 UI
                 */
                int source = realNameEvent.getSource();
                /*
                 * 用户行为，参考 RealNameEvent.Action
                 * RealNameEvent.Action.Show：展示实名认证弹窗
                 * RealNameEvent.Action.Close：关闭实名认证弹窗
                 * RealNameEvent.Action.Submit：提交实名认证
                 */
                int action = realNameEvent.getAction();

                // action 为 RealNameEvent.Action.Submit 时，可以获得实名认证的结果
                RealNameResult result = realNameEvent.getResult();
            }

            // 时间限制事件回调
            @Override
            public void onTimeLimit(TimeLimitEvent timeLimitEvent) {
                Log.d(TAG, "EventManager onTimeLimit: " + timeLimitEvent);

                /*
                 * 用户行为，参考 TimeLimitEvent.Action
                 * TimeLimitEvent.Action.Show：展示时间限制弹窗
                 * TimeLimitEvent.Action.OpenRealName：进行实名认证
                 * TimeLimitEvent.Action.CloseDialog：关闭时间限制弹窗（游戏时间未达到限制）
                 * TimeLimitEvent.Action.ExitApp：退出 App（游戏时间已达到限制）
                 */
                int action = timeLimitEvent.getAction();

                // 产生时间限制事件时，具体的时间限制原因
                TimeLimit timeLimit = timeLimitEvent.getTimeLimit();
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