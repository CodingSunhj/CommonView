package com.commonview.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by SHJ on 2016/9/21.
 */
public abstract class CommonAdapter<T> extends MultiItemAdapter<T>{
    protected int mLayoutId;
    protected List<T> mDatas;
    protected Context mContext;
    protected CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mLayoutId = layoutId;
        mContext = context;
        mDatas = datas;
        /**
         * 普通adapter，即只有一个视图，重写并在构造方法中调用addItemViewDelegate,默认装载layoutId的视图
         */
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent) {
                return ViewHolder.createViewHolder(context,parent,layoutId);
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, T t) {
                bindViewHolder(holder,t);
            }
        });
    }

    public abstract void bindViewHolder(ViewHolder holder, T t);

}
