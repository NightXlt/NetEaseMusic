package com.example.night.neteasemusic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.night.neteasemusic.listener.MusicStateListener;


/**
 * Created by Night on 2017/10/4.
 * Desc:
 */

public class BaseFragment extends Fragment implements MusicStateListener {
    public Activity context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context = null;
    }

    @Override
    public void reloadAdapter() {

    }

    @Override
    public void changeTheme() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public void updateTrackInfo() {

    }

}
