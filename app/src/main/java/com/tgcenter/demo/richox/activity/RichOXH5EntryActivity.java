package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.richox.sdk.RichOXH5Error;
import com.richox.sdk.core.scene.EnterScene;
import com.richox.sdk.core.scene.SceneListener;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;

public class RichOXH5EntryActivity extends BaseActivity {
    private static final String TAG = "DialogFragment";
    private TextView mEnter;
    private TextView mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_h5_entry);
        initView();
    }

    private void initView() {
        mEnter = findViewById(R.id.enter_button);
        EnterScene scene = new EnterScene(getApplicationContext(), "50129");
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

        mShow = findViewById(R.id.show_button);

        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scene.isReady()) {
                    scene.showScene();
                }
            }
        });
    }

}
