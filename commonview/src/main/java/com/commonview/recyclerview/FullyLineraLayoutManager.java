package com.commonview.recyclerview;

/**
 * Created by SHJ on 2016/9/27.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

public class FullyLineraLayoutManager extends LinearLayoutManager {
    private int[] mMeasuredDimension = new int[2];

    public FullyLineraLayoutManager(Context context) {
        super(context);
    }

    public FullyLineraLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
        int widthMode = MeasureSpec.getMode(widthSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int widthSize = MeasureSpec.getSize(widthSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);
        Log.i(FullyLineraLayoutManager.class.getSimpleName(), "onMeasure called. \nwidthMode " + widthMode + " \nheightMode " + heightSpec + " \nwidthSize " + widthSize + " \nheightSize " + heightSize + " \ngetItemCount() " + this.getItemCount());
        int width = 0;
        int height = 0;
        for(int i = 0; i < this.getItemCount(); ++i) {
            this.measureScrapChild(recycler, i, MeasureSpec.makeMeasureSpec(i, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(i, MeasureSpec.UNSPECIFIED), this.mMeasuredDimension);
            if(this.getOrientation() == 0) {
                width += this.mMeasuredDimension[0];
                if(i == 0) {
                    height = this.mMeasuredDimension[1];
                }
            } else {
                height += this.mMeasuredDimension[1];
                if(i == 0) {
                    width = this.mMeasuredDimension[0];
                }
            }
        }

        switch(widthMode) {
            case 1073741824:
                width = widthSize;
            case -2147483648:
            case 0:
            default:
                switch(heightMode) {
                    case 1073741824:
                        height = heightSize;
                    case -2147483648:
                    case 0:
                    default:
                        this.setMeasuredDimension(width, height);
                }
        }
    }

    private void measureScrapChild(Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        try {
            try {
                View e = recycler.getViewForPosition(0);
                if(e != null) {
                    LayoutParams p = (LayoutParams)e.getLayoutParams();
                    int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, this.getPaddingLeft() + this.getPaddingRight(), p.width);
                    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, this.getPaddingTop() + this.getPaddingBottom(), p.height);
                    e.measure(childWidthSpec, childHeightSpec);
                    measuredDimension[0] = e.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    measuredDimension[1] = e.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                    recycler.recycleView(e);
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }

        } finally {
            ;
        }
    }
}

