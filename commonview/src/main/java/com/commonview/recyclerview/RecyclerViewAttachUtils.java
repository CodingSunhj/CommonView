package com.commonview.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SHJ on 2016/9/27.
 */

/**
 * 对GridLayoutManager和StaggerLayoutManager特出情况下header和loadMore布局进行处理
 */
public class RecyclerViewAttachUtils {
    private static List<Integer> itemViewTypes = Arrays.asList(
            LoadMoreWrapper.ITEM_TYPE_LOAD_MORE,
            MultiItemAdapter.ITEM_TYPE_HEADER
    );
    public static void onAttachedToRecyclerView(final RecyclerView.Adapter adapter, RecyclerView recyclerView){
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    return itemViewTypes.contains(adapter.getItemViewType(position))
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }

    }
    public static void onViewAttachedToWindow(ViewHolder holder){
        ViewGroup.LayoutParams lp = holder.getBinderView().getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }
}
