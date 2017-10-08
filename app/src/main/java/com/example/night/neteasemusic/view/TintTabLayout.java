package com.example.night.neteasemusic.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import com.bilibili.magicasakura.utils.TintManager;
import com.bilibili.magicasakura.widgets.AppCompatBackgroundHelper;
import com.bilibili.magicasakura.widgets.AppCompatTextHelper;
import com.bilibili.magicasakura.widgets.Tintable;

/**
 * Created by Night on 2017/10/6.
 * Desc:
 */

public class TintTabLayout extends TabLayout implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible
        , AppCompatTextHelper.TextExtensible {
    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatTextHelper mTextHelper;

    public TintTabLayout(Context context) {
        this(context, null);
    }

    public TintTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager mTintManager = TintManager.get(getContext());

        mBackgroundHelper = new AppCompatBackgroundHelper(this, mTintManager);
        //mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        super.setSelectedTabIndicatorColor(color);

    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mTextHelper != null) {
            mTextHelper.tint();
        }
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }

    @Override
    public void setTextColorById(@ColorRes int colorId) {
        if (mTextHelper != null) {
            mTextHelper.setTextColorById(colorId);
        }
    }

}
