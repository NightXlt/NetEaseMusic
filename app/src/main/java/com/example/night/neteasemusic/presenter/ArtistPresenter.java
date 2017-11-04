package com.example.night.neteasemusic.presenter;

import com.example.night.neteasemusic.contract.ArtistContract;
import com.example.night.neteasemusic.info.ArtistInfo;
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

public class ArtistPresenter extends BasePresenterImpl<ArtistContract.View> implements ArtistContract.Presenter {
    private List<ArtistInfo> mArtistInfos;

    @Override
    public void reloadAdapter() {
        Observable.create(new ObservableOnSubscribe<List<ArtistInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArtistInfo>> e) throws Exception {
                mArtistInfos = MusicUtils.queryArtist(mContext);
                e.onNext(mArtistInfos);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArtistInfo>>() {
                    @Override
                    public void accept(List<ArtistInfo> folderInfos) throws Exception {
                        mView.updateResults(folderInfos);
                    }
                });

    }


}
