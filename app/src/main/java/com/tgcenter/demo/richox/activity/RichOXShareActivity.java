package com.tgcenter.demo.richox.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richox.share.RichOXShare;
import com.richox.share.ShareCallback;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.HashMap;
import java.util.Set;

public class RichOXShareActivity extends BaseActivity {
    private TextView mGenScene;
    private TextView mGenQRCode;
    private TextView mGenQRCodeBytes;
    private ImageView mQRImageView;

    private TextView mRestoreScene;

    private String mShareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_share);
        init();
        initView();
    }

    private void init() {
        RichOXShare.init(RichOXShareActivity.this);
    }

    private void initView() {
        mGenScene = findViewById(R.id.richox_demo_share_gen_scene);
        mGenScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", "123456");
                RichOXShare.genShareUrl("http://share.msgcarry.cn/share/doudizhu.html", map, new ShareCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mShareUrl = result;
                        Log.d(Constants.TAG, "the result is " + result);

                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is " + msg);
                    }
                });
            }
        });

        mGenQRCodeBytes = findViewById(R.id.richox_demo_share_gen_qr_bytes);
        mGenQRCodeBytes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShareUrl != null) {
                    byte[] bitmap = RichOXShare.getQRCodeBytes(mShareUrl, 200, 200);
                    if (mQRImageView != null) {
                        mQRImageView.setImageBitmap(BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length));
                    }
                }
            }
        });

        mGenQRCode = findViewById(R.id.richox_demo_share_gen_qr);
        mGenQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShareUrl != null) {
                    Bitmap bitmap = RichOXShare.getQRCodeBitmap(mShareUrl, 200, 200);
                    if (mQRImageView != null) {
                        mQRImageView.setImageBitmap(bitmap);
                    }
                }
            }
        });
        mQRImageView = findViewById(R.id.richox_demo_share_qr_view);

        mRestoreScene = findViewById(R.id.richox_demo_share_restore_scene);
        mRestoreScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXShare.getInstallParams(new ShareCallback<HashMap<String, Object>>() {
                    @Override
                    public void onSuccess(HashMap<String, Object> params) {
                        if (params != null) {
                            Log.d(Constants.TAG, "get the params:");
                            Set<String> keys = params.keySet();
                            for (String key : keys) {
                                Log.d(Constants.TAG, "the key is" + key + " the value is " + params.get(key));
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is " + msg);
                    }
                });
            }
        });
    }


}
