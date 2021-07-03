package com.android.mb.wash.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.youth.banner.Banner;

/**
 * Created by cgy on 19/4/23.
 */

public class MyBanner1 extends Banner {

    public MyBanner1(Context context) {
        super(context);
    }

    public MyBanner1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = childWidthSize;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
