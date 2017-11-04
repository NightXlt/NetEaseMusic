package com.example.night.neteasemusic.contract;

import com.example.night.neteasemusic.info.FolderInfo;
import com.example.night.neteasemusic.mvp.BasePresenter;
import com.example.night.neteasemusic.mvp.BaseView;

import java.util.List;

public class FolderContract {

    public interface View extends BaseView {
        void updateResults(List<FolderInfo> folderInfos);
    }

    public interface Presenter extends BasePresenter<View> {
        void reloadAdapter();
    }
}
