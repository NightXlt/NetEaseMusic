package com.example.night.neteasemusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.adapter.AlbumAdapter;
import com.example.night.neteasemusic.comparator.AlbumComparator;
import com.example.night.neteasemusic.contract.AlbumContract;
import com.example.night.neteasemusic.info.AlbumInfo;
import com.example.night.neteasemusic.mvp.MVPBaseFragment;
import com.example.night.neteasemusic.presenter.AlbumPresenter;
import com.example.night.neteasemusic.utils.CommonUtils;
import com.example.night.neteasemusic.utils.PreferencesUtility;
import com.example.night.neteasemusic.utils.SortOrder;
import com.example.night.neteasemusic.view.DividerItemDecoration;
import com.example.night.neteasemusic.view.SideBar;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Night on 2017/10/14.
 * Desc:
 */

public class AlbumFragment extends MVPBaseFragment<AlbumContract.View, AlbumPresenter> implements AlbumContract.View, View.OnClickListener {

    private RecyclerView mRecyclerview;
    private TextView mDialogText;
    private SideBar mSidebar;
    private AlbumAdapter mAlbumAdapter;
    private PreferencesUtility mPreferences;
    private boolean isAZSort = true;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    View popupView;
    PopupWindow popupWindow;
    private ConstraintLayout mClSongNumber;
    private ConstraintLayout mClAlbumName;
    private ImageView[] imageviews = new ImageView[3];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance();
        isAZSort = mPreferences.getAlbumOrder().equals(SortOrder.AlbumOrder.ALBUM_A_Z);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mAlbumAdapter = new AlbumAdapter(null, context);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerview.setAdapter(mAlbumAdapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        mSidebar = (SideBar) v.findViewById(R.id.sidebar);
        mDialogText = (TextView) v.findViewById(R.id.dialog_text);
        mSidebar.setView(mDialogText);
        mSidebar.setOnTouchLetterListener(new SideBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetterChanged(String s) {
                //TODO:touch things
                mDialogText.setText(s);
                mSidebar.setView(mDialogText);
                if (positionMap.get(s) != null) {
                    int i = positionMap.get(s);
                    ((LinearLayoutManager) mRecyclerview.getLayoutManager()).scrollToPositionWithOffset(i, 0);//recycler view滑到指定位置
                }
            }
        });
        mPresenter.reloadAdapter();
        int density = (int) CommonUtils.getDeviceDensity(context);
        popupView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.album_sort_layout, null);
        popupWindow = new PopupWindow(popupView, 264 * density, 240 * density);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        mClAlbumName = (ConstraintLayout) popupView.findViewById(R.id.cl_album_name);
        mClAlbumName.setOnClickListener(this);
        mClSongNumber = (ConstraintLayout) popupView.findViewById(R.id.cl_song_number);
        mClSongNumber.setOnClickListener(this);
        imageviews[1] = (ImageView) popupView.findViewById(R.id.iv_choose_1);
        imageviews[2] = (ImageView) popupView.findViewById(R.id.iv_choose_2);
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                mSidebar.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.local_music_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_local_music:
                mPresenter.reloadAdapter();
                return true;
            case R.id.choose_rank_method:
                popupWindow.showAtLocation(
                        mRecyclerview,
                        Gravity.CENTER,
                        0,
                        0);
                int c = NetEaseApplication.sSp.getInt("album_sort_order", 0);
                for (int i = 1; i < imageviews.length; i++) {
                    imageviews[i].setVisibility(View.INVISIBLE);
                }
                if (c != 0) {
                    imageviews[c].setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.get_lyric:
                mPresenter.reloadAdapter();
                return true;
            case R.id.upgrade_music_quality:
                mPresenter.reloadAdapter();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void updateResults(List<AlbumInfo> albumInfos) {
        isAZSort = mPreferences.getAlbumOrder().equals(SortOrder.AlbumOrder.ALBUM_A_Z);
        if (isAZSort) {
            Collections.sort(albumInfos, new AlbumComparator());
            for (int i = 0; i < albumInfos.size(); i++) {
                if (positionMap.get(albumInfos.get(i).album_sort) == null)
                    positionMap.put(albumInfos.get(i).album_sort, i);
            }
        }
        mAlbumAdapter.updateDataSet(albumInfos);
        if (isAZSort) {
            mRecyclerview.addOnScrollListener(scrollListener);
        } else {
            mSidebar.setVisibility(View.INVISIBLE);
            mRecyclerview.removeOnScrollListener(scrollListener);
        }
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_album_name:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("album_sort_order", 1).apply();
                mPreferences.setAlbumOrder(SortOrder.AlbumOrder.ALBUM_A_Z);
                mPresenter.reloadAdapter();
                break;
            case R.id.cl_song_number:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("album_sort_order", 2).apply();
                mPreferences.setAlbumOrder(SortOrder.AlbumOrder.ALBUM_NUMBER_OF_SONGS);
                mPresenter.reloadAdapter();
                break;
            default:
                break;
        }
    }
}
