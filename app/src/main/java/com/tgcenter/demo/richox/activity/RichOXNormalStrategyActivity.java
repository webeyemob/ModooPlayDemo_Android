package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.strategy.normal.ROXNormalStrategy;
import com.richox.strategy.normal.bean.NormalAssetsInfo;
import com.richox.strategy.normal.bean.NormalMissionTask;
import com.richox.strategy.normal.bean.NormalStrategyConfig;
import com.richox.strategy.normal.bean.NormalWithdrawTask;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.List;

public class RichOXNormalStrategyActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mDoMission;
    private TextView mGetStageInfo;
    private TextView mExtremeWithdraw;
    private TextView mWithdraw;

    private final int STRATEGY_ID = 59;
    private final String MISSION_ID = "323ffeed";
    private final String EXTREME_WITHDRAW_ID = "86020ab0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_normal_strategy);
        initView();
    }

    private void initView() {
        mFetchConfig = findViewById(R.id.demo_stage2_fetch_config);
        mFetchConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).getStrategyConfig(new CommonCallback<NormalStrategyConfig>() {
                    @Override
                    public void onSuccess(NormalStrategyConfig strategyConfig) {
                        Log.d(Constants.TAG, "the common info is " + strategyConfig.toString());
                        List<NormalMissionTask> missionTaskList = strategyConfig.getMissionTaskInfo().getMissionTaskList();
                        Log.d(Constants.TAG, "the mission task is: ");
                        for (NormalMissionTask task : missionTaskList) {
                            Log.d(Constants.TAG, task.toString());
                        }
                        Log.d(Constants.TAG, "the withdraw task is: ");
                        List<NormalWithdrawTask> withdrawTaskList = strategyConfig.getWithdrawTaskList();
                        for (NormalWithdrawTask task : withdrawTaskList) {
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
                ROXNormalStrategy.getInstance(STRATEGY_ID).doMission(MISSION_ID, 2, new CommonCallback<Boolean>() {
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
                ROXNormalStrategy.getInstance(STRATEGY_ID).queryAssetInfo(new CommonCallback<NormalAssetsInfo>() {
                    @Override
                    public void onSuccess(NormalAssetsInfo info) {
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
                ROXNormalStrategy.getInstance(STRATEGY_ID).extremeWithdraw(EXTREME_WITHDRAW_ID, "test withdraw", new CommonCallback<Boolean>() {
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
