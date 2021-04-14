package com.tgcenter.demo.richox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.base.RichOX;
import com.richox.base.RichOXUser;
import com.richox.base.UserType;
import com.richox.base.bean.user.SpecificUserInfo;
import com.richox.base.bean.user.UserBean;
import com.richox.base.bean.user.UserTokenBean;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.we.modoo.ModooHelper;
import com.we.modoo.callback.LoginCallback;
import com.we.modoo.core.LoginType;

import java.util.ArrayList;
import java.util.List;

public class RichOXCommonUserActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;

    private static String mWXCode;
    private static String mWXAppId = "wxf4443d19c4266f6b";
    private static String mSourceId = "";

    // user
    private static TextView mVisitorButton;
    private static TextView mWXLoginButton;
    private static TextView mWXRegisterButton;
    private static TextView mAccountBindButton;


    private static TextView mGetSpecialUserButton;
    private static TextView mGetTokenButton;


    private static TextView mGetUserButton;
    private static TextView mGetUserRanking;
    private static TextView mUnregisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_common_user);
        initView();
    }

    private void initView() {
        mVisitorButton = findViewById(R.id.richox_demo_user_register_vistor);
        mVisitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.registerVisitor(0, mSourceId, new CommonCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean user) {
                        Log.d(TAG, "the response is :" + user.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mWXLoginButton = findViewById(R.id.richox_demo_user_wx_login);
        mWXLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModooHelper.setLoginCallback(new LoginCallback() {
                    @Override
                    public void loginSuccess(String wxCode) {
                        Log.d(TAG, "wx login success and the code is " + wxCode);
                        mWXCode = wxCode;
                    }

                    @Override
                    public void loginCancel(String s) {
                        Log.d(TAG, "wx login cancel");
                    }

                    @Override
                    public void loginFailed(String s) {
                        Log.d(TAG, "wx login failed");
                    }
                });
                ModooHelper.login(LoginType.Wechat);
            }
        });

        mWXRegisterButton = findViewById(R.id.richox_demo_user_wx_register);
        mWXRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mWXCode)) {
                    Toast.makeText(RichOXCommonUserActivity.this, "wx code is null, please login wx first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.registerWithWechat(mWXAppId, mWXCode, mSourceId, new CommonCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean user) {
                        Log.d(TAG, "the response is :" + user.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mAccountBindButton = findViewById(R.id.richox_demo_user_bind_account);
        mAccountBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mWXCode)) {
                    Toast.makeText(RichOXCommonUserActivity.this, "wx code is null, please login wx first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.startBindAccount(UserType.TYPE_WECHAT, mWXAppId, mWXCode, new CommonCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean user) {
                        Log.d(TAG, "the response is :" + user);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mGetSpecialUserButton = findViewById(R.id.richox_demo_user_get_special_user);
        mGetSpecialUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> userList = new ArrayList<>();
                userList.add("6823c6e402f8b373");
                RichOXUser.getSpecificUsersInfo(userList, new CommonCallback<List<SpecificUserInfo>>() {
                    @Override
                    public void onSuccess(List<SpecificUserInfo> users) {
                        if (users != null && users.size() > 0) {
                            for (SpecificUserInfo user : users) {
                                Log.d(TAG, "the response is :" + user);
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


        mGetTokenButton = findViewById(R.id.richox_demo_user_get_token_info);
        mGetTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.getUserToken(new CommonCallback<UserTokenBean>() {
                    @Override
                    public void onSuccess(UserTokenBean tokenInfo) {
                        Log.d(TAG, "the response is :" + tokenInfo);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mGetUserRanking = findViewById(R.id.richox_demo_user_get_user_ranking);
        mGetUserRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.getUserRanking(5, 0, "coin", new CommonCallback<List<UserBean>>() {
                    @Override
                    public void onSuccess(List<UserBean> users) {
                        if (users != null && users.size() > 0) {
                            for (UserBean user : users) {
                                Log.d(TAG, "the response is :" + user);
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

        mGetUserButton = findViewById(R.id.richox_demo_user_get_user_info);
        mGetUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(RichOXCommonUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                RichOXUser.getUserInfo(new CommonCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean user) {
                        Log.d(TAG, "the response is :" + user);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mUnregisterButton = findViewById(R.id.richox_demo_user_unregister);
        mUnregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXUser.logout(new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean success) {
                        Log.d(TAG, "the response is :" + success);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
