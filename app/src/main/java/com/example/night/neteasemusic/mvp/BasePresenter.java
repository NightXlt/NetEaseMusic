package com.example.night.neteasemusic.mvp;

import android.content.Context;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view, Context context);

    void detachView();
}
