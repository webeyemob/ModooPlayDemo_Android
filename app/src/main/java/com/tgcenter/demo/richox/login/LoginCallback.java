package com.tgcenter.demo.richox.login;

public interface LoginCallback {
    void onSuccess(UserInfo user);

    void onFailed(String msg);
}
