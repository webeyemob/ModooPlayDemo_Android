package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.sdk.RichOXH5Error;
import com.richox.sdk.core.scene.FloatScene;
import com.richox.sdk.core.scene.SceneListener;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;

public class RichOXH5FloatActivity extends BaseActivity {
    private static final String TAG = "FloatFragment";
    private TextView mLottieLoad;
    private FrameLayout mLottieContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5_floatview);
        initView();
    }

    private void initView() {
        mLottieLoad = findViewById(R.id.float_lottie);
        mLottieContent = findViewById(R.id.float_lottie_content);
        final FloatScene scene = new FloatScene(getApplicationContext(), "50121", mLottieContent);
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
            public void onLoadFailed(final RichOXH5Error error) {
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
