package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.sect.ROXSect;
import com.richox.sect.bean.ApprenticeInfo;
import com.richox.sect.bean.ApprenticeList;
import com.richox.sect.bean.ChiefInfo;
import com.richox.sect.bean.Contribution;
import com.richox.sect.bean.SectInfo;
import com.richox.sect.bean.SectRankingInfo;
import com.richox.sect.bean.SectSettings;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ROXSectActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;

    private final String TONG_ID = "468d34ecb3a6590d";
    private final String APPRENTICE_ID = "9af741d7c08c4a0d";
    private ArrayList<ApprenticeInfo> mStudentList;
    private final String TONG_MISSION_ID = "";
    // data
    private TextView mGetTongInfo;
    private TextView mUserStatus;
    private TextView mGetStudents;
    private TextView mGetStudentDetail;
    private TextView mGenStar;
    private TextView mGetStar;
    private TextView mGetStarAll;
    private TextView mSectSettings;
    private TextView mBindInviter;
    private TextView mRanking;
    private TextView mCounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rox_sect);
        initView();
    }

    private void initView() {
        mGetTongInfo = findViewById(R.id.richox_demo_sect_get_tong_info);
        mGetTongInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getSectInfo(new CommonCallback<SectInfo>() {
                    @Override
                    public void onSuccess(SectInfo tongInfo) {
                        ChiefInfo chiefInfo = tongInfo.getChiefInfo();
                        if (chiefInfo != null) {
                            Log.d(TAG, tongInfo.getChiefInfo().toString());
                            HashMap rewardMap = chiefInfo.getInviteAwardMap();
                            Set<String> keys = rewardMap.keySet();
                            for (String key : keys) {
                                Log.d(TAG, "key is " + key + " and value is " + rewardMap.get(key));
                            }
                        }


                        if (tongInfo != null) {
                            HashMap map = tongInfo.getApprenticeMap();
                            Set<String> keys = map.keySet();
                            for (String level : keys) {
                                Log.d(TAG, "the level is " + level);
                                ApprenticeList list = (ApprenticeList) map.get(level);
                                Log.d(TAG, "the level " + level + " has total : " + list.getTotal());
                                for (ApprenticeInfo info : list.getApprenticeList()) {
                                    Log.d(TAG, info.toString());
                                }
                            }
                        }
                        ToastUtil.showToast(ROXSectActivity.this, "获取宗门信息成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is: " + code + " msg : " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "获取宗门失败： " + msg);
                    }
                });
            }
        });

        mUserStatus = findViewById(R.id.richox_demo_sect_get_user_status);
        mUserStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getUserSectStatus(new CommonCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "the user status : " + integer);
                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                });
            }
        });

        mGetStudents = findViewById(R.id.richox_demo_sect_get_students);
        mGetStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getApprenticeList(1, -1, -1, new CommonCallback<ApprenticeList>() {
                    @Override
                    public void onSuccess(ApprenticeList list) {
                        Log.d(TAG, list.toString());
                        for (ApprenticeInfo info : list.getApprenticeList()) {
                            Log.d(TAG, info.toString());
                        }
                        ToastUtil.showToast(ROXSectActivity.this, "获取徒弟信息成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is: " + code + " msg : " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "获取徒弟信息失败： " + msg);
                    }
                });
            }
        });

        mGetStudentDetail = findViewById(R.id.richox_demo_sect_get_student_detail);
        mGetStudentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getApprenticeInfo(APPRENTICE_ID, new CommonCallback<ApprenticeInfo>() {
                    @Override
                    public void onSuccess(ApprenticeInfo student) {
                        if (student != null) {
                            Log.d(TAG, student.toString());
                            ToastUtil.showToast(ROXSectActivity.this, "获取具体徒弟信息成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(ROXSectActivity.this, "获取具体徒弟信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "获取具体徒弟信息失败： " + msg);
                    }
                });
            }
        });


        mGenStar = findViewById(R.id.richox_demo_sect_gen_star);
        mGenStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.genContribution(0, new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            if (contribution != null) {
                                Log.d(TAG, contribution.toString());
                                ToastUtil.showToast(ROXSectActivity.this, "生成贡献成功");
                            } else {
                                Log.d(TAG, "student is null");
                                ToastUtil.showToast(ROXSectActivity.this, "生成贡献信息为空");
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "生成贡献失败： " + msg);
                    }
                });

            }
        });


        mGetStar = findViewById(R.id.richox_demo_sect_get_star);
        mGetStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getContribution(APPRENTICE_ID, new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            Log.d(TAG, contribution.toString());
                            ToastUtil.showToast(ROXSectActivity.this, "领取贡献成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(ROXSectActivity.this, "领取贡献信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "领取贡献失败： " + msg);
                    }
                });
            }
        });

        mGetStarAll = findViewById(R.id.richox_demo_sect_get_star_all);
        mGetStarAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getAllContribution(new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            Log.d(TAG, contribution.toString());
                            ToastUtil.showToast(ROXSectActivity.this, "领取贡献成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(ROXSectActivity.this, "领取贡献信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "领取贡献失败： " + msg);
                    }
                });
            }
        });

        mBindInviter = findViewById(R.id.richox_demo_sect_bind_inviter);
        mBindInviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.bindInviter(TONG_ID, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            ToastUtil.showToast(ROXSectActivity.this, "绑定成功");
                        } else {
                            ToastUtil.showToast(ROXSectActivity.this, "绑定失败");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "绑定失败： " + msg);
                    }
                });
            }
        });

        mSectSettings = findViewById(R.id.richox_demo_sect_get_settings);
        mSectSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getSettings(new CommonCallback<SectSettings>() {
                    @Override
                    public void onSuccess(SectSettings sectSettings) {
                        if (sectSettings != null) {
                            Log.d(TAG, sectSettings.toString());
                            List<SectSettings.InviteAward> list = sectSettings.getAwardSettingsList();
                            Log.d(TAG, "邀请人数奖励信息如下: ");
                            for (SectSettings.InviteAward inviteAward : list) {
                                Log.d(TAG, inviteAward.toString());
                            }
                            HashMap<Integer, Integer> transformStep = sectSettings.getTransformStep();
                            Set<Integer> keys = transformStep.keySet();
                            for (Integer key : keys) {
                                Log.d(TAG, "档位: " + key + " 需要消耗贡献值: " + transformStep.get(key));
                            }
                            HashMap<Integer, double[]> timeMaps = sectSettings.getTransformTimesMap();
                            if (timeMaps != null) {
                                Set<Integer> timesKeys = timeMaps.keySet();
                                for (Integer timeKey : timesKeys) {
                                    Log.d(TAG, "兑换次数: " + timeKey);
                                    Log.d(TAG, "对应的红包奖励值:");
                                    double[] redBagList = timeMaps.get(timeKey);

                                    if (redBagList != null) {
                                        for (int j = 0; j < redBagList.length; j++) {
                                            Log.d(TAG, "宗门等级：" + (j + 1) + " 奖励：" + redBagList[j]);
                                        }
                                    }
                                }

                            }
                            int[] grades = sectSettings.getGrades();
                            if (grades != null) {
                                for (int j = 0; j < grades.length; j++) {
                                    Log.d(TAG, "宗门等级： " + (j + 1) + " 需要邀请人数： " + grades[j]);
                                }
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

        mRanking = findViewById(R.id.richox_demo_sect_ranking);
        mRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getInviteRanking(new CommonCallback<List<SectRankingInfo>>() {
                    @Override
                    public void onSuccess(List<SectRankingInfo> sectRankingInfos) {
                        if (sectRankingInfos != null) {
                            for (SectRankingInfo sectRankingInfo : sectRankingInfos) {
                                Log.d(TAG, sectRankingInfo.toString());
                                ToastUtil.showToast(ROXSectActivity.this, "获取排行成功");
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "获取排行失败： " + msg);
                    }
                });
            }
        });

        mCounts = findViewById(R.id.richox_demo_sect_invite_counts);
        mCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXSect.getInviterCounts(-1, -1, new CommonCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "Total student is : " + integer.intValue());
                        ToastUtil.showToast(ROXSectActivity.this, "获取邀请总人数成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(ROXSectActivity.this, "获取总人数失败： " + msg);
                    }
                });
            }
        });
    }
}
