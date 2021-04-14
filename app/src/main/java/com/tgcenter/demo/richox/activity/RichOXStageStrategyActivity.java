package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.base.RichOX;
import com.richox.strategy.base.WithdrawResult;
import com.richox.strategy.base.bean.StrategyMissionResult;
import com.richox.strategy.base.bean.StrategyMissionTask;
import com.richox.strategy.base.bean.StrategyWithdrawRecord;
import com.richox.strategy.stage.ROXStageStrategy;
import com.richox.strategy.stage.bean.StageStrategyConfig;
import com.richox.strategy.stage.bean.StageStrategyInfo;
import com.richox.strategy.stage.bean.StageWithdrawStatus;
import com.richox.strategy.stage.bean.StageWithdrawTask;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RichOXStageStrategyActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mDoMission;
    private TextView mGetStageInfo;
    private TextView mExtremeWithdraw;
    private TextView mWithdraw;

    private final int STRATEGY_ID = 146;
    private final String MISSION_ID = "watchvideo";
    private final String EXTREME_WITHDRAW_ID = "withdraw1";
    private final String WITHDRAW_ID = "withdraw3";

    private LinearLayout mStrategyContent;
    private List<View> mStageListView = new ArrayList<>();

    Map<Integer, StageWithdrawTask> mStageWithdrawTaskMap = new HashMap<>();
    Map<String, Integer> mWithdrawStatusMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_strategy_stage);
        initView();
    }

    private void addView2StageContent() {
        if (mStrategyContent != null) {
            mStrategyContent.removeAllViews();
        }
        Set<Integer> keys = mStageWithdrawTaskMap.keySet();
        for (int index : keys) {
            View childView = LayoutInflater.from(this).inflate(R.layout.layout_stage_item, null);
            childView.setTag(index);
            mStageListView.add(childView);
            mStrategyContent.addView(childView);
        }
    }

    private void updateStage(StageStrategyInfo info) {
        int total = info.getCurrentProgress();
        int left = total;
        int size = mStageListView.size();
        for (int i = 0; i < size; i++) {
            int index = i + 1;
            View view = getViewByIndex(index);
            ProgressBar progressBar = ((ProgressBar) view.findViewById(R.id.demo_stage_progress));
            TextView textView = (TextView) view.findViewById(R.id.demo_stage_submit);
            textView.setOnClickListener(null);
            StageWithdrawTask task = mStageWithdrawTaskMap.get(index);
            if (left < task.getProcessRank()) {
                progressBar.setProgress((left * 100 / task.getProcessRank()));
                textView.setText("未达标");
                break;
            } else {
                progressBar.setProgress(100);
                left = left - task.getProcessRank();
                boolean hasRecorded = mWithdrawStatusMap.containsKey(task.getId());
                if (hasRecorded) {
                    int status = mWithdrawStatusMap.get(task.getId());
                    switch (status) {
                        case WithdrawResult.STATUS_REMIT_SUCCESS:
                            textView.setText("已提现");
                            break;
                        case WithdrawResult.STATUS_REVIEW_PASS:
                        case WithdrawResult.STATUS_WAITING_REMIT:
                        case WithdrawResult.STATUS_WAITING_REVIEW:
                            textView.setText("审核中");
                            break;
                        case WithdrawResult.STATUS_REVIEW_REJECT:
                        case WithdrawResult.STATUS_REMIT_FAILED:
                            textView.setText("提现失败");
                            break;
                        case 0 :
                            textView.setText("提现");
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (task.isExtreme()) {
                                        ROXStageStrategy.getInstance(STRATEGY_ID).extremeWithdraw(task.getId(), 2, new CommonCallback<Boolean>() {
                                            @Override
                                            public void onSuccess(Boolean aBoolean) {
                                                Log.d(Constants.TAG, "the result is  " + aBoolean);
                                                updateProgress();
                                            }

                                            @Override
                                            public void onFailed(int code, String msg) {
                                                Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                                            }
                                        });
                                    } else {
                                        ROXStageStrategy.getInstance(STRATEGY_ID).withdraw(task.getId(), 1, "jenkins.zhang", "xxx", "123", new CommonCallback<Boolean>() {
                                            @Override
                                            public void onSuccess(Boolean aBoolean) {
                                                Log.d(Constants.TAG, "the result is  " + aBoolean);
                                                updateProgress();
                                            }

                                            @Override
                                            public void onFailed(int code, String msg) {
                                                Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                                            }
                                        });
                                    }
                                }
                            });
                            break;
                    }
                }
            }
        }
    }

    private View getViewByIndex(int index) {
        int size = mStrategyContent.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = mStrategyContent.getChildAt(i);
            int tag = (int) view.getTag();
            if (tag == index) {
                return view;
            }
        }
        return null;
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
                        List<StrategyMissionTask> stageMissionTaskList = stageStrategyConfig.getMissionTaskList();
                        Log.d(Constants.TAG, "the mission task is: ");
                        for (StrategyMissionTask task : stageMissionTaskList) {
                            Log.d(Constants.TAG, task.toString());
                        }
                        Log.d(Constants.TAG, "the withdraw task is: ");
                        List<StageWithdrawTask> stageWithdrawTaskList = stageStrategyConfig.getWithdrawTaskList();
                        for (StageWithdrawTask task : stageWithdrawTaskList) {
                            mStageWithdrawTaskMap.put(task.getIndex(), task);
                            Log.d(Constants.TAG, task.toString());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addView2StageContent();
                            }
                        });

                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mStrategyContent = findViewById(R.id.strategy_content);

        mDoMission = findViewById(R.id.demo_stage2_do_mission);
        mDoMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).doMission(MISSION_ID, 5, new CommonCallback<StrategyMissionResult>() {
                    @Override
                    public void onSuccess(StrategyMissionResult result) {
                        Log.d(Constants.TAG, "the result is  " + result.toString());
                        updateProgress();
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
                updateProgress();
            }
        });

        mExtremeWithdraw = findViewById(R.id.demo_stage2_extreme_withdraw);
        mExtremeWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).extremeWithdraw(EXTREME_WITHDRAW_ID, 2, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(Constants.TAG, "the result is  " + aBoolean);
                        updateProgress();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mWithdraw = findViewById(R.id.demo_stage2_withdraw);
        mWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXStageStrategy.getInstance(STRATEGY_ID).withdraw(WITHDRAW_ID, 1, "jenkins.zhang", "xxx", "123", new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(Constants.TAG, "the result is  " + aBoolean);
                        updateProgress();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });
    }

    private void updateProgress() {
        if (!TextUtils.isEmpty(RichOX.getUserId())) {

        }
        ROXStageStrategy.getInstance(STRATEGY_ID).queryStageInfo(new CommonCallback<StageStrategyInfo>() {
            @Override
            public void onSuccess(StageStrategyInfo info) {
                if (info != null) {
                    Log.d(Constants.TAG, "the result is  " + info);
                    Log.d(Constants.TAG, "the withdraw list :");
                    // 返回提现记录
                    List<StrategyWithdrawRecord> records = info.getWithdrawRecords();
                    for (StrategyWithdrawRecord record : records) {
                        Log.d(Constants.TAG, record.toString());
                    }

                    Log.d(Constants.TAG, "the withdraw status :");
                    List<StageWithdrawStatus> statusList = info.getWithdrawStatus();
                    for (StageWithdrawStatus status : statusList) {
                        Log.d(Constants.TAG, status.toString());
                        mWithdrawStatusMap.put(status.getTaskId(), status.getStatus());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 更新阶梯信息
                            updateStage(info);
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
