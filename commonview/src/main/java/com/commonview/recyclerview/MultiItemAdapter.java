package com.commonview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by SHJ on 2016/9/23.
 */

public class MultiItemAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    protected static final int ITEM_TYPE_HEADER = 10002;

    private View headerView;

    protected Context mContext;
    protected ItemViewDelegateManager<T> itemViewDelegateManager;
    protected List<T> mDatas;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;

    public MultiItemAdapter(Context context,List<T> datas){
        mContext =context;
        mDatas = datas;
        itemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        if(headerView!=null && position ==0){
            return ITEM_TYPE_HEADER;
        }
        Log.i("TAG","position"+position+"realPosition"+getRealPosition(position));
        return itemViewDelegateManager.getItemViewType(mDatas.get(getRealPosition(position)),getRealPosition(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(headerView!=null && viewType==ITEM_TYPE_HEADER){
            return ViewHolder.createViewHolder(mContext,headerView);
        }else {
            ItemViewDelegate<T> itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
            return itemViewDelegate.onCreateViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==ITEM_TYPE_HEADER){
            return;
        }
        setListener(holder);
        bindHolder(holder, mDatas.get(getRealPosition(position)));
    }
    public int getRealPosition(int position){
        return headerView==null?position:position-1;
    }

    @Override
    public int getItemCount() {
        return headerView==null?mDatas.size():mDatas.size()+1;
    }

    public void bindHolder(ViewHolder holder,T t){
        itemViewDelegateManager.onBindViewHolder(holder,t,getRealPosition(holder.getAdapterPosition()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
       RecyclerViewAttachUtils.onAttachedToRecyclerView(this,recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        RecyclerViewAttachUtils.onViewAttachedToWindow(holder);
    }

    /**
     * 删除元素
     * @param t
     */
    public void removeItem(T t) {
        int position = this.mDatas.indexOf(t);
        this.mDatas.remove(position);
        this.notifyItemRemoved(position);
    }

    public void removeItem(int position) {
        this.mDatas.remove(position);
        this.notifyItemRemoved(position);
    }

    /**
     * 获取当前的数据
     * @return
     */
    public List<T> getDatas(){
        return this.mDatas;
    }

    /**
     * 默认为adapter的itemView的点击事件
     * @param holder
     */
    private void setListener(final ViewHolder holder) {
        holder.getBinderView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    int position = getRealPosition(holder.getAdapterPosition());
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });
        holder.getBinderView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemLongClickListener!=null){
                    int position = getRealPosition(holder.getAdapterPosition());
                    onItemLongClickListener.onItemLongClick(v,position);
                }
                return false;
            }
        });
    }


    /**
     * 设置Adapter的数据
     * @param datas
     */
    public void setList(List<T> datas) {
        this.mDatas.clear();
        if(datas != null && !datas.isEmpty()) {
            this.mDatas.addAll(datas);
        }

        this.notifyDataSetChanged();
    }

    /**
     * 设置头部布局，获取头部布局
     * @param headerView
     */
    public void setHeaderView(View headerView){
        this.headerView = headerView;
        notifyItemInserted(0);
    }
   public View getHeaderView(){
       return headerView;
   }


    /**
     *判断是否使用了ItemViewDelegateManager进行多个ItemView的添加
     * @return
     */
//    public boolean isUseItemViewDelegateManager(){
//        return itemViewDelegateManager.getItemViewDelegateCount()>0;
//    }

    /**
     **在Adapter中添加不同的ItemViewDelegate,即实现多种ItemView
     * @param itemViewDelegate
     * @return
     */
    public MultiItemAdapter<T> addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate){
        itemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }
    public MultiItemAdapter addItemViewDelegate(int viewType,ItemViewDelegate itemViewDelegate){
        itemViewDelegateManager.addDelegate(viewType,itemViewDelegate);
        return this;
    }
    /**
     * 定义Adapter的点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClick(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }


}
