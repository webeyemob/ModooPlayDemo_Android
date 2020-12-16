package com.tgcenter.demo;

import android.app.Application;

public class ModooPlayApplication extends Application {

    // 不要在 Application 中初始化 SDK，应该在用户同意《用户协议和隐私政策》之后初始化，参考 MainActivity
    @Override
    public void onCreate() {
        super.onCreate();
    }
}