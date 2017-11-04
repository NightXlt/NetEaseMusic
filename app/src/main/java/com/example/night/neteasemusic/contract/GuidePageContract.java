package com.example.night.neteasemusic.contract;

import android.app.Activity;

import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class GuidePageContract {
    public interface View extends BaseView {
        void updateProgress(int i);
    }

    public interface Presenter extends BasePresenter<View> {
        void load(Activity fromActivity, Class toActivity);
    }
}
