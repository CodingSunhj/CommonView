package com.commonview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.view.shj.commonview.R;

/**
 * Created by SHJ on 2016/9/25.
 */

public class FlashTextView extends TextView {
    private static final int NORMAL_COLOR = 0xE0E0E0;
    private static final int MIDDLE_COLOR = 0xccffffff;
    private int normal_color;
    private int middle_cloor;
    private int mViewWidth;
    private LinearGradient mLineraGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;
    public FlashTextView(Context context) {
        super(context,null);
    }
    public FlashTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.setClickable(true);
        getAttrs(context,attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlashTextView);
        normal_color = ta.getColor(R.styleable.FlashTextView_bg_color,NORMAL_COLOR);
        middle_cloor = ta.getColor(R.styleable.FlashTextView_middle_color,MIDDLE_COLOR);
    }

    /**
     * 重写onSizeChanged的方法，使用LinearGradient着色器进行线性绘制
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth==0){
            mViewWidth = getMeasuredWidth();
            if(mViewWidth>0){
                Paint mPaint = getPaint();
                //初始化LineraGradient着色器，颜色从BUlE,30%透明白色，红色进行均等线性渐变
                mLineraGradient = new LinearGradient(0,0,mViewWidth,0,new int[]{normal_color,middle_cloor,normal_color},null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLineraGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mGradientMatrix != null){
            mTranslate += mViewWidth/5;
            if(mTranslate>mViewWidth){
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate,0);
            //Matrix配合LinearGradient达到一个不断位移的效果
            mLineraGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
