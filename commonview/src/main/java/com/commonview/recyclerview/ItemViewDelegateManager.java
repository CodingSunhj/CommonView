package com.commonview.recyclerview;

import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;

/**
 * Created by SHJ on 2016/9/22.
 */

public class ItemViewDelegateManager<T> {
    private SparseArrayCompat<ItemViewDelegate<T>> delegates;
    public ItemViewDelegateManager(){
        delegates = new SparseArrayCompat<ItemViewDelegate<T>>();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate)
    {
        int viewType = delegates.size();
        if (delegate != null)
        {
            delegates.put(viewType, delegate);
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate)
    {
        if (delegates.get(viewType) != null)
        {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate)
    {
        if (delegate == null)
        {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType)
    {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }
    /**
     * 根据itemViewDelegate获取ViewType
     * @param item
     * @return
     */
    public int getItemViewType(ItemViewDelegate item){
        return delegates.indexOfValue(item);
    }
    public int getItemViewType(T item,int position){
        int itemViewDelegatesCount = getItemViewDelegateCount();
        for(int i = 0;i<itemViewDelegatesCount;i++){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if(delegate.isForViewType(item,position)){
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 分别对不同的itemView进行数据绑定，调用其自身的onBindViewHolder方法
     * @param holder
     * @param item
     * @param position
     */
    public void onBindViewHolder(ViewHolder holder,T item,int position){
        for(int i = 0;i<getItemViewDelegateCount();i++){
            ItemViewDelegate<T> delegateView = delegates.valueAt(i);
            if(delegateView.isForViewType(item,position)){
                delegateView.onBindViewHolder(holder,item);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 获取当前ItemView的个数
     * @return
     */
    public int getItemViewDelegateCount(){
        return delegates.size();
    }

    /**
     * 根据ViewType获取ItewView,存储方式是通过SparseArrayCompat存储View,ViewType为Key，ItemViewDelegate为value
     * @param viewType
     * @return
     */
    public ItemViewDelegate<T> getItemViewDelegate(int viewType){
        return delegates.valueAt(viewType);
    }




}
