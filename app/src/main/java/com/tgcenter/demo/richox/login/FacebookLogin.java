package com.tgcenter.demo.richox.login;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookLogin {
    private static CallbackManager mFBCallback;
    private static Activity mActivity;

    public static void facebookLogin(Activity activity, final LoginCallback loginCallback) {
        mActivity = activity;
        mFBCallback = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFBCallback,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        String token = loginResult.getAccessToken().getToken();
                        String openId = loginResult.getAccessToken().getUserId();
                        UserInfo userInfo = new UserInfo();
                        userInfo.mToken = token;
                        userInfo.mUserId = openId;
                        if (loginCallback != null) {
                            loginCallback.onSuccess(userInfo);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        if (loginCallback != null) {
                            loginCallback.onFailed("onCancel");
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        if (loginCallback != null) {
                            loginCallback.onFailed(exception.getMessage());
                        }
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile"));
    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFBCallback != null) {
            mFBCallback.onActivityResult(requestCode, resultCode, data);
        }
    }
}
