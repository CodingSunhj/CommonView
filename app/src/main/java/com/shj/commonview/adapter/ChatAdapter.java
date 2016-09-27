package com.shj.commonview.adapter;

import android.content.Context;

import com.commonview.recyclerview.MultiItemAdapter;
import com.shj.commonview.model.Message;

import java.util.List;

/**
 * Created by SHJ on 2016/9/25.
 */

public class ChatAdapter extends MultiItemAdapter<Message> {

    public ChatAdapter(Context context, List<Message> datas) {
        super(context, datas);
        addItemViewDelegate(new MyMsg(context));
        addItemViewDelegate(new YouMsg(context));
    }

}
