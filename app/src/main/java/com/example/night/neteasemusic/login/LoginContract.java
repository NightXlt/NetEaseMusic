package com.example.night.neteasemusic.login;

import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void showLoad();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
