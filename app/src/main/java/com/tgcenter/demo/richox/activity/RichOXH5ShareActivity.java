package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.base.CommonCallback;
import com.richox.sdk.RichOXH5;
import com.richox.sdk.RichOXH5Error;
import com.richox.sdk.ShareRegisterCallback;
import com.richox.sdk.ShareResultCallback;
import com.richox.sdk.core.scene.FloatScene;
import com.richox.sdk.core.scene.SceneListener;
import com.richox.sect.RichOXSect;
import com.richox.share.RichOXShare;
import com.richox.share.ShareCallback;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.we.modoo.ModooHelper;
import com.we.modoo.core.ShareType;
import com.we.modoo.share.IShare;

import java.util.HashMap;

/**
 * H5 分享活动需要涉及如下模块：
 * 裂变模块，宗门模块
 * <p>
 * 活动接入包括两部分：
 * （1） 绑定师徒关系需要APP侧完成
 * 进入应用后，首先需要调用裂变模块的还原接口，返回分享链接中的参数
 * 如果当前用户是裂变用户，需要调用宗门模块的异步绑定接口，异步绑定需新用户才可以绑定师徒关系
 * <p>
 * （2）发送分享
 * 加载 H5 活动，需要调用 RichOX H5 相关接口，完成活动加载。
 * 用户在分享过程中需要开发者做如下操作：
 * 生成分享 URL 时，需要开发者调用裂变模块生成分享链接接口。
 * 分享过程中，需要开发者调用微信分享接口。
 */

public class RichOXH5ShareActivity extends BaseActivity {
    private static final String TAG = "FloatFragment";
    private static final String UID = "uid";
    private TextView mLottieLoad;
    private FrameLayout mLottieContent;
    private ShareResultCallback mWechatShareCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5_share);
        initRichOXShare();
        initWechat();
        initRichOXH5();
        initView();

    }

    private void initRichOXShare() {
        RichOXShare.init(getApplicationContext());
        RichOXShare.getInstallParams(new ShareCallback<HashMap<String, Object>>() {
            @Override
            public void onSuccess(HashMap<String, Object> stringObjectHashMap) {
                String uid = (String) stringObjectHashMap.get(UID);
                // 宗门异步绑定
                RichOXSect.bindInviter(uid, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(TAG, "bind success");
                        // 上报师徒关系绑定
                        RichOXShare.reportBindEvent();
                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                });

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void initWechat() {
        // 注册微信分享，微信初始化在RichOXApplication中
        ModooHelper.registerShareCallback(new com.we.modoo.callback.ShareCallback() {
            @Override
            public void shareSuccess() {
                Toast.makeText(RichOXH5ShareActivity.this, "share success", Toast.LENGTH_SHORT).show();
                if (mWechatShareCallback != null) {
                    mWechatShareCallback.onResultForShare(0, null);
                }
            }

            @Override
            public void shareCancel() {
                Toast.makeText(RichOXH5ShareActivity.this, "share cancel", Toast.LENGTH_SHORT).show();
                mWechatShareCallback.onResultForShare(-1, "cancel");
            }

            @Override
            public void shareFailed() {
                Toast.makeText(RichOXH5ShareActivity.this, "share failed", Toast.LENGTH_SHORT).show();
                mWechatShareCallback.onResultForShare(-1, "failed");
            }
        });
    }

    private void initRichOXH5() {
        // RichOXH5 初始化
        RichOXH5.init(RichOXH5ShareActivity.this);

        RichOXH5.registerShareCallback(new ShareRegisterCallback() {
            @Override
            public void genShareUrl(String hostUrl, HashMap<String, Object> params, ShareResultCallback callback) {
                RichOXShare.genShareUrl(hostUrl, params, new ShareCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        callback.onResultForGen(s, 0, null);
                        // 准备分享上报
                        RichOXShare.reportOpenShare();
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        callback.onResultForGen(null, code, msg);
                    }
                });
            }

            @Override
            public void shareContent(String title, String content, byte[] bitmap, ShareResultCallback callback) {
                mWechatShareCallback = callback;
                // 分享海报
                ModooHelper.shareImageBytes(ShareType.WeChat, IShare.SHARE_TYPE_SESSION, bitmap);
                // 开始分享上报
                RichOXShare.reportStartShare();
            }
        });
    }

    private void initView() {
        mLottieLoad = findViewById(R.id.share_load_icon);
        mLottieContent = findViewById(R.id.share_float_lottie_content);
        // 加载活动
        FloatScene scene = new FloatScene(getApplicationContext(), "50133", mLottieContent);
        scene.setSceneListener(new SceneListener() {
            @Override
            public void onLoaded() {
                Log.d(TAG, "on loaded");
                if (scene.isReady()) {
                    Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLottieContent.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }

            @Override
            public void onLoadFailed(RichOXH5Error error) {
                Log.d(TAG, "on onLoadFailed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "加载失败：" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onShown() {
                Log.d(TAG, "on onShown");
            }

            @Override
            public void onRenderSuccess() {

            }

            @Override
            public void onRenderFailed(RichOXH5Error error) {

            }

            @Override
            public void onClick() {
                Log.d(TAG, "on onClick");
            }

            @Override
            public void onClose() {
                Log.d(TAG, "on onClose");
            }
        });
        mLottieLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.load();
            }
        });
    }
}
