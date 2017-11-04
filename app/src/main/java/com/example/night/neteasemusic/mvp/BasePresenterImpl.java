package com.example.night.neteasemusic.mvp;

import android.content.Context;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;
    protected Context mContext;

    @Override
    public void attachView(V view, Context context) {
        mView = view;
        mContext = context;
    }

    @Override
    public void detachView() {
        mView = null;
        mContext = null;
    }
}
