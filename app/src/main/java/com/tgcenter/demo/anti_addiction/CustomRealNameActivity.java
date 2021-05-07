package com.tgcenter.demo.anti_addiction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tgcenter.demo.R;
import com.tgcenter.unified.antiaddiction.api.AntiAddiction;
import com.tgcenter.unified.antiaddiction.api.event.EventManager;
import com.tgcenter.unified.antiaddiction.api.event.RealNameEvent;
import com.tgcenter.unified.antiaddiction.api.realname.RealNameCallback;
import com.tgcenter.unified.antiaddiction.api.user.User;

public class CustomRealNameActivity extends AppCompatActivity {

    private final String TAG = "CustomUIActivity";

    private EditText mNameEditText;
    private EditText mIdNumberEditText;

    private Button mRealNameButton;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anti_addiction_customui);
        initView();

        EventManager.INSTANCE.callbackRealNameEvent(
                new RealNameEvent(RealNameEvent.Source.Custom_UI, RealNameEvent.Action.Show));
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        mNameEditText = findViewById(R.id.editText_name);
        mIdNumberEditText = findViewById(R.id.editText_idNumber);

        mRealNameButton = findViewById(R.id.button_real_name);
        mRealNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = AntiAddiction.getInstance().getUser();
                if (user.isTourist()) {
                    if (user.getRealNameResult().isProcessing()) {
                        toast("RealName is processing, please wait...");
                    } else {
                        realName(mNameEditText.getText().toString(),
                                mIdNumberEditText.getText().toString());
                    }
                } else {
                    toast("RealName Success");
                }
            }
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle(com.tgcenter.unified.antiaddiction.R.string.realname_process_wait);
        mProgressDialog.setIndeterminate(true);
    }

    // 实名认证，使用自定义 UI
    private void realName(String name, String idNumber) {
        showProgressDialog();
        AntiAddiction.getInstance().realName(name, idNumber, new RealNameCallback() {
            @Override
            public void onFinish(User user) {
                hideProgressDialog();
                toast("realName onFinish: " + user);
                finish();
            }
        });
    }

    private void showProgressDialog() {
        if (mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            EventManager.INSTANCE.callbackRealNameEvent(
                    new RealNameEvent(RealNameEvent.Source.Custom_UI, RealNameEvent.Action.Close));
        }
    }
}