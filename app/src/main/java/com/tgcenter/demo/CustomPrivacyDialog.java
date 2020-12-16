package com.tgcenter.demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nefarian.privacy.policy.BasePrivacyDialog;
import com.nefarian.privacy.policy.IPrivacyPolicyCallback;

public class CustomPrivacyDialog extends BasePrivacyDialog {

    private View mView;

    private TextView mTitleView;
    private TextView mDescriptionTextView;
    private TextView mAgreeView;
    private TextView mDisagreeView;

    private IPrivacyPolicyCallback mCallback;

    public CustomPrivacyDialog(Context context) {
        super(context);

        mView = View.inflate(context, R.layout.custom_privacy_dialog, null);
        initView();

        setContentView(mView);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setAgreeCallback(IPrivacyPolicyCallback callback) {
        mCallback = callback;
    }

    private void initView() {
        mTitleView = mView.findViewById(R.id.title);
        mDescriptionTextView = mView.findViewById(R.id.description);
        mAgreeView = mView.findViewById(R.id.agree_tv);
        mDisagreeView = mView.findViewById(R.id.disagree_tv);

        // 注意：自定义对话框中的文字内容不能修改！
        // 标题
        mTitleView.setText(getTitle());
        // 描述内容
        SpannableStringBuilder style = getDescriptionSpannableStringBuilder(Color.parseColor("#50D8A6"));
        mDescriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mDescriptionTextView.setText(style);
        // 同意按钮
        mAgreeView.setText(getAgreeText());
        // 不同意按钮
        mDisagreeView.setText(getDisagreeText());

        mAgreeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallback.onUserAgree();
            }
        });
        mDisagreeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onUserDisagree();
            }
        });
    }
}