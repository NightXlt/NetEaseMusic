package com.example.night.neteasemusic.listener;

/**
 * Created by Night on 2017/10/4.
 * Desc:Music State Listener
 */

public interface MusicStateListener {

    void reloadAdapter();

    void changeTheme();

    void updateTime();

    void updateTrackInfo();
}
