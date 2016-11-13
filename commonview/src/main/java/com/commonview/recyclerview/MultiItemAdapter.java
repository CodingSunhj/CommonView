package com.commonview.recyclerview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.view.shj.commonview.R;

import java.util.List;

/**
 * Created by SHJ on 2016/9/23.
 */

public class MultiItemAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    public static final int ITEM_TYPE_HEADER = 10002;
    public static final int ITEM_TYPE_LOAD_MORE = 10001;
    private SparseArrayCompat<View> headerViews;
    private Context mContext;
    private ItemViewDelegateManager<T> itemViewDelegateManager;
    protected List<T> mDatas;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;

    public OnLoadMoreListener onLoadMoreListener;
    private boolean isLoadAll = false;

    private View loadMoreView = null;
    private int loadMoreLayoutId = 0;
    private ViewHolder loaderHolder;
    public MultiItemAdapter(Context context,List<T> datas){
        mContext =context;
        mDatas = datas;
        itemViewDelegateManager = new ItemViewDelegateManager<>();
        headerViews = new SparseArrayCompat<>();
    }

    @Override
    public int getItemViewType(int position) {
        if(headerViews.size()!=0 && position < headerViews.size()){
            return headerViews.keyAt(position);
        }
        if (isShowingLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        Log.i("TAG","position"+position+"realPosition"+getRealPosition(position));
        return itemViewDelegateManager.getItemViewType(mDatas.get(getRealPosition(position)),getRealPosition(position));
    }
    private View getHeaderViewByType(int viewType){
       return headerViews.get(viewType);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(headerViews.get(viewType)!=null){
            return  ViewHolder.createViewHolder(mContext,getHeaderViewByType(viewType));
        }else if (viewType == ITEM_TYPE_LOAD_MORE){
                if (loadMoreView != null) {
                    loaderHolder =  ViewHolder.createViewHolder(parent.getContext(), loadMoreView);
                } else if (loadMoreLayoutId != 0) {
                    loaderHolder =  ViewHolder.createViewHolder(parent.getContext(), parent, loadMoreLayoutId);
                } else {
                    loaderHolder =  ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.item_load_more);
                }
            return loaderHolder;
        }else {
            ItemViewDelegate<T> itemViewDelegate = itemViewDelegateManager.getItemViewDelegate(viewType);
            return itemViewDelegate.onCreateViewHolder(parent);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(headerViews.size()!=0 && position < headerViews.size()){
            return;
        }
        if (getItemViewType(position)==ITEM_TYPE_LOAD_MORE) {
            if (!isLoadAll) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
            return;
        }
        setListener(holder);
        bindHolder(holder, mDatas.get(getRealPosition(position)));
    }
    public int getRealPosition(int position){
        return headerViews.size()==0?position:position-headerViews.size();
    }

    @Override
    public int getItemCount() {
        return headerViews.size()==0?mDatas.size()+1:mDatas.size()+headerViews.size()+1;
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
     * 追加Adapter数据,可用于loader数据的时候
     */
    public void addList(List<T> datas){
        if(datas!=null && !datas.isEmpty()){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }else if(loadMoreView ==null && loadMoreLayoutId == 0){//如果采用默认布局
            isLoadAll = true;
           notifyDataLoadedAll();
        }

    }

    /**
     * 通知已经加载完毕
     */
        public void notifyDataLoadedAll(){
            ProgressBar progressBar = loaderHolder.getView(R.id.progressBar);
            TextView textView = loaderHolder.getView(R.id.loadText);
            progressBar.setVisibility(View.GONE);
            textView.setText("已全部加载");
        }
    /**
     * 设置头部布局，获取头部布局
     * @param headerView
     */
    public void addHeaderView(View headerView){
        headerViews.put(ITEM_TYPE_HEADER+headerViews.size(),headerView);
    }
   public SparseArrayCompat<View> getHeaderView(){
       return headerViews;
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
     * 加载更多的配置
     */
    public boolean isShowingLoadMore(int position) {
        return (position >= (getItemCount()-1));
    }
    /**
     * 设置并获取loadMoreView
     * @param loadMoreView
     * @return
     */
    public MultiItemAdapter<T> setLoadMoreView(View loadMoreView) {
        this.loadMoreView = loadMoreView;
        return this;
    }

    public MultiItemAdapter<T> setLoadMoreView(int loadMoreLayoutId) {
        this.loadMoreLayoutId = loadMoreLayoutId;
        return this;
    }
    public View getLoadMoreView(){
        return loaderHolder.getBinderView();
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
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (onLoadMoreListener != null) {
            this.onLoadMoreListener = onLoadMoreListener;
        }
    }


}
