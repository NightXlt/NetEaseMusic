package com.example.night.neteasemusic.contract;

import com.example.night.neteasemusic.info.ArtistInfo;
import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

import java.util.List;

public class ArtistContract {

    public interface View extends BaseView {
        void updateResults(List<ArtistInfo> folderInfos);
    }

    public interface Presenter extends BasePresenter<View> {
        void reloadAdapter();
    }
}
