package com.example.night.neteasemusic.presenter;

import com.example.night.neteasemusic.contract.SingleContract;
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

public class SinglePresenter extends BasePresenterImpl<SingleContract.View> implements SingleContract.Presenter {
    private List<MusicInfo> mMusicInfos;

    @Override
    public void reloadAdapter() {

        Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MusicInfo>> e) throws Exception {
                mMusicInfos = MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL);
                e.onNext(mMusicInfos);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MusicInfo>>() {
                    @Override
                    public void accept(List<MusicInfo> folderInfos) throws Exception {
                        mView.updateResults(mMusicInfos);
                    }
                });
    }


}
