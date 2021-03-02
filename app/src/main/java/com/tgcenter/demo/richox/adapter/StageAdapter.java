package com.tgcenter.demo.richox.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richox.base.RichOX;
import com.richox.strategy.ROXStageStrategy;
import com.richox.strategy.mission.ResultCallback;
import com.richox.strategy.mission.bean.StageItem;
import com.richox.strategy.mission.bean.WithdrawInfo;
import com.tgcenter.demo.R;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class StageAdapter extends BaseAdapter {
    private Activity mActivity;
    private String mStrategyId;
    private List<StageItem> mStageList = new ArrayList<>();
    private String mMissionId;
    private RefreshCallback mCallback;

    public StageAdapter(Activity context, String strategyId, RefreshCallback callback) {
        mActivity = context;
        mStrategyId = strategyId;
        mCallback = callback;
        updateList();
    }

    public void setMissionId(String missionId) {
        mMissionId = missionId;
    }

    public void updateList() {
        mStageList = ROXStageStrategy.getInstance(mStrategyId).getList();
    }

    @Override
    public int getCount() {
        if (mStageList != null) {
            return mStageList.size();
        } else {
            return 0;
        }

    }

    @Override
    public StageItem getItem(int position) {
        return mStageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.layout_stage_item, null);

            TextView stageName = convertView.findViewById(R.id.demo_stage_name);
            ProgressBar stageProgress = convertView.findViewById(R.id.demo_stage_progress);
            TextView stageStatus = convertView.findViewById(R.id.demo_stage_submit);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mStageName = stageName;
            viewHolder.mStageProgress = stageProgress;
            viewHolder.mStageStatus = stageStatus;
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final StageItem item = mStageList.get(position);
        holder.mStageName.setText(item.getTitle());
        holder.mStageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mActivity, item.getId());
            }
        });
        Log.d(Constants.TAG, "current progress " + item.getPercent());
        holder.mStageProgress.setProgress((int) item.getPercent());
        if (((int) item.getPercent()) >= 100) {
            int withdrawStatus = item.getWithdrawStatus();
            if (withdrawStatus == 100) {
                holder.mStageStatus.setText("已经提现");
            } else {
                holder.mStageStatus.setText("立即提现");
            }
        } else {
            holder.mStageStatus.setText("领取任务");
        }
        holder.mStageStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((int) item.getPercent()) == 100) {
                    if (!TextUtils.isEmpty(RichOX.getUserId())) {
                        WithdrawInfo info = new WithdrawInfo.Builder().stageItemId(item.getId())
                                .grade(0)
                                .payMark("Test")
                                .comment(null)
                                .userRealName(null)
                                .userIDCard(null)
                                .userPhoneNumber(null)
                                .withdrawChannel(null)
                                .build();
                        ROXStageStrategy.getInstance(mStrategyId).withdraw(info, new ResultCallback<List<StageItem>>() {
                            @Override
                            public void onSuccess(List<StageItem> response) {
                                logList(response);
                                ToastUtil.showToast(mActivity, "提现成功");
                                mCallback.onSuccess();
                            }

                            @Override
                            public void onFailed(int code, String reason) {
                                Log.d(Constants.TAG, "the code is :  " + code + " and reason is:  " + reason);
                                ToastUtil.showToast(mActivity, "提现失败：" + reason);
                            }
                        });
                    } else {
                        ToastUtil.showToast(mActivity, "用户ID为空，请先登录");
                    }
                } else {
                    if (!TextUtils.isEmpty(RichOX.getUserId())) {
                        ROXStageStrategy.getInstance(mStrategyId).doMission(item.getId(), mMissionId, -1, new ResultCallback<List<StageItem>>() {
                            @Override
                            public void onSuccess(List<StageItem> response) {
                                if (response != null) {
                                    ToastUtil.showToast(mActivity, "领取任务成功");
                                    logList(mStageList);
                                    mCallback.onSuccess();
                                    return;
                                }
                                ToastUtil.showToast(mActivity, "领取任务失败");
                            }

                            @Override
                            public void onFailed(int code, String reason) {
                                Log.d(Constants.TAG, "the code is :  " + code + " and reason is:  " + reason);
                                ToastUtil.showToast(mActivity, "领取任务失败");
                            }
                        });
                    } else {
                        ToastUtil.showToast(mActivity, "用户ID为空，请先登录");
                    }
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView mStageName;
        public ProgressBar mStageProgress;
        public TextView mStageStatus;
    }

    private void logList(List<StageItem> list) {
        Log.d(Constants.TAG, "the list info is: ");
        for (StageItem item : list) {
            Log.d(Constants.TAG, item.toString());
        }
    }

    public interface RefreshCallback {
        void onSuccess();
    }
}
