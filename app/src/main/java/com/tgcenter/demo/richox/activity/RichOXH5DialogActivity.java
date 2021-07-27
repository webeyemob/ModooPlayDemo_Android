package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.sdk.RichOXH5Error;
import com.richox.sdk.core.scene.DialogEnterCallback;
import com.richox.sdk.core.scene.DialogScene;
import com.richox.sdk.core.scene.SceneListener;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

public class RichOXH5DialogActivity extends BaseActivity {
    private static final String TAG = Constants.TAG;
    private TextView mLoadView;
    private TextView mShowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5_dialog);
        initView();
    }

    private void initView() {
        DialogScene scene = new DialogScene(getApplicationContext(), "50109");
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
        scene.setDialogCallback(new DialogEnterCallback() {
            @Override
            public void login() {
                Log.d(TAG, "the dialog login");
            }

            @Override
            public void cancel() {
                Log.d(TAG, "the dialog cancel");
            }
        });
        mLoadView = findViewById(R.id.dialog_load);
        mLoadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scene.load();
            }
        });
        mShowView = findViewById(R.id.dialog_show);
        mShowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scene.isReady()) {
                    scene.showDialog();
                } else {
                    Log.d(TAG, "no ready ....");
                }
            }
        });
    }
}
