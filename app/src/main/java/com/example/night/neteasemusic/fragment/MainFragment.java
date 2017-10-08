package com.example.night.neteasemusic.fragment;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.adapter.MainFragmentAdapter;
import com.example.night.neteasemusic.bean.MainFragmentItem;
import com.example.night.neteasemusic.bean.Playlist;
import com.example.night.neteasemusic.utils.CommonUtils;
import com.example.night.neteasemusic.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Night on 2017/10/3.
 * Desc:
 */

public class MainFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<MainFragmentItem> mList = new ArrayList<>();
    //private PlaylistInfo playlistInfo; //playlist 管理类
    private SwipeRefreshLayout swipeRefresh; //下拉刷新layout
    private MainFragmentAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(NetEaseApplication.sContext);
        recyclerView.setLayoutManager(layoutManager);
        //swipeRefresh.setColorSchemeResources(R.color.theme_color_PrimaryAccent);
        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(NetEaseApplication.sContext, R.color.theme_color_primary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();

            }
        });
        //先给adapter设置空数据，异步加载好后更新数据，防止Recyclerview no attach
        mAdapter = new MainFragmentAdapter(context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(NetEaseApplication.sContext, DividerItemDecoration.VERTICAL_LIST));
        //设置没有item动画
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        reloadAdapter();

        context.getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        return view;
    }

    //刷新列表
    public void reloadAdapter() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
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

                if (mAdapter == null) {
                    mAdapter = new MainFragmentAdapter(NetEaseApplication.sContext);
                }
                mAdapter.updateResults(results, playlists, netPlaylists);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (NetEaseApplication.sContext == null)
                    return;
                mAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }.execute();
    }

    private void setMusicInfo() {

        if (CommonUtils.isLollipop() && ContextCompat.checkSelfPermission(context, "") != PackageManager.PERMISSION_GRANTED) {
            loadCount(false);
        } else {
            loadCount(true);
        }
    }

    private void loadCount(boolean f) {
        int localMusicCount = 0, recentMusicCount = 0, downLoadCount = 0, artistsCount = 0;
        if (f) {

        }
        setInfo(context.getResources().getString(R.string.local_music), localMusicCount, R.mipmap.music_icn_local, 0);
        setInfo(context.getResources().getString(R.string.recent_play), recentMusicCount, R.mipmap.music_icn_recent, 1);
        setInfo(context.getResources().getString(R.string.local_manage), downLoadCount, R.mipmap.music_icn_dld, 2);
        setInfo(context.getResources().getString(R.string.my_artist), artistsCount, R.mipmap.music_icn_artist, 3);

    }

    //为info设置数据，并放入mlistInfo
    private void setInfo(String title, int count, int id, int i) {
        MainFragmentItem information = new MainFragmentItem();
        information.setTitle(title);
        information.setCount(count);
        information.setAvatar(id);
        if (mList.size() < 4) {
            mList.add(new MainFragmentItem());
        }
        mList.set(i, information); //将新的info对象加入到信息列表中
    }

    @Override
    public void changeTheme() {
        super.changeTheme();
        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(context, R.color.theme_color_primary));
    }
}
