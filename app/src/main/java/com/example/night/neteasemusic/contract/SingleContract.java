package com.example.night.neteasemusic.contract;

import com.example.night.neteasemusic.info.MusicInfo;
import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

import java.util.List;

public class SingleContract {

    public interface View extends BaseView {
        void updateResults(List<MusicInfo> folderInfos);
    }

    public interface Presenter extends BasePresenter<View> {
        void reloadAdapter();
    }
}
