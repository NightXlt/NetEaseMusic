package com.example.night.neteasemusic.contract;

import com.example.night.neteasemusic.info.AlbumInfo;
import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

import java.util.List;

public class AlbumContract {

    public interface View extends BaseView {
        void updateResults(List<AlbumInfo> folderInfos);
    }

    public interface Presenter extends BasePresenter<View> {
        void reloadAdapter();
    }
}
