package com.commonview.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.view.shj.commonview.R;

/**
 * Created by SHJ on 2016/9/26.
 */

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = 10001;
    private MultiItemAdapter innerAdapter;
    public OnLoadMoreListener onLoadMoreListener;
    private boolean isLoadAll = false;
    private ViewHolder holder;
    public LoadMoreWrapper(MultiItemAdapter adapter) {
        innerAdapter = adapter;
    }

    private View loadMoreView = null;
    private int loadMoreLayoutId = 0;

//    public boolean hasLoadMoreView() {
//        return loadMoreView != null || loadMoreLayoutId != 0;
//    }

    public boolean isShowingLoadMore(int position) {
        return (position >= innerAdapter.getItemCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_LOAD_MORE) {

            if (loadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), loadMoreView);
            } else if (loadMoreLayoutId != 0) {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, loadMoreLayoutId);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, R.layout.item_load_more);
            }
            return holder;
        }
        return innerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isShowingLoadMore(position)) {
            if (!isLoadAll) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
            return;
        }
        innerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return innerAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowingLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return innerAdapter.getItemViewType(position);
    }

    public boolean notifyDataCompleteLoad() {
        isLoadAll = true;
        if (loadMoreLayoutId == 0 && loadMoreView == null) {
            View view = holder.getBinderView();
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            TextView textView = (TextView) view.findViewById(R.id.loadText);
            progressBar.setVisibility(View.GONE);
            textView.setText("已全部加载");
        }
        return isLoadAll;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if(manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
//            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    return getItemViewType(position) == ITEM_TYPE_LOAD_MORE
//                            ? gridManager.getSpanCount() : 1;
//                }
//            });
//        }
        RecyclerViewAttachUtils.onAttachedToRecyclerView(this,recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams lp = holder.getBinderView().getLayoutParams();
//        if(lp != null
//                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
//            p.setFullSpan(holder.getLayoutPosition() == 0);
//        }
        RecyclerViewAttachUtils.onViewAttachedToWindow(holder);
    }

    /**
     * 设置并获取loadMoreView
     * @param loadMoreView
     * @return
     */
    public LoadMoreWrapper<T> setLoadMoreView(View loadMoreView) {
        this.loadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int loadMoreLayoutId) {
        this.loadMoreLayoutId = loadMoreLayoutId;
        return this;
    }
    public View getLoadMoreView(){
        return loadMoreView;
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
