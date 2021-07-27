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
import com.richox.share.ShareConstant;
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

    private TextView mBeginShareEvent;
    private TextView mStartShareEvent;
    private TextView mBindEvent;

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
//                map.put("uid", "123456");
                map.put(ShareConstant.FISSION_ACTIVITY_NAME, "testActivity");
                map.put(ShareConstant.FISSION_SHARE_CHANNEL_ID, "testChannel");
//                map.put("platform", "android");
                map.put("share_type", "share_book_detail");
                map.put("book_id", "af1a4a613969ff4a0fb8a8aa");
//                map.put(RichOXShare.DOMAIN_HOST, "https://novelnow.page.link");
//                map.put(RichOXShare.IOS_PARAMS_BUNDLE_ID, "com.novel.romance");
//                map.put(RichOXShare.IOS_PARAMS_APPSTORE_ID, "1552127129");
//                map.put(RichOXShare.IOS_PARAMS_FORCE_SHOW_PREVIEW, true);
                RichOXShare.genShareUrl("http://share.openmobiles.com/pages/novel_share_now.html", map, new ShareCallback<String>() {
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

        mBeginShareEvent = findViewById(R.id.richox_demo_share_event_begin_share);
        mBeginShareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXShare.reportOpenShare();
            }
        });

        mStartShareEvent = findViewById(R.id.richox_demo_share_event_start_share);
        mStartShareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXShare.reportStartShare();
            }
        });

        mBindEvent = findViewById(R.id.richox_demo_share_event_report_bind);
        mBindEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXShare.reportBindEvent();
            }
        });
    }

}
