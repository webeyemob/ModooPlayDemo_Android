package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.strategy.stage.ROXStageStrategy;
import com.richox.strategy.stage.bean.StageMissionTask;
import com.richox.strategy.stage.bean.StageStrategyConfig;
import com.richox.strategy.stage.bean.StageStrategyInfo;
import com.richox.strategy.stage.bean.StageWithdrawTask;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.List;

public class RichOXStageStrategyActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mDoMission;
    private TextView mGetStageInfo;
    private TextView mExtremeWithdraw;
    private TextView mWithdraw;

    private final int STRATEGY_ID = 57;
    private final String MISSION_ID = "323ffeed";
    private final String EXTREME_WITHDRAW_ID = "f38aebf8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_strategy2);
        initView();
    }

    private void initView() {
        mFetchConfig = findViewById(R.id.demo_stage2_fetch_config);
        mFetchConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).getStrategyConfig(new CommonCallback<StageStrategyConfig>() {
                    @Override
                    public void onSuccess(StageStrategyConfig stageStrategyConfig) {
                        Log.d(Constants.TAG, "the common info is " + stageStrategyConfig.toString());
                        List<StageMissionTask> stageMissionTaskList = stageStrategyConfig.getMissionTaskList();
                        Log.d(Constants.TAG, "the mission task is: ");
                        for (StageMissionTask task : stageMissionTaskList) {
                            Log.d(Constants.TAG, task.toString());
                        }
                        Log.d(Constants.TAG, "the withdraw task is: ");
                        List<StageWithdrawTask> stageWithdrawTaskList = stageStrategyConfig.getWithdrawTaskList();
                        for (StageWithdrawTask task : stageWithdrawTaskList) {
                            Log.d(Constants.TAG, task.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mDoMission = findViewById(R.id.demo_stage2_do_mission);
        mDoMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).doMission(MISSION_ID, 1, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        Log.d(Constants.TAG, "the result is  " + result);

                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mGetStageInfo = findViewById(R.id.demo_stage2_query_stage);
        mGetStageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).queryStageInfo(new CommonCallback<StageStrategyInfo>() {
                    @Override
                    public void onSuccess(StageStrategyInfo info) {
                        Log.d(Constants.TAG, "the result is  " + info);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mExtremeWithdraw = findViewById(R.id.demo_stage2_extreme_withdraw);
        mExtremeWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).extremeWithdraw(EXTREME_WITHDRAW_ID, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(Constants.TAG, "the result is  " + aBoolean);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });
    }
}
