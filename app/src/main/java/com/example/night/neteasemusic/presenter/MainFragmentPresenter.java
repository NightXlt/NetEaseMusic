package com.example.night.neteasemusic.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.adapter.MainFragmentAdapter;
import com.example.night.neteasemusic.bean.MainFragmentItem;
import com.example.night.neteasemusic.bean.Playlist;
import com.example.night.neteasemusic.contract.MainFragmentContract;
import com.example.night.neteasemusic.mvp.BasePresenterImpl;
import com.example.night.neteasemusic.utils.CommonUtils;
import com.example.night.neteasemusic.utils.IConstants;
import com.example.night.neteasemusic.utils.MusicUtils;

import java.util.ArrayList;
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

public class MainFragmentPresenter extends BasePresenterImpl<MainFragmentContract.View> implements MainFragmentContract.Presenter {
    private List<MainFragmentItem> mList = new ArrayList<>();

    @Override
    public void reloadAdapter() {
        Observable.create(new ObservableOnSubscribe<MainFragmentAdapter>() {
            @Override
            public void subscribe(ObservableEmitter<MainFragmentAdapter> e) throws Exception {
                ArrayList results = new ArrayList();
                setMusicInfo();
                ArrayList<Playlist> playlists = new ArrayList<Playlist>();
                ArrayList<Playlist> netPlaylists = new ArrayList<Playlist>();
                playlists.add(new Playlist());
                netPlaylists.add(new Playlist());
                results.addAll(mList);
                results.add(NetEaseApplication.sContext.getResources().getString(R.string.created_playlists));
                results.addAll(playlists);
                if (netPlaylists != null) {
                    results.add("收藏的歌单");
                    results.addAll(netPlaylists);
                }
                mView.updateResults(results, playlists, netPlaylists);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainFragmentAdapter>() {
                    @Override
                    public void accept(MainFragmentAdapter adapter) throws Exception {
                        if (mContext == null)
                            return;
                        mView.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void setMusicInfo() {
        if (CommonUtils.isLollipop() && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            loadCount(false);
        } else {
            loadCount(true);
        }
    }

    @Override
    public void loadCount(boolean f) {
        int localMusicCount = 0, recentMusicCount = 0, downLoadCount = 0, artistsCount = 0;
        if (f) {
            try {
                localMusicCount = MusicUtils.queryMusic(mContext, IConstants.START_FROM_LOCAL).size();
                artistsCount = MusicUtils.queryArtist(mContext).size();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        setInfo(mContext.getResources().getString(R.string.local_music), localMusicCount, R.mipmap.music_icn_local, 0);
        setInfo(mContext.getResources().getString(R.string.recent_play), recentMusicCount, R.mipmap.music_icn_recent, 1);
        setInfo(mContext.getResources().getString(R.string.local_manage), downLoadCount, R.mipmap.music_icn_dld, 2);
        setInfo(mContext.getResources().getString(R.string.my_artist), artistsCount, R.mipmap.music_icn_artist, 3);

    }

    @Override
    public void setInfo(String title, int count, int id, int i) {
        MainFragmentItem information = new MainFragmentItem();
        information.setTitle(title);
        information.setCount(count);
        information.setAvatar(id);
        if (mList.size() < 4) {
            mList.add(new MainFragmentItem());
        }
        mList.set(i, information); //将新的info对象加入到信息列表中
    }
}
