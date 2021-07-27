package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.base.RichOX;
import com.richox.base.RichOXWithDraw;
import com.richox.base.bean.withdraw.WithdrawBean;
import com.richox.base.bean.withdraw.WithdrawMission;
import com.richox.base.bean.withdraw.WithdrawMissionsBean;
import com.richox.base.bean.withdraw.WithdrawRecord;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.List;

public class RichOXCommonWithdrawActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;

    private static String mRealName = "张**";
    private static String mPhoneNumber = "13770561390";
    private static String mComment = "test";
    private static String mAddress = "nanjing jiangsu";
    private static String mIdCard = "3209111985********";
    private static String mTaskIdWX = "zongmen_withdraw_contribution";
    private static String mTaskIdNormal = "zongmen_withdraw_invite_3";
    private static String mWithdrawChannel = "";
    // withdraw
    private static TextView mWXSmallWithdrawButton;
    private static TextView mWXBigWithdrawButton;
    private static TextView mQueryWithdrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_common_withdraw);
        initView();
    }

    private void initView() {
        mWXSmallWithdrawButton = findViewById(R.id.richox_demo_withdraw_wx_small_withdraw);
        mWXSmallWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonWithdrawActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXWithDraw.startRequestWXWithdraw(mTaskIdWX, mComment, new CommonCallback<WithdrawBean>() {
                    @Override
                    public void onSuccess(WithdrawBean withdrawBean) {
                        Log.d(TAG, "the response is :" + withdrawBean.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mWXBigWithdrawButton = findViewById(R.id.richox_demo_withdraw_wx_big_withdraw);
        mWXBigWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonWithdrawActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXWithDraw.startRequestWithdraw(mTaskIdNormal, mRealName, mIdCard, mPhoneNumber, mWithdrawChannel, new CommonCallback<WithdrawBean>() {
                    @Override
                    public void onSuccess(WithdrawBean withdrawBean) {
                        Log.d(TAG, "the response is :" + withdrawBean.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });


        mQueryWithdrawButton = findViewById(R.id.richox_demo_withdraw_wx_query_withdraw);
        mQueryWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonWithdrawActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXWithDraw.startQueryWithdraw(new CommonCallback<WithdrawMissionsBean>() {
                    @Override
                    public void onSuccess(WithdrawMissionsBean withdrawMissionsBean) {
                        List<WithdrawRecord> records = withdrawMissionsBean.getRecords();
                        Log.d(TAG, "the records is :");
                        for (WithdrawRecord record : records) {
                            Log.d(TAG, record.toString());
                        }
                        List<WithdrawMission> cashMissions = withdrawMissionsBean.getCashMissions();
                        Log.d(TAG, "the cashMissions is :");
                        for (WithdrawMission record : cashMissions) {
                            Log.d(TAG, record.toString());
                        }
                        List<WithdrawMission> missions = withdrawMissionsBean.getMissions();
                        Log.d(TAG, "the missions is :");
                        for (WithdrawMission record : missions) {
                            Log.d(TAG, record.toString());
                        }
                        List<WithdrawMission> cashRangeMissions = withdrawMissionsBean.getCashRangeMissions();
                        Log.d(TAG, "the cashRangeMissions is :");
                        for (WithdrawMission record : missions) {
                            Log.d(TAG, record.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });
    }
}
