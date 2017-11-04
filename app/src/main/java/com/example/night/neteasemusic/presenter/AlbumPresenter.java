package com.example.night.neteasemusic.presenter;

import com.example.night.neteasemusic.contract.AlbumContract;
import com.example.night.neteasemusic.info.AlbumInfo;
import com.example.night.neteasemusic.mvp.BasePresenterImpl;
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

public class AlbumPresenter extends BasePresenterImpl<AlbumContract.View> implements AlbumContract.Presenter {
    private List<AlbumInfo> mAlbumInfos;

    @Override
    public void reloadAdapter() {
        Observable.create(new ObservableOnSubscribe<List<AlbumInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AlbumInfo>> e) throws Exception {
                mAlbumInfos = MusicUtils.queryAlbums(mContext);
                e.onNext(mAlbumInfos);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AlbumInfo>>() {
                    @Override
                    public void accept(List<AlbumInfo> folderInfos) throws Exception {
                        mView.updateResults(folderInfos);
                    }
                });

    }


}
