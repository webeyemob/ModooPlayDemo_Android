package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.strategy.base.WithdrawResult;
import com.richox.strategy.stage.ROXStageStrategy;
import com.richox.strategy.stage.bean.BaseType;
import com.richox.strategy.stage.bean.StageMissionResult;
import com.richox.strategy.stage.bean.StageMissionTask;
import com.richox.strategy.stage.bean.StageProgressConfig;
import com.richox.strategy.stage.bean.StageStrategyConfig;
import com.richox.strategy.stage.bean.StageStrategyInfo;
import com.richox.strategy.stage.bean.StageWithdrawStatus;
import com.richox.strategy.stage.bean.StageWithdrawTask;
import com.richox.strategy.stage.bean.TypeWithdraw;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.List;

public class RichOXStageStrategyActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mDoMission;
    private TextView mGetStageInfo;
    private TextView mExtremeWithdraw;
    private TextView mWithdraw;

    private final int STRATEGY_ID = 244;
    private final String MISSION_ID = "watchvideo1";
    private final String EXTREME_WITHDRAW_ID = "withdraw03";
    private final String WITHDRAW_ID = "withdraw3";

    private LinearLayout mWithdrawContent;
    private LinearLayout mMissionContent;

    private List<StageWithdrawTask> mStageWithdrawTasks;
    private List<StageMissionTask> mTaskList;
    private List<StageWithdrawStatus> mWithdrawStatusList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_strategy_stage);
        initView();
    }

    private void addView2MissionContent() {
        if (mMissionContent != null) {
            mMissionContent.removeAllViews();
        }
        for (StageMissionTask task : mTaskList) {
            TextView textView = new TextView(RichOXStageStrategyActivity.this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
            textView.setGravity(Gravity.CENTER);
            textView.setText(task.getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (task.getPrizeType() == StageMissionTask.PRIZE_TYPE_FIXED_VALUE) {
                        ROXStageStrategy.getInstance(STRATEGY_ID).doMission(task.getId(), new CommonCallback<StageMissionResult>() {
                            @Override
                            public void onSuccess(StageMissionResult stageMissionResult) {
                                Log.d(Constants.TAG, stageMissionResult.toString());
                                updateProgress();
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                            }
                        });
                    } else {
                        ROXStageStrategy.getInstance(STRATEGY_ID).doMission(task.getId(), task.getPrizeAmount(), new CommonCallback<StageMissionResult>() {
                            @Override
                            public void onSuccess(StageMissionResult stageMissionResult) {
                                Log.d(Constants.TAG, stageMissionResult.toString());
                                updateProgress();
                            }

                            @Override
                            public void onFailed(int code, String msg) {

                            }
                        });
                    }

                }
            });
            mMissionContent.addView(textView);
        }
    }

    private void addView2WithdrawContent() {
        if (mWithdrawContent != null) {
            mWithdrawContent.removeAllViews();
        }
        for (StageWithdrawStatus withdrawStatus : mWithdrawStatusList) {
            View view = LayoutInflater.from(RichOXStageStrategyActivity.this).inflate(R.layout.layout_stage_item, null);
            TypeWithdraw typeWithdraw = withdrawStatus.getTypeList().get(0);
            if (typeWithdraw != null) {
                ((TextView) view.findViewById(R.id.demo_stage_name)).setText(typeWithdraw.getName());
                int progress = (int) (typeWithdraw.getTotal() * 100 / typeWithdraw.getRange());
                if (progress > 100) {
                    progress = 100;
                }
                ((ProgressBar) view.findViewById(R.id.demo_stage_progress)).setProgress(progress);
                TextView textView = (TextView) view.findViewById(R.id.demo_stage_submit);
                int status = withdrawStatus.getStatus();
                // 未提现
                if (status == WithdrawResult.STATUS_NO_WITHDRAW) {
                    if (progress == 100) {
                        // 判断是否有额外条线
                        int maxRestrict = withdrawStatus.getMaxExternalRestrict();
                        int currentRestrict = withdrawStatus.getCurrentExternalRestrict();
                        // 有限制条件
                        if (maxRestrict > 0) {
                            if (currentRestrict > maxRestrict) {
                                textView.setText("去提现");
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        go2Withdraw(withdrawStatus.getTaskId());
                                    }
                                });
                            } else {
                                // 未达到限制条件
                                textView.setText("还差" + (maxRestrict - currentRestrict) + "人");
                            }
                        } else {
                            textView.setText("去提现");
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    go2Withdraw(withdrawStatus.getTaskId());
                                }
                            });
                        }
                    } else {
                        textView.setText("去完成任务");
                    }

                } else if (status == WithdrawResult.STATUS_REMIT_SUCCESS) {
                    textView.setText("已提现");
                } else {
                    textView.setText("处理中");
                }
            }
            mWithdrawContent.addView(view);
        }
    }

    private StageWithdrawTask getTaskConfig(String taskId) {
        for (StageWithdrawTask withdrawTask : mStageWithdrawTasks) {
            if (taskId.equals(withdrawTask.getId())) {
                return withdrawTask;
            }
        }
        return null;
    }

    private void go2Withdraw(String taskId) {
        // 获取提现配置信息，根据提现配置信息去提现
        StageWithdrawTask task = getTaskConfig(taskId);
        if (task.isExtreme()) {
            ROXStageStrategy.getInstance(STRATEGY_ID).extremeWithdraw(taskId, new CommonCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    Log.d(Constants.TAG, "withdraw : " + aBoolean);
                    ToastUtil.showToast(RichOXStageStrategyActivity.this, "提现成功");
                    updateProgress();
                }

                @Override
                public void onFailed(int code, String msg) {
                    Log.d(Constants.TAG, "withdraw failed : " + code + " msg " + msg);
                    ToastUtil.showToast(RichOXStageStrategyActivity.this, "提现失败");
                }
            });
        } else {
            String name = "张松顺";
            String phone = "13770561390";
            String card = "320911198512011934";
            ROXStageStrategy.getInstance(STRATEGY_ID).withdraw(taskId, name, card, phone, new CommonCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    Log.d(Constants.TAG, "withdraw : " + aBoolean);
                    ToastUtil.showToast(RichOXStageStrategyActivity.this, "提现成功");
                    updateProgress();
                }

                @Override
                public void onFailed(int code, String msg) {
                    Log.d(Constants.TAG, "withdraw failed : " + code + " msg " + msg);
                    ToastUtil.showToast(RichOXStageStrategyActivity.this, "提现失败");
                }
            });
        }
    }

    private void initView() {
        mFetchConfig = findViewById(R.id.demo_stage2_fetch_config);
        mFetchConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).getStrategyConfig(new CommonCallback<StageStrategyConfig>() {
                    @Override
                    public void onSuccess(StageStrategyConfig stageStrategyConfig) {
                        if (stageStrategyConfig != null) {
                            Log.d(Constants.TAG, "the common info is " + stageStrategyConfig.toString());
                            StageProgressConfig config = stageStrategyConfig.getStageProgressConfig();
                            if (config != null) {
                                List<BaseType> baseList = config.getTypeList();
                                for (BaseType baseType : baseList) {
                                    Log.d(Constants.TAG, baseType.toString());
                                }

                                mTaskList = config.getTaskList();
                                for (StageMissionTask task : mTaskList) {
                                    Log.d(Constants.TAG, task.toString());
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        addView2MissionContent();
                                    }
                                });

                            }

                            mStageWithdrawTasks = stageStrategyConfig.getWithdrawTaskList();
                            for (StageWithdrawTask withdrawTask : mStageWithdrawTasks) {
                                Log.d(Constants.TAG, withdrawTask.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mWithdrawContent = findViewById(R.id.demo_stage2_withdraw_content);
        mMissionContent = findViewById(R.id.demo_stage2_mission_content);
    }

    private void updateProgress() {
        ROXStageStrategy.getInstance(STRATEGY_ID).queryStageInfo(new CommonCallback<StageStrategyInfo>() {
            @Override
            public void onSuccess(StageStrategyInfo info) {
                if (info != null) {
                    mWithdrawStatusList = info.getWithdrawStatus();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addView2WithdrawContent();
                        }
                    });
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
            }
        });
    }
}
