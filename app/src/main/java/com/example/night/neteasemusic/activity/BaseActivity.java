package com.example.night.neteasemusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.fragment.BottomFragment;
import com.example.night.neteasemusic.listener.MusicStateListener;

import java.util.ArrayList;

/**
 * Created by Night on 2017/10/6.
 * Desc:
 */

public class BaseActivity extends AppCompatActivity {
    private ArrayList<MusicStateListener> mMusicListener = new ArrayList<>();
    private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showBottomControl(true);
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
