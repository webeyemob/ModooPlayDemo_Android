package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.richox.sdk.RichOXH5Error;
import com.richox.sdk.core.scene.NativeScene;
import com.richox.sdk.core.scene.SceneListener;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

public class RichOXH5NativeActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;
    private TextView mEnter;
    private TextView mShow;
    private ImageView mIconView;
    private TextView mTitleView;
    private TextView mDescView;
    private TextView mCTAView;
    private ViewGroup mLayoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5_native);
        initView();
    }

    private void initView() {
        mIconView = findViewById(R.id.native_icon);
        mTitleView = findViewById(R.id.native_title);
        mDescView = findViewById(R.id.native_desc);
        mCTAView = findViewById(R.id.native_cta);

        mLayoutRoot = findViewById(R.id.native_root_layout);

        mEnter = findViewById(R.id.native_load);
        NativeScene scene = new NativeScene(getApplicationContext(), "50076");
        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.setSceneListener(new SceneListener() {
                    @Override
                    public void onLoaded() {
                        Log.d(TAG, "on loaded");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                scene.load();
            }
        });

        mShow = findViewById(R.id.native_show);

        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scene.isReady()) {
                    NativeScene.NativeInfo info = scene.getNativeInfo();
                    if (info != null) {
                        mTitleView.setText(info.getTitle());
                        mDescView.setText(info.getDesc());
                        mCTAView.setText(info.getCTA());
                        Glide.with(RichOXH5NativeActivity.this)
                                .load(info.getIconUrl())
                                .into(mIconView);
                        scene.reportSceneShown();
                        mLayoutRoot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                scene.showScene();
                            }
                        });
                    }
                }
            }
        });
    }
}
