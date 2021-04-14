package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.richox.base.RichOX;
import com.richox.strategy.ROXStageStrategy;
import com.richox.strategy.mission.ResultCallback;
import com.richox.strategy.mission.bean.StageItem;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.adapter.StageAdapter;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.List;

public class RichOXStrategyFissionActivity extends BaseActivity {
    private TextView mStageId;
    private ListView mListView;
    private StageAdapter mStageAdapter;

    private final String STRATEGY_ID = "richox_test_stage";
    private final String MISSION_ID = "richox_ladder_test_watch_video";

    private String mCheckSum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_strategy_fission);
        sync();
        initView();
    }

    private void initView() {
        mStageId = findViewById(R.id.demo_stage_id);
        mStageId.setText(STRATEGY_ID);
        mListView = findViewById(R.id.demo_stage_list);
        mStageAdapter = new StageAdapter(RichOXStrategyFissionActivity.this, STRATEGY_ID, new StageAdapter.RefreshCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mStageAdapter != null) {
                            mStageAdapter.updateList();
                            mStageAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        });
        mStageAdapter.setMissionId(MISSION_ID);
        mListView.setAdapter(mStageAdapter);
    }

    private void sync() {
        if (!TextUtils.isEmpty(RichOX.getUserId())) {
            ROXStageStrategy.getInstance(STRATEGY_ID).syncList(new ResultCallback<List<StageItem>>() {

                @Override
                public void onSuccess(List<StageItem> response) {
                    if (response != null) {
                        logList(response);
                        ToastUtil.showToast(RichOXStrategyFissionActivity.this, "获取进度成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mStageAdapter != null) {
                                    mStageAdapter.updateList();
                                    mStageAdapter.notifyDataSetChanged();
                                }
                            }
                        });

                        return;
                    }
                    ToastUtil.showToast(RichOXStrategyFissionActivity.this, "获取进度失败");
                }

                @Override
                public void onFailed(int code, String reason) {
                    Log.d(Constants.TAG, "the code is :  " + code + " and reason is:  " + reason);
                    ToastUtil.showToast(RichOXStrategyFissionActivity.this, "获取进度失败");
                }
            });
        } else {
            ToastUtil.showToast(RichOXStrategyFissionActivity.this, "用户ID为空，请先登录");
        }
    }

    private void logList(List<StageItem> list) {
        Log.d(Constants.TAG, "the list info is: ");
        for (StageItem item : list) {
            Log.d(Constants.TAG, item.toString());
        }
    }

}
