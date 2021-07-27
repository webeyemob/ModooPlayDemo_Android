package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.strategy.base.bean.StrategyAsset;
import com.richox.strategy.base.bean.StrategyExchangeTask;
import com.richox.strategy.base.bean.StrategyMissionTask;
import com.richox.strategy.normal.ROXNormalStrategy;
import com.richox.strategy.normal.bean.NormalMissionResult;
import com.richox.strategy.normal.bean.NormalStrategyConfig;
import com.richox.strategy.normal.bean.NormalStrategyWithdrawTask;
import com.taurusx.ads.core.api.TaurusXAds;
import com.taurusx.ads.core.api.ad.RewardedVideoAd;
import com.taurusx.ads.core.api.listener.AdError;
import com.taurusx.ads.core.api.listener.newapi.RewardedVideoAdListener;
import com.taurusx.ads.core.api.model.ILineItem;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.List;

public class RichOXNormalStrategyCustomActivity extends BaseActivity {
    private TextView mFetchConfig;
    private TextView mLoadRewarded;
    private TextView mShowRewarded;
    private TextView mCustomRewardClient;
    private TextView mCustomRewardServer;

    private final int STRATEGY_ID = 272;
    private final String CUSTOM_ID_CLIENT = "test1";
    private final String CUSTOM_ID_SERVER = "test";
    private String mTid = "";

    private RewardedVideoAd mRewardedVideoAd;
    private String mRewardedVideoAdUnitId = "cfc3aafd-5bff-4803-99d0-de3b5a4286ca";

    private boolean mRewardedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_normal_strategy_custom);
        initView();
        initTaurusX();
        initRewardedVideoAd();
    }

    private void initTaurusX() {
        TaurusXAds.getDefault().init(this, "4b4b6832-4267-42db-9c04-d517d5288bbb");
    }

    private void initRewardedVideoAd() {
        // Create RewardedVideoAd
        mRewardedVideoAd = new RewardedVideoAd(this);
        mRewardedVideoAd.setAdUnitId(mRewardedVideoAdUnitId);

        // Listen Ad load result
        mRewardedVideoAd.setADListener(new RewardedVideoAdListener() {
            @Override
            public void onAdLoaded(ILineItem iLineItem) {
                mShowRewarded.setEnabled(true);
                ToastUtil.showToast(RichOXNormalStrategyCustomActivity.this, "加载成功");
            }

            @Override
            public void onAdShown(ILineItem iLineItem) {
                mRewardedStatus = false; // 展示广告时，重置之前的状态
            }

            @Override
            public void onAdClicked(ILineItem iLineItem) {
            }

            @Override
            public void onRewarded(ILineItem iLineItem, RewardedVideoAd.RewardItem rewardItem) {
                mRewardedStatus = true; // 广告平台返回奖励结果
            }

            @Override
            public void onAdClosed(ILineItem iLineItem) {
                if (mRewardedStatus) {
                    if (iLineItem != null) {
                        mTid = iLineItem.getTId(); //获取广告校验id
                    }
                }

            }

            @Override
            public void onAdFailedToLoad(AdError adError) {
                ToastUtil.showToast(RichOXNormalStrategyCustomActivity.this, "加载失败");
            }

            @Override
            public void onVideoStarted(ILineItem iLineItem) {
            }

            @Override
            public void onVideoCompleted(ILineItem iLineItem) {
            }

            @Override
            public void onRewardFailed(ILineItem iLineItem) {
            }
        });
    }

    private void initView() {
        mFetchConfig = findViewById(R.id.demo_normal_strategy_fetch_config);
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

        mLoadRewarded = findViewById(R.id.demo_normal_strategy_load_reward);
        mLoadRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRewardedVideoAd.loadAd();
            }
        });

        mShowRewarded = findViewById(R.id.demo_normal_strategy_show_reward);
        mShowRewarded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadRewarded.setEnabled(false);
                if (mRewardedVideoAd.isReady()) {
                    mRewardedVideoAd.show(RichOXNormalStrategyCustomActivity.this);
                }
            }
        });

        mCustomRewardClient = findViewById(R.id.demo_normal_strategy_custom_reward);
        mCustomRewardClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.TAG, "the tid is " + mTid);
                ROXNormalStrategy.getInstance(STRATEGY_ID).doCustomRulesMission(CUSTOM_ID_CLIENT, mTid, new CommonCallback<NormalMissionResult>() {
                    @Override
                    public void onSuccess(NormalMissionResult normalMissionResult) {
                        Log.d(Constants.TAG, "the result is  " + normalMissionResult.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mCustomRewardServer = findViewById(R.id.demo_normal_strategy_custom_reward2);
        mCustomRewardServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXNormalStrategy.getInstance(STRATEGY_ID).doCustomRulesMission(CUSTOM_ID_SERVER, new CommonCallback<NormalMissionResult>() {
                    @Override
                    public void onSuccess(NormalMissionResult normalMissionResult) {
                        Log.d(Constants.TAG, "the result is  " + normalMissionResult.toString());
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
