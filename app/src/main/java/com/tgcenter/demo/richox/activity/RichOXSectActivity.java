package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.sect.RichOXSect;
import com.richox.sect.bean.ApprenticeInfo;
import com.richox.sect.bean.ApprenticeList;
import com.richox.sect.bean.AwardInfo;
import com.richox.sect.bean.ChiefInfo;
import com.richox.sect.bean.Contribution;
import com.richox.sect.bean.ContributionRecord;
import com.richox.sect.bean.RedPacketRecords;
import com.richox.sect.bean.SectInfo;
import com.richox.sect.bean.SectSettings;
import com.richox.sect.bean.TransformInfo;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RichOXSectActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;

    private final String TONG_ID = "0532f4e1c5bd385d";
    private final String APPRENTICE_ID = "c3b609ab75675697";
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
    private TextView mGetInviteReward;
    private TextView mGetInviteListConfig;
    private TextView mBindInviter;
    private TextView mGetStarDayRecord;
    private TextView mGetRedPacketRecord;
    private TextView mTransform;

    private TextView mSectSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_sect);
        initView();
    }

    private void initView() {
        mGetTongInfo = findViewById(R.id.richox_demo_sect_get_tong_info);
        mGetTongInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getSectInfo(new CommonCallback<SectInfo>() {
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
                        ToastUtil.showToast(RichOXSectActivity.this, "获取宗门信息成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is: " + code + " msg : " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "获取宗门失败： " + msg);
                    }
                });
            }
        });

        mUserStatus = findViewById(R.id.richox_demo_sect_get_user_status);
        mUserStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getUserSectStatus(new CommonCallback<Integer>() {
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
                RichOXSect.getApprenticeList(1, -1, -1, new CommonCallback<ApprenticeList>() {
                    @Override
                    public void onSuccess(ApprenticeList list) {
                        Log.d(TAG, list.toString());
                        for (ApprenticeInfo info : list.getApprenticeList()) {
                            Log.d(TAG, info.toString());
                        }
                        ToastUtil.showToast(RichOXSectActivity.this, "获取徒弟信息成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is: " + code + " msg : " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "获取徒弟信息失败： " + msg);
                    }
                });
            }
        });

        mGetStudentDetail = findViewById(R.id.richox_demo_sect_get_student_detail);
        mGetStudentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getApprenticeInfo("623b50f805ca6eed", new CommonCallback<ApprenticeInfo>() {
                    @Override
                    public void onSuccess(ApprenticeInfo student) {
                        if (student != null) {
                            Log.d(TAG, student.toString());
                            ToastUtil.showToast(RichOXSectActivity.this, "获取具体徒弟信息成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(RichOXSectActivity.this, "获取具体徒弟信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "获取具体徒弟信息失败： " + msg);
                    }
                });
            }
        });


        mGenStar = findViewById(R.id.richox_demo_sect_gen_star);
        mGenStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.genContribution(0, new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            if (contribution != null) {
                                Log.d(TAG, contribution.toString());
                                ToastUtil.showToast(RichOXSectActivity.this, "生成贡献成功");
                            } else {
                                Log.d(TAG, "student is null");
                                ToastUtil.showToast(RichOXSectActivity.this, "生成贡献信息为空");
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "生成贡献失败： " + msg);
                    }
                });

            }
        });


        mGetStar = findViewById(R.id.richox_demo_sect_get_star);
        mGetStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getContribution(APPRENTICE_ID, new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            Log.d(TAG, contribution.toString());
                            ToastUtil.showToast(RichOXSectActivity.this, "领取贡献成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(RichOXSectActivity.this, "领取贡献信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "领取贡献失败： " + msg);
                    }
                });
            }
        });

        mGetStarAll = findViewById(R.id.richox_demo_sect_get_star_all);
        mGetStarAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getAllContribution(new CommonCallback<Contribution>() {
                    @Override
                    public void onSuccess(Contribution contribution) {
                        if (contribution != null) {
                            Log.d(TAG, contribution.toString());
                            ToastUtil.showToast(RichOXSectActivity.this, "领取贡献成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(RichOXSectActivity.this, "领取贡献信息为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "领取贡献失败： " + msg);
                    }
                });
            }
        });

        mGetInviteListConfig = findViewById(R.id.richox_demo_sect_get_invite_config_list);
        mGetInviteListConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getInviteAwardList(new CommonCallback<List<SectSettings.InviteAward>>() {
                    @Override
                    public void onSuccess(List<SectSettings.InviteAward> list) {
                        if (list != null && list.size() > 0) {
                            ToastUtil.showToast(RichOXSectActivity.this, "领取邀请配置成功");
                            for (SectSettings.InviteAward item : list) {
                                Log.d(TAG, item.toString());
                            }
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(RichOXSectActivity.this, "领取邀请配置为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "领取邀请配置失败： " + msg);
                    }
                });
            }
        });

        mGetInviteReward = findViewById(R.id.richox_demo_sect_get_invite_award);
        mGetInviteReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getInviteAward(1, new CommonCallback<AwardInfo>() {
                    @Override
                    public void onSuccess(AwardInfo awardInfo) {
                        if (awardInfo != null) {
                            Log.d(TAG, awardInfo.toString());
                            ToastUtil.showToast(RichOXSectActivity.this, "领取邀请奖励成功");
                        } else {
                            Log.d(TAG, "student is null");
                            ToastUtil.showToast(RichOXSectActivity.this, "领取邀请奖励为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "领取邀请奖励失败： " + msg);
                    }
                });
            }
        });

        mBindInviter = findViewById(R.id.richox_demo_sect_bind_inviter);
        mBindInviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.bindInviter(TONG_ID, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        if (success) {
                            ToastUtil.showToast(RichOXSectActivity.this, "绑定成功");
                        } else {
                            ToastUtil.showToast(RichOXSectActivity.this, "绑定失败");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "绑定失败： " + msg);
                    }
                });
            }
        });

        mGetStarDayRecord = findViewById(R.id.richox_demo_sect_get_star_day_record);
        mGetStarDayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getContributionRecordByDay(2021, 2, new CommonCallback<Map<String, ContributionRecord>>() {
                    @Override
                    public void onSuccess(Map<String, ContributionRecord> recordsMap) {
                        Log.d(TAG, "the list is :");
                        if (recordsMap != null) {
                            Set<String> keys = recordsMap.keySet();
                            for (String date : keys) {
                                Log.d(TAG, "date : " + date + " , " + recordsMap.get(date).toString());
                            }
                        }
                        ToastUtil.showToast(RichOXSectActivity.this, "获取贡献记录成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "获取贡献记录失败： " + msg);
                    }
                });
            }
        });

        mGetRedPacketRecord = findViewById(R.id.richox_demo_sect_get_red_packet_record);
        mGetRedPacketRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getRedPacketRecord(new CommonCallback<RedPacketRecords>() {
                    @Override
                    public void onSuccess(RedPacketRecords packetRecords) {
                        if (packetRecords != null) {
                            ToastUtil.showToast(RichOXSectActivity.this, "获取红包记录成功");
                            Log.d(TAG, packetRecords.toString());
                            List<RedPacketRecords.Item> items = packetRecords.getRecordList();
                            Log.d(TAG, "the list info: ");
                            for (RedPacketRecords.Item item : items) {
                                Log.d(TAG, item.toString());
                            }
                        } else {
                            ToastUtil.showToast(RichOXSectActivity.this, "获取红包记录为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "获取红包记录失败： " + msg);
                    }
                });
            }
        });

        mTransform = findViewById(R.id.richox_demo_sect_transform);
        mTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.transform(new CommonCallback<TransformInfo>() {
                    @Override
                    public void onSuccess(TransformInfo transformInfo) {
                        if (transformInfo != null) {
                            ToastUtil.showToast(RichOXSectActivity.this, "进行现金兑换成功");
                            Log.d(TAG, transformInfo.toString());
                        } else {
                            ToastUtil.showToast(RichOXSectActivity.this, "进行现金兑换为空");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                        ToastUtil.showToast(RichOXSectActivity.this, "进行现金兑换失败： " + msg);
                    }
                });
            }
        });

        mSectSettings = findViewById(R.id.richox_demo_sect_get_settings);
        mSectSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXSect.getSettings(new CommonCallback<SectSettings>() {
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
                            Set<Integer> timesKeys = timeMaps.keySet();
                            for (Integer timeKey : timesKeys) {
                                Log.d(TAG, "兑换次数: " + timeKey);
                                Log.d(TAG, "对应的红包奖励值:");
                                double[] redBagList = timeMaps.get(timeKey);

                                for (int j = 0; j < redBagList.length; j++) {
                                    Log.d(TAG, "宗门等级：" + (j + 1) + " 奖励：" + redBagList[j]);
                                }
                            }
                            int[] grades = sectSettings.getGrades();
                            for (int j = 0; j < grades.length; j++) {
                                Log.d(TAG, "宗门等级： " + (j + 1) + " 需要邀请人数： " + grades[j]);
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
    }
}
