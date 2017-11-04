package com.example.night.neteasemusic.contract;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;

import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

public class MainActivityContract {
    public interface View extends BaseView {
        boolean KeyDown(int keyCode, KeyEvent event);
    }

    public interface Presenter extends BasePresenter<View> {
        void requestPermission(Activity activity);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        boolean keyDown(int keyCode, KeyEvent event);
    }
}
