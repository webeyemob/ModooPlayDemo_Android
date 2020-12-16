package com.tgcenter.demo.ads;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.tgcenter.demo.R;
import com.tgcenter.demo.ads.adtype.AdTypeActivity;
import com.tgcenter.demo.ads.base.BaseActivity;
import com.tgcenter.demo.ads.utils.Constant;
import com.tgcenter.demo.ads.utils.Utils;

import org.json.JSONObject;

import java.util.List;

public class NetworkAdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network);
        getSupportActionBar().setTitle(R.string.ad_test);

        GridView listView = findViewById(R.id.listView);

        List<JSONObject> mediationList = Utils.getMediationList(NetworkAdActivity.this, "network_ad.json");
        NetworkAdapter adapter = new NetworkAdapter(NetworkAdActivity.this, mediationList);
        listView.setAdapter(adapter);
    }

    private static class NetworkAdapter extends BaseAdapter {

        private Context mContext;

        private List<JSONObject> mMediationList;

        private NetworkAdapter(Context context, List<JSONObject> mediationList) {
            mContext = context;
            mMediationList = mediationList;
        }

        @Override
        public int getCount() {
            return mMediationList.size();
        }

        @Override
        public JSONObject getItem(int position) {
            return mMediationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextViewHolder holder;
            if (convertView == null) {
                holder = new TextViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.network_grid_textview, null);
                holder.mTextView = convertView.findViewById(R.id.textView_title);
                convertView.setTag(holder);
            } else {
                holder = (TextViewHolder) convertView.getTag();
            }

            holder.mTextView.setText(getItem(position).optString(Constant.JSON_KEY_ADNAME));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdTypeActivity.start(mContext, getItem(position));
                }
            });
            return convertView;
        }

        private class TextViewHolder {
            private TextView mTextView;
        }
    }
}