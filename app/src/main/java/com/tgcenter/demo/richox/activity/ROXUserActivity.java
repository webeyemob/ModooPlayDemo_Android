package com.tgcenter.demo.richox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.base.ROXUser;
import com.richox.base.RichOX;
import com.richox.base.bean.user.ROXUserInfo;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;
import com.tgcenter.demo.richox.login.FacebookLogin;
import com.tgcenter.demo.richox.login.GoogleLogin;
import com.tgcenter.demo.richox.login.LoginCallback;
import com.tgcenter.demo.richox.login.UserInfo;
import com.tgcenter.demo.richox.util.ToastUtil;

public class ROXUserActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;

    private static String mWXCode;
    private static String mWXAppId = "wxf4443d19c4266f6b";
    private static String mSourceId = "";

    // user
    private TextView mVisitorButton;

    private TextView mFBLogin;
    private TextView mFBRegister;

    private TextView mGoogleLogin;
    private TextView mGoogleRegister;

    private TextView mBind;

    private TextView mGetUserInfo;

    private TextView mInviterInfo;

    private TextView mBindInviter;

    private TextView mLogout;

    private String mFBOpenId = "addadfaefefsdfdfcdd";
    private String mFBToken = "dafkekekekekekekeke";

    private String mGoogleToken = "efeesfs03838838888888888888888888888";
    private String mGoogleId = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rox_user);
        initView();
    }

    private void initView() {
        mVisitorButton = findViewById(R.id.richox_demo_roxuser_register_vistor);
        mVisitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RichOX.hasInitiated()) {
                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
                    return;
                }
                ROXUser.registerVisitor(null, new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mFBLogin = findViewById(R.id.richox_demo_roxuser_fb_login);
        mFBLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookLogin.facebookLogin(ROXUserActivity.this, new LoginCallback() {
                    @Override
                    public void onSuccess(UserInfo user) {
                        if (user != null) {
                            mFBOpenId = user.mUserId;
                            mFBToken = user.mToken;
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.d(TAG, "the msg : " + msg);
                    }
                });

            }
        });

        mFBRegister = findViewById(R.id.richox_demo_roxuser_fb_register);
        mFBRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.registerWithFacebook(mFBOpenId, mFBToken, new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mGoogleLogin = findViewById(R.id.richox_demo_roxuser_google_login);
        mGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleLogin.googleLogin(ROXUserActivity.this, new LoginCallback() {
                    @Override
                    public void onSuccess(UserInfo user) {
                        if (user != null) {
                            mGoogleToken = user.mToken;
                            mGoogleId = user.mUserId;
                            Log.d(TAG, "the google token: " + mGoogleToken);
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.d(TAG, "the  msg : " + msg);
                    }
                });
            }
        });

        mGoogleRegister = findViewById(R.id.richox_demo_roxuser_google_register);
        mGoogleRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.registerWithGoogle(mGoogleToken, new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mBind = findViewById(R.id.richox_demo_roxuser_bind_account);
        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ROXUser.startBindAccount("facebook", mFBOpenId, mFBToken, new CommonCallback<ROXUserInfo>() {
//                    @Override
//                    public void onSuccess(ROXUserInfo roxUserInfo) {
//                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
//                    }
//                });
                ROXUser.startBindAccount("google", mGoogleId, mGoogleToken, new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mGetUserInfo = findViewById(R.id.richox_demo_roxuser_get_user_info);
        mGetUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.getUserInfo(new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mInviterInfo = findViewById(R.id.richox_demo_roxuser_get_teacher);
        mInviterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.startRetrieveInviter(new CommonCallback<ROXUserInfo>() {
                    @Override
                    public void onSuccess(ROXUserInfo roxUserInfo) {
                        Log.d(TAG, "the user info : " + roxUserInfo.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

        mBindInviter = findViewById(R.id.richox_demo_roxuser_bind_inviter);
        mBindInviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.bindInviter("r_c794788841c703d5", new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        ToastUtil.showToast(ROXUserActivity.this, "绑定成功");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtil.showToast(ROXUserActivity.this, "绑定失败");
                        Log.d(TAG, "code is " + code + " msg: " + msg);
                    }
                });
            }
        });

        mLogout = findViewById(R.id.richox_demo_roxuser_unregister);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ROXUser.logout(new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(TAG, "unregister success");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(TAG, "the code is: " + code + " msg : " + msg);
                    }
                });
            }
        });

//        mWXLoginButton = findViewById(R.id.richox_demo_user_wx_login);
//        mWXLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ModooHelper.setLoginCallback(new LoginCallback() {
//                    @Override
//                    public void loginSuccess(String wxCode) {
//                        Log.d(TAG, "wx login success and the code is " + wxCode);
//                        mWXCode = wxCode;
//                    }
//
//                    @Override
//                    public void loginCancel(String s) {
//                        Log.d(TAG, "wx login cancel");
//                    }
//
//                    @Override
//                    public void loginFailed(String s) {
//                        Log.d(TAG, "wx login failed");
//                    }
//                });
//                ModooHelper.login(LoginType.Wechat);
//            }
//        });
//
//        mWXRegisterButton = findViewById(R.id.richox_demo_user_wx_register);
//        mWXRegisterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(mWXCode)) {
//                    Toast.makeText(ROXUserActivity.this, "wx code is null, please login wx first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                RichOXUser.registerWithWechat(mWXAppId, mWXCode, mSourceId, new CommonCallback<UserBean>() {
//                    @Override
//                    public void onSuccess(UserBean user) {
//                        Log.d(TAG, "the response is :" + user.toString());
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//        mAccountBindButton = findViewById(R.id.richox_demo_user_bind_account);
//        mAccountBindButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(mWXCode)) {
//                    Toast.makeText(ROXUserActivity.this, "wx code is null, please login wx first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                RichOXUser.startBindAccount(UserType.TYPE_WECHAT, mWXAppId, mWXCode, new CommonCallback<UserBean>() {
//                    @Override
//                    public void onSuccess(UserBean user) {
//                        Log.d(TAG, "the response is :" + user);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//        mGetSpecialUserButton = findViewById(R.id.richox_demo_user_get_special_user);
//        mGetSpecialUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                List<String> userList = new ArrayList<>();
//                userList.add("6823c6e402f8b373");
//                RichOXUser.getSpecificUsersInfo(userList, new CommonCallback<List<SpecificUserInfo>>() {
//                    @Override
//                    public void onSuccess(List<SpecificUserInfo> users) {
//                        if (users != null && users.size() > 0) {
//                            for (SpecificUserInfo user : users) {
//                                Log.d(TAG, "the response is :" + user);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//
//        mGetTokenButton = findViewById(R.id.richox_demo_user_get_token_info);
//        mGetTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                RichOXUser.getUserToken(new CommonCallback<UserTokenBean>() {
//                    @Override
//                    public void onSuccess(UserTokenBean tokenInfo) {
//                        Log.d(TAG, "the response is :" + tokenInfo);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//        mGetUserRanking = findViewById(R.id.richox_demo_user_get_user_ranking);
//        mGetUserRanking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                RichOXUser.getUserRanking(5, 0, "coin", new CommonCallback<List<UserBean>>() {
//                    @Override
//                    public void onSuccess(List<UserBean> users) {
//                        if (users != null && users.size() > 0) {
//                            for (UserBean user : users) {
//                                Log.d(TAG, "the response is :" + user);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//        mGetUserButton = findViewById(R.id.richox_demo_user_get_user_info);
//        mGetUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!RichOX.hasInitiated()) {
//                    Toast.makeText(ROXUserActivity.this, "Not init, please init first", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                RichOXUser.getUserInfo(new CommonCallback<UserBean>() {
//                    @Override
//                    public void onSuccess(UserBean user) {
//                        Log.d(TAG, "the response is :" + user);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
//
//        mUnregisterButton = findViewById(R.id.richox_demo_user_unregister);
//        mUnregisterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RichOXUser.logout(new CommonCallback<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean success) {
//                        Log.d(TAG, "the response is :" + success);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        Log.d(TAG, "code is " + code + " msg: " + msg);
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleLogin.onActivityResult(requestCode, resultCode, data);
        FacebookLogin.onActivityResult(requestCode, resultCode, data);
    }


}
