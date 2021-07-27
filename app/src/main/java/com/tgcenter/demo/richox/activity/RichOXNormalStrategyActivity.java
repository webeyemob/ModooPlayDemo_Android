package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.strategy.base.bean.StrategyAsset;
import com.richox.strategy.base.bean.StrategyExchangeTask;
import com.richox.strategy.base.bean.StrategyMissionTask;
import com.richox.strategy.base.bean.StrategyWithdrawRecord;
import com.richox.strategy.normal.ROXNormalStrategy;
import com.richox.strategy.normal.bean.NormalAssetStock;
import com.richox.strategy.normal.bean.NormalAssetsInfo;
import com.richox.strategy.normal.bean.NormalMissionResult;
import com.richox.strategy.normal.bean.NormalMissionsProgress;
import com.richox.strategy.normal.bean.NormalStrategyConfig;
import com.richox.strategy.normal.bean.NormalStrategyWithdrawTask;
import com.richox.strategy.normal.bean.NormalTransformResult;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.ArrayList;
import java.util.List;

public class RichOXNormalStrategyActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mDoMissionMax;
    private TextView mDoMissionFixed;
    private TextView mGetStageInfo;
    private TextView mExtremeWithdraw;
    private TextView mWithdraw;
    private TextView mExtremeWithdrawNew;
    private TextView mWithdrawNew;
    private TextView mTransform;
    private TextView mTransformAll;
    private TextView mQueryMissions;
    private TextView mQueryMissionsAll;

    private final int STRATEGY_ID = 262;
    //    private final String MISSION_ID_FIXED = "dailyconsume2";
    private final String MISSION_ID_MAX = "dailycoin";
    private final String MISSION_ID_FIXED = "dailyconsume";
    private final String EXTREME_WITHDRAW_ID = "withdraw03";
    private final String WITHDRAW_ID = "withdraw3";
    private final String TRANSFORM_ID = "cointocash";
    private final String TRANSFORM_ID_ALL = "fragtocash";
//    private final String TRANSFORM_ID = "fragtocash";

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
                        List<StrategyMissionTask> strategyMissionTaskList = strategyConfig.getMissionTaskInfo().getMissionTaskList();
                        Log.d(Constants.TAG, "the mission task is: ");
                        for (StrategyMissionTask task : strategyMissionTaskList) {
                            Log.d(Constants.TAG, task.toString());
                        }

                        List<StrategyAsset> strategyAssetList = strategyConfig.getMissionTaskInfo().getStrategyAssetList();
                        Log.d(Constants.TAG, "the  strategy asset task is: ");
                        for (StrategyAsset asset : strategyAssetList) {
                            Log.d(Constants.TAG, asset.toString());
                        }

                        List<StrategyExchangeTask> strategyExchangeTaskList = strategyConfig.getMissionTaskInfo().getStrategyExchangeTaskList();
                        Log.d(Constants.TAG, "the exchange task is: ");
                        for (StrategyExchangeTask exchangeTask : strategyExchangeTaskList) {
                            Log.d(Constants.TAG, exchangeTask.toString());
                        }

                        Log.d(Constants.TAG, "the withdraw task is: ");
                        List<NormalStrategyWithdrawTask> withdrawTaskList = strategyConfig.getWithdrawTaskList();
                        for (NormalStrategyWithdrawTask task : withdrawTaskList) {
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

        mDoMissionMax = findViewById(R.id.demo_stage2_do_mission_max);
        mDoMissionMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).doMission(MISSION_ID_MAX, 49.3, new CommonCallback<NormalMissionResult>() {
                    @Override
                    public void onSuccess(NormalMissionResult result) {
                        Log.d(Constants.TAG, "the result is  " + result.toString());

                        List<NormalAssetStock> stocks = result.getAssetList();
                        Log.d(Constants.TAG, "the stocks is  ");
                        for (NormalAssetStock stock : stocks) {
                            Log.d(Constants.TAG, stock.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mDoMissionFixed = findViewById(R.id.demo_stage2_do_mission_fixed);
        mDoMissionFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).doMission(MISSION_ID_FIXED, new CommonCallback<NormalMissionResult>() {
                    @Override
                    public void onSuccess(NormalMissionResult result) {
                        Log.d(Constants.TAG, "the result is  " + result.toString());

                        List<NormalAssetStock> stocks = result.getAssetList();
                        Log.d(Constants.TAG, "the stocks is  ");
                        for (NormalAssetStock stock : stocks) {
                            Log.d(Constants.TAG, stock.toString());
                        }

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
                        if (info != null) {
                            Log.d(Constants.TAG, "the result is  " + info);
                            Log.d(Constants.TAG, "the asset list :");
                            List<NormalAssetStock> stocks = info.getAssetStockList();
                            for (NormalAssetStock stock : stocks) {
                                Log.d(Constants.TAG, stock.toString());
                            }
                            Log.d(Constants.TAG, "the withdraw list :");
                            List<StrategyWithdrawRecord> records = info.getWithdrawRecords();
                            for (StrategyWithdrawRecord record : records) {
                                Log.d(Constants.TAG, record.toString());
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

        mExtremeWithdrawNew = findViewById(R.id.demo_stage2_extreme_withdraw2);
        mExtremeWithdrawNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).extremeWithdrawNew(EXTREME_WITHDRAW_ID, new CommonCallback<List<NormalAssetStock>>() {
                    public void onSuccess(List<NormalAssetStock> list) {
                        for (NormalAssetStock stock : list) {
                            Log.d(Constants.TAG, "the result is  " + stock.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mWithdrawNew = findViewById(R.id.demo_stage2_withdraw2);
        mWithdrawNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).withdrawNew(WITHDRAW_ID, "*", "*", "*", new CommonCallback<List<NormalAssetStock>>() {
                    @Override
                    public void onSuccess(List<NormalAssetStock> list) {
                        for (NormalAssetStock stock : list) {
                            Log.d(Constants.TAG, "the result is  " + stock.toString());
                        }

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
                ROXNormalStrategy.getInstance(STRATEGY_ID).extremeWithdraw(EXTREME_WITHDRAW_ID, new CommonCallback<Boolean>() {
                    public void onSuccess(Boolean result) {
                        Log.d(Constants.TAG, "the result is  " + result.toString());
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
                ROXNormalStrategy.getInstance(STRATEGY_ID).withdraw(WITHDRAW_ID, "*", "*", "*", new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        Log.d(Constants.TAG, "the result is  " + result.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mTransform = findViewById(R.id.demo_stage2_transform);
        mTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).transform(TRANSFORM_ID, 1000, new CommonCallback<NormalTransformResult>() {
                    @Override
                    public void onSuccess(NormalTransformResult normalTransformResult) {
                        if (normalTransformResult != null) {
                            NormalTransformResult.TransformAssetInfo original = normalTransformResult.getOriginAsset();
                            Log.d(Constants.TAG, "the original is  " + original.toString());

                            NormalTransformResult.TransformAssetInfo destination = normalTransformResult.getDestinationAsset();
                            Log.d(Constants.TAG, "the destination is  " + destination.toString());

                            Log.d(Constants.TAG, "the asset list :");
                            List<NormalAssetStock> stocks = normalTransformResult.getAssetList();
                            for (NormalAssetStock stock : stocks) {
                                Log.d(Constants.TAG, stock.toString());
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


        mTransformAll = findViewById(R.id.demo_stage2_transform_all);
        mTransformAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).transform(TRANSFORM_ID_ALL, new CommonCallback<NormalTransformResult>() {
                    @Override
                    public void onSuccess(NormalTransformResult normalTransformResult) {
                        if (normalTransformResult != null) {
                            NormalTransformResult.TransformAssetInfo original = normalTransformResult.getOriginAsset();
                            Log.d(Constants.TAG, "the original is  " + original.toString());

                            NormalTransformResult.TransformAssetInfo destination = normalTransformResult.getDestinationAsset();
                            Log.d(Constants.TAG, "the destination is  " + destination.toString());

                            Log.d(Constants.TAG, "the asset list :");
                            List<NormalAssetStock> stocks = normalTransformResult.getAssetList();
                            for (NormalAssetStock stock : stocks) {
                                Log.d(Constants.TAG, stock.toString());
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

        mQueryMissions = findViewById(R.id.demo_stage2_query_missions);
        mQueryMissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                list.add(MISSION_ID_MAX);
                list.add(MISSION_ID_FIXED);
                ROXNormalStrategy.getInstance(STRATEGY_ID).queryProgress(list, new CommonCallback<NormalMissionsProgress>() {
                    @Override
                    public void onSuccess(NormalMissionsProgress normalMissionsProgress) {
                        Log.d(Constants.TAG, normalMissionsProgress.toString());
                        if (normalMissionsProgress.getMissionList() != null) {
                            for (NormalMissionsProgress.MissionProgress progress : normalMissionsProgress.getMissionList()) {
                                Log.d(Constants.TAG, progress.toString());
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


        mQueryMissionsAll = findViewById(R.id.demo_stage2_query_missions_all);
        mQueryMissionsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).queryAllProgress(new CommonCallback<NormalMissionsProgress>() {
                    @Override
                    public void onSuccess(NormalMissionsProgress normalMissionsProgress) {
                        Log.d(Constants.TAG, normalMissionsProgress.toString());
                        if (normalMissionsProgress.getMissionList() != null) {
                            for (NormalMissionsProgress.MissionProgress progress : normalMissionsProgress.getMissionList()) {
                                Log.d(Constants.TAG, progress.toString());
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

    }
}
