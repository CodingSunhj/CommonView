package com.commonview.recyclerview;

/**
 * Created by SHJ on 2016/9/22.
 */

import android.view.ViewGroup;

/**
 * 一个itemView的接口
 * @param <T>
 */
public interface ItemViewDelegate<T> {
    ViewHolder onCreateViewHolder(ViewGroup parent);
    boolean isForViewType(T item,int position);
    void onBindViewHolder(ViewHolder holder,T t);
}
