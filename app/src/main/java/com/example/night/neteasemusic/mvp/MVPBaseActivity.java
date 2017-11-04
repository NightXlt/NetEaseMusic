package com.example.night.neteasemusic.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.fragment.BottomFragment;
import com.example.night.neteasemusic.listener.MusicStateListener;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView {
    public T mPresenter;
    private ArrayList<MusicStateListener> mMusicListener = new ArrayList<>();
    private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reloadAdapter() {
        for (final MusicStateListener listener : mMusicListener) {
            if (listener != null) {
                listener.reloadAdapter();
            }
        }
    }


    public void changeTheme() {
        for (final MusicStateListener listener : mMusicListener) {
            if (listener != null) {
                listener.changeTheme();
            }
        }
    }

    public void updateTime() {
        for (final MusicStateListener listener : mMusicListener) {
            if (listener != null) {
                listener.updateTime();
            }
        }
    }

    public void updateTrackInfo() {
        for (final MusicStateListener listener : mMusicListener) {
            if (listener != null) {
                listener.reloadAdapter();
                listener.updateTrackInfo();
            }
        }
    }

    public void showBottomControl(boolean f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (f) {
            if (mBottomFragment == null) {
                mBottomFragment = BottomFragment.newInstance();
                fragmentTransaction.add(R.id.bottom_container, mBottomFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.show(mBottomFragment).commitAllowingStateLoss();
            }
        } else {
            fragmentTransaction.hide(mBottomFragment).commitAllowingStateLoss();
        }
    }
}
