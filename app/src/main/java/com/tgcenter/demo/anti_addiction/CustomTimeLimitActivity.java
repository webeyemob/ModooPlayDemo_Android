package com.tgcenter.demo.anti_addiction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.taurusx.ads.core.api.utils.LogUtil;
import com.taurusx.ads.core.api.utils.ScreenUtil;
import com.tgcenter.demo.R;
import com.tgcenter.demo.util.ToastUtil;
import com.tgcenter.unified.antiaddiction.api.AntiAddiction;
import com.tgcenter.unified.antiaddiction.api.realname.RealNameCallback;
import com.tgcenter.unified.antiaddiction.api.timelimit.TimeLimit;
import com.tgcenter.unified.antiaddiction.api.user.RealNameResult;
import com.tgcenter.unified.antiaddiction.api.user.User;

public class CustomTimeLimitActivity extends Activity {

    private static final String TAG = "TimeLimitActivity";

    // 此页面是否正在展示，如果同时触发了多个时间限制提示，则只能提示一个
    public static boolean sIsShown;

    private static final String KEY_TIME_LIMIT = "time_limit";

    public static void start(Context context, TimeLimit timeLimit) {
        LogUtil.d(TAG, "start");
        sIsShown = true;
        Intent intent = new Intent(context, CustomTimeLimitActivity.class);
        intent.putExtra(KEY_TIME_LIMIT, timeLimit);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // 触发此页面展示的时间限制
    private TimeLimit mTimeLimit;

    private TextView mTitleTextView;
    private TextView mDescTextView;
    private Button mActionButton;
    private Button mRealNameButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timelimit_customui);

        initData();
        initView();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ScreenUtil.getScreenWidth(this)
                - ScreenUtil.dp2px(this, 16 * 2);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        setFinishOnTouchOutside(false);
    }

    private void initData() {
        mTimeLimit = (TimeLimit) getIntent().getSerializableExtra(KEY_TIME_LIMIT);
    }

    private void initView() {
        final TimeLimit.LimitTip limitTip = mTimeLimit.getLimitTip();

        mTitleTextView = findViewById(com.tgcenter.unified.antiaddiction.R.id.textView_title);
        mTitleTextView.setText(limitTip.getTitle());

        mDescTextView = findViewById(com.tgcenter.unified.antiaddiction.R.id.textView_desc);
        mDescTextView.setText(limitTip.getDesc());

        mActionButton = findViewById(com.tgcenter.unified.antiaddiction.R.id.button_action);
        mActionButton.setText(limitTip.getButton());
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "clickAction: " + mActionButton.getText().toString());
                if (mTimeLimit.getTimeToLimit() == 0) {
                    LogUtil.d(TAG, "TimeToLimit is 0, move app to background");
                    // 将 app 退到后台
                    moveTaskToBack(true);
                } else {
                    LogUtil.d(TAG, "TimeToLimit > 0, finish this Activity");
                    // 没有达到限制，直接退出此页面
                    finish();
                }
            }
        });

        if (limitTip.canRealName()) {
            LogUtil.d(TAG, "can RealName, show realName button");
            mRealNameButton = findViewById(com.tgcenter.unified.antiaddiction.R.id.button_realname);
            mRealNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.d(TAG, "click realName button");
                    // 可以调用 SDK 默认的实名认证页面，可以使用 CustomRealNameActivity
                    AntiAddiction.getInstance().realName(new RealNameCallback() {
                        @Override
                        public void onFinish(User user) {
                            LogUtil.d(TAG, "realName finish: " + user);
                            RealNameResult result = user.getRealNameResult();
                            if (result.isProcessing()) {
                                LogUtil.d(TAG, "realName  isProcessing, stay this Activity");
                                ToastUtil.toastLong(CustomTimeLimitActivity.this, com.tgcenter.unified.antiaddiction.R.string.timelimit_realname_process);
                            } else if (result.isSuccess()) {
                                LogUtil.d(TAG, "realName success, finish this Activity");
                                finish();
                            } else {
                                LogUtil.d(TAG, "realName fail, stay this Activity");
                                // 认证失败、初始状态时，继续展示此页面
                            }
                        }
                    });
                }
            });
            mRealNameButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            sIsShown = false;
        }
    }

    @Override
    public void onBackPressed() {
        // 避免用户误触手机的返回键关闭此页面，必须用户手动关闭
        // super.onBackPressed();
    }
}