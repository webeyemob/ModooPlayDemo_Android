package com.tgcenter.demo.richox.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richox.base.CommonCallback;
import com.richox.toolbox.RichOXToolbox;
import com.richox.toolbox.bean.ChatMessage;
import com.richox.toolbox.bean.GroupInfo;
import com.richox.toolbox.bean.PiggyBank;
import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.richox.constance.Constants;

import java.util.List;

public class ToolboxActivity extends BaseActivity {
    private final int PIGGY_ID = 1029;

    private TextView mQueryPiggyBank;
    private TextView mPiggyBankWithdraw;

    private TextView mFetchChatGroup;
    private TextView mFetchChatMessageList;
    private TextView mPushChatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richox_toolbox);
        initView();
    }


    private void initView() {
        mQueryPiggyBank = findViewById(R.id.demo_stage2_query_piggy_bank);
        mQueryPiggyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXToolbox.queryPiggyBankList(new CommonCallback<List<PiggyBank>>() {
                    @Override
                    public void onSuccess(List<PiggyBank> piggyBanks) {
                        for (PiggyBank bank : piggyBanks) {
                            Log.d(Constants.TAG, bank.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mPiggyBankWithdraw = findViewById(R.id.demo_stage2_piggy_bank_withdraw);
        mPiggyBankWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXToolbox.piggyBankWithdraw(PIGGY_ID, new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Log.d(Constants.TAG, aBoolean.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mFetchChatGroup = findViewById(R.id.demo_stage2_chat_get_group);
        mFetchChatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXToolbox.getGroupInfo(new CommonCallback<List<GroupInfo>>() {
                    @Override
                    public void onSuccess(List<GroupInfo> infoGroups) {
                        for (GroupInfo info : infoGroups) {
                            Log.d(Constants.TAG, info.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mFetchChatMessageList = findViewById(R.id.demo_stage2_chat_get_messages);
        mFetchChatMessageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXToolbox.getMessageList("d212052701788bed6802a13bdb03de82", 5, new CommonCallback<List<ChatMessage>>() {
                    @Override
                    public void onSuccess(List<ChatMessage> chatMessages) {
                        Log.d(Constants.TAG, "get chat message and length:" + chatMessages.size());
                        for (ChatMessage chatMessage : chatMessages) {
                            Log.d(Constants.TAG, chatMessage.toString());
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

        mPushChatMessage = findViewById(R.id.demo_stage2_chat_push_message);
        mPushChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichOXToolbox.postChatMessage("d212052701788bed6802a13bdb03de82", "raunch", "", "10", "war craft", new CommonCallback<ChatMessage>() {
                    @Override
                    public void onSuccess(ChatMessage chatMessage) {
                        Log.d(Constants.TAG, "the chatMesage " + chatMessage.toString());
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        Log.d(Constants.TAG, "the code is " + code + " the msg is : " + msg);
                    }
                });
            }
        });

    }

}
