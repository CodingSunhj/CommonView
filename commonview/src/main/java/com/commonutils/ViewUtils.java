package com.commonutils;

import android.app.Activity;
import android.view.View;

/**
 * Created by SHJ on 2016/9/24.
 */

public class ViewUtils {
    /**
     * 进行简化findViewById的操作，简化方法在BaseActivity中设置
     * @param activity
     * @param resId
     * @param <E>
     * @return
     */
    public static <E extends View> E findViewById(Activity activity, int resId) {
        return (E) activity.findViewById(resId);
    }

    public static <E extends View> E findViewById(View view, int resId) {
        return (E) view.findViewById(resId);
    }
}
