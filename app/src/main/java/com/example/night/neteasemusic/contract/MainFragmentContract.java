package com.example.night.neteasemusic.contract;

import com.example.night.neteasemusic.bean.Playlist;
import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

import java.util.ArrayList;

public class MainFragmentContract {

    public interface View extends BaseView {
        void updateResults(ArrayList results, ArrayList<Playlist> playlists, ArrayList<Playlist> netPlaylists);

        void notifyDataSetChanged();
    }

    public interface Presenter extends BasePresenter<View> {
        void reloadAdapter();

        void setMusicInfo();

        void loadCount(boolean f);

        void setInfo(String title, int count, int id, int i);
    }
}
