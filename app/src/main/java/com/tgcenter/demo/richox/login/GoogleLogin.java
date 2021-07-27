package com.tgcenter.demo.richox.login;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tgcenter.demo.R;

public class GoogleLogin {
    private static int RC_SIGN_IN_GOOGLE = 9511;
    private static LoginCallback mLoginCallback;

    public static void googleLogin(Activity activity, LoginCallback callback) {
        mLoginCallback = callback;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getResources().getString(R.string.server_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        activity.startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN_GOOGLE);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account;
            try {
                account = task.getResult(ApiException.class);
                if (account != null) {
                    String token = account.getIdToken();
                    if (mLoginCallback != null) {
                        UserInfo userInfo = new UserInfo();
                        userInfo.mToken = token;
                        userInfo.mUserId = account.getId();
                        mLoginCallback.onSuccess(userInfo);
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
                if (mLoginCallback != null) {
                    mLoginCallback.onFailed(e.getMessage());
                }
            }
        }
    }
}
