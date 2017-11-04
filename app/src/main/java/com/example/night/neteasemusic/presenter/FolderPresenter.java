package com.example.night.neteasemusic.presenter;

import com.example.night.neteasemusic.contract.FolderContract;
import com.example.night.neteasemusic.info.FolderInfo;
import com.example.night.neteasemusic.info.MusicInfo;
import com.example.night.neteasemusic.mvp.BasePresenterImpl;
import com.example.night.neteasemusic.utils.IConstants;
import com.example.night.neteasemusic.utils.MusicUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Night
 * @desc
 * @since 2017/10/26 11:31
 */

public class FolderPresenter extends BasePresenterImpl<FolderContract.View> implements FolderContract.Presenter {
    private List<FolderInfo> mAlbumInfos;

    @Override
    public void reloadAdapter() {
        Observable.create(new ObservableOnSubscribe<List<FolderInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FolderInfo>> e) throws Exception {
                List<FolderInfo> folderInfos = MusicUtils.queryFolder(mContext);
                for (int i = 0; i < folderInfos.size(); i++) {
                    List<MusicInfo> albumList = MusicUtils.queryMusic(mContext, folderInfos.get(i).folder_path, IConstants.START_FROM_FOLDER);
                    folderInfos.get(i).folder_count = albumList.size();
                }
                e.onNext(folderInfos);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FolderInfo>>() {
                    @Override
                    public void accept(List<FolderInfo> folderInfos) throws Exception {
                        mView.updateResults(folderInfos);
                    }
                });

    }


}
