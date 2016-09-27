package com.shj.commonview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.commonview.recyclerview.CommonAdapter;
import com.commonview.recyclerview.ViewHolder;
import com.shj.commonview.R;

import java.util.List;

/**
 * Created by SHJ on 2016/9/22.
 */

public class RvSimpleAdapter  extends CommonAdapter<String>{
    public RvSimpleAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, String s) {
        holder.setText(R.id.tv,s);
    }
}
