package com.shj.commonview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.commonutils.BaseActivity;
import com.shj.commonview.R;
import com.shj.commonview.adapter.ChatAdapter;
import com.shj.commonview.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MultiTEMRvActivity extends BaseActivity {
    
    RecyclerView chatRv;
    private List<Message> datas = new ArrayList<>();
    private ChatAdapter chatAdapter;
    public static void start(Activity context){
        Intent intent = new Intent(context,MultiTEMRvActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rv);
        initView();
        initData();
    }

    private void initData() {
        datas.addAll(Message.MOCK_DATAS);
        chatRv.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this,datas);
        chatRv.setAdapter(chatAdapter);

    }

    private void initView() {
        chatRv = $(R.id.charRv);
    }
}
