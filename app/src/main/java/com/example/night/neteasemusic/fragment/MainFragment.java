package com.example.night.neteasemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.night.neteasemusic.contract.MainFragmentContract;
import com.example.night.neteasemusic.mvp.MVPBaseFragment;
import com.example.night.neteasemusic.presenter.MainFragmentPresenter;
import com.example.night.neteasemusic.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Night on 2017/10/3.
 * Desc:
 */

public class MainFragment extends MVPBaseFragment<MainFragmentContract.View, MainFragmentPresenter> implements MainFragmentContract.View {
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
        swipeRefresh.setColorSchemeResources(R.color.theme_color_primary);
        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(NetEaseApplication.sContext, R.color.theme_color_primary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.reloadAdapter();
            }
        });
        //先给adapter设置空数据，异步加载好后更新数据，防止Recyclerview no attach
        mAdapter = new MainFragmentAdapter(context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(NetEaseApplication.sContext, DividerItemDecoration.VERTICAL_LIST));
        //设置没有item动画
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mPresenter.reloadAdapter();
        context.getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        return view;
    }

    @Override
    public void changeTheme() {
        super.changeTheme();
        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(context, R.color.theme_color_primary));
    }

    @Override
    public void updateResults(ArrayList results, ArrayList<Playlist> playlists, ArrayList<Playlist> netPlaylists) {
        if (mAdapter == null) {
            mAdapter = new MainFragmentAdapter(NetEaseApplication.sContext);
        }
        mAdapter.updateResults(results, playlists, netPlaylists);

    }
    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
}
