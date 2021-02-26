package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.base.RichOXCommon;
import com.richox.base.RichOXTask;
import com.richox.base.bean.task.RecentDaysTaskBean;
import com.richox.base.bean.task.TaskCoinsBean;
import com.richox.base.bean.task.TaskConfigBean;
import com.richox.base.bean.task.TaskCountBean;
import com.richox.base.bean.task.TaskRecordBean;
import com.richox.base.bean.task.TaskRewardedBean;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.List;

public class RichOXCommonTaskActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;
    // task
    private static TextView mQueryAllTasks;
    private static TextView mSubmitTaskButton;
    private static TextView mDoubleRewardButton;
    private static TextView mQueryTaskRecordsButton;
    private static TextView mQuerySubmitCountsTasks;
    private static TextView mQueryTodayCoinsTasks;

    private static String mRecordId;
    private static String mTaskId = "random_redpack";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_common_task);
        initView();
    }

    private void initView() {

        mQueryAllTasks = findViewById(R.id.richox_demo_task_query_all_tasks);
        mQueryAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonTaskActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXTask.queryAllTasks(new CommonCallback<List<TaskConfigBean>>() {
                    @Override
                    public void onSuccess(List<TaskConfigBean> taskConfigs) {
                        if (taskConfigs != null && taskConfigs.size() > 0) {
                            for (TaskConfigBean config : taskConfigs) {
                                Log.d(TAG, "the task config is :" + config);
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mSubmitTaskButton = findViewById(R.id.richox_demo_task_submit_task);
        mSubmitTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonTaskActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXTask.startSubmitTask(mTaskId, 50, 0, new CommonCallback<TaskRewardedBean>() {
                    @Override
                    public void onSuccess(TaskRewardedBean submitTaskData) {
                        Log.d(TAG, "the response is :" + submitTaskData);
                        mRecordId = submitTaskData.getRecord().getId();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mDoubleRewardButton = findViewById(R.id.richox_demo_task_submit_double);
        mDoubleRewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXTask.startMultiplyTask(mTaskId, mRecordId, 2, new CommonCallback<TaskRewardedBean>() {
                    @Override
                    public void onSuccess(TaskRewardedBean submitTaskData) {
                        Log.d(TAG, "the response is :" + submitTaskData);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mQueryTaskRecordsButton = findViewById(R.id.richox_demo_task_query_task);
        mQueryTaskRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonTaskActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXTask.startQueryRecentDayTasks(mTaskId, 1, -1, -1, new CommonCallback<RecentDaysTaskBean>() {
                    @Override
                    public void onSuccess(RecentDaysTaskBean taskRecords) {
                        if (taskRecords != null) {
                            Log.d(TAG, "the response is :" + taskRecords);
                            List<TaskRecordBean> list = taskRecords.mRecords;
                            Log.d(TAG, "the record list is: ");
                            for (TaskRecordBean bean : list) {
                                Log.d(TAG, bean.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mQuerySubmitCountsTasks = findViewById(R.id.richox_demo_task_query_submit_count);
        mQuerySubmitCountsTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonTaskActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXTask.startQueryTaskCountInfo(mTaskId, 1, new CommonCallback<TaskCountBean>() {
                    @Override
                    public void onSuccess(TaskCountBean taskRecords) {
                        Log.d(TAG, "the response is :" + taskRecords);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mQueryTodayCoinsTasks = findViewById(R.id.richox_demo_task_query_today_coins);
        mQueryTodayCoinsTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOXCommon.hasInitiated()) {
                    Toast.makeText(RichOXCommonTaskActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXTask.startGetTodayCoins(new CommonCallback<TaskCoinsBean>() {
                    @Override
                    public void onSuccess(TaskCoinsBean taskRecords) {
                        Log.d(TAG, "the response is :" + taskRecords);
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
