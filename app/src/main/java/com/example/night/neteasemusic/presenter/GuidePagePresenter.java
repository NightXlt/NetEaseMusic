package com.example.night.neteasemusic.presenter;

import android.app.Activity;
import android.content.Intent;

import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.contract.GuidePageContract;
import com.example.night.neteasemusic.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class GuidePagePresenter extends BasePresenterImpl<GuidePageContract.View> implements GuidePageContract.Presenter {

    @Override
    public void load(final Activity fromActivity, final Class toActivity) {
        NetEaseApplication.fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i += i) {
                    mView.updateProgress(i);
                    i += 10;
                }
                fromActivity.startActivity(new Intent(fromActivity, toActivity));
                fromActivity.finish();
            }
        });
    }
}
