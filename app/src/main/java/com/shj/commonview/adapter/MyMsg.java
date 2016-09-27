package com.shj.commonview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.commonview.recyclerview.ItemViewDelegate;
import com.commonview.recyclerview.ViewHolder;
import com.shj.commonview.R;
import com.shj.commonview.model.Message;

/**
 * Created by SHJ on 2016/9/25.
 */

public class MyMsg implements ItemViewDelegate<Message> {
    private Context context;
    public MyMsg(Context context){
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return ViewHolder.createViewHolder(context,parent, R.layout.simple_rv_title_item);
    }

    @Override
    public boolean isForViewType(Message item, int position) {
        return item.isTitle();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Message message) {
        holder.setText(R.id.tv,message.getContent());
    }
}
