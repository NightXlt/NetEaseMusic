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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.adapter.SingleAdapter;
import com.example.night.neteasemusic.comparator.MusicComparator;
import com.example.night.neteasemusic.contract.SingleContract;
import com.example.night.neteasemusic.info.MusicInfo;
import com.example.night.neteasemusic.mvp.MVPBaseFragment;
import com.example.night.neteasemusic.presenter.SinglePresenter;
import com.example.night.neteasemusic.utils.CommonUtils;
import com.example.night.neteasemusic.utils.PreferencesUtility;
import com.example.night.neteasemusic.utils.SortOrder;
import com.example.night.neteasemusic.view.DividerItemDecoration;
import com.example.night.neteasemusic.view.SideBar;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Night on 2017/10/13.
 * Desc:
 */

public class SingleFragment extends MVPBaseFragment<SingleContract.View, SinglePresenter> implements SingleContract.View, View.OnClickListener {


    private ImageView mSelect;
    private RelativeLayout mRelativelayout;
    private RecyclerView mRecyclerview;
    private TextView mDialogText;
    private SingleAdapter mSingleAdapter;
    private SideBar mSidebar;
    private PreferencesUtility mPreferences;
    private boolean isAZSort = true;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    View popupView;
    PopupWindow popupWindow;
    private ConstraintLayout mClTime;
    private ConstraintLayout mClName;
    private ConstraintLayout mClAlbumName;
    private ConstraintLayout mClArtistName;
    private ImageView[] imageviews = new ImageView[5];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance();
        isAZSort = mPreferences.getSongOrder().equals(SortOrder.SongOrder.SONG_A_Z);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        mDialogText = (TextView) v.findViewById(R.id.dialog_text);
        mSingleAdapter = new SingleAdapter(null, context);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerview.setAdapter(mSingleAdapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        mSidebar = (SideBar) v.findViewById(R.id.sidebar);
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
        int density = (int) CommonUtils.getDeviceDensity(context);
        popupView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.single_sort_layout, null);
        popupWindow = new PopupWindow(popupView, 264 * density, 240 * density);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        mPresenter.reloadAdapter();
        mClTime = (ConstraintLayout) popupView.findViewById(R.id.cl_time);
        mClTime.setOnClickListener(this);
        mClName = (ConstraintLayout) popupView.findViewById(R.id.cl_name);
        mClName.setOnClickListener(this);
        mClAlbumName = (ConstraintLayout) popupView.findViewById(R.id.cl_album_name);
        mClAlbumName.setOnClickListener(this);
        mClArtistName = (ConstraintLayout) popupView.findViewById(R.id.cl_artist_name);
        mClArtistName.setOnClickListener(this);

        imageviews[1] = (ImageView) popupView.findViewById(R.id.iv_choose_1);
        imageviews[2] = (ImageView) popupView.findViewById(R.id.iv_choose_2);
        imageviews[3] = (ImageView) popupView.findViewById(R.id.iv_choose_3);
        imageviews[4] = (ImageView) popupView.findViewById(R.id.iv_choose_4);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_time:
                NetEaseApplication.sSp.edit().putInt("single_sort", 1).apply();
                popupWindow.dismiss();
                mPreferences.setSongOrder(SortOrder.SongOrder.SONG_DATE);
                mPresenter.reloadAdapter();
                break;
            case R.id.cl_name:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("single_sort", 2).apply();
                mPreferences.setSongOrder(SortOrder.SongOrder.SONG_A_Z);
                mPresenter.reloadAdapter();
                break;
            case R.id.cl_album_name:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("single_sort", 3).apply();
                mPreferences.setSongOrder(SortOrder.SongOrder.SONG_ALBUM);
                mPresenter.reloadAdapter();
                break;
            case R.id.cl_artist_name:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("single_sort", 4).apply();
                mPreferences.setSongOrder(SortOrder.SongOrder.SONG_ARTIST);
                mPresenter.reloadAdapter();
                break;

            default:
                break;
        }
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
                return true;
            case R.id.choose_rank_method:
                popupWindow.showAtLocation(
                        mRelativelayout,
                        Gravity.CENTER,
                        0,
                        0);
                int c = NetEaseApplication.sSp.getInt("single_sort", 0);
                for (int i = 1; i < imageviews.length; i++) {
                    imageviews[i].setVisibility(View.INVISIBLE);
                }
                if (c != 0) {
                    imageviews[c].setVisibility(View.VISIBLE);
                }
                backgroundAlpha(0.75f);
                return true;
            case R.id.get_lyric:
                return true;
            case R.id.upgrade_music_quality:
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
    public void updateResults(List<MusicInfo> musicInfos) {
        isAZSort = mPreferences.getAlbumOrder().equals(SortOrder.SongOrder.SONG_A_Z);
        if (isAZSort) {
            Collections.sort(musicInfos, new MusicComparator());
            for (int i = 0; i < musicInfos.size(); i++) {
                if (positionMap.get(musicInfos.get(i).sort) == null)
                    positionMap.put(musicInfos.get(i).sort, i);
            }
        }
        mSingleAdapter.updateDataSet(musicInfos);
        mSingleAdapter.notifyDataSetChanged();
        if (isAZSort) {
            mRecyclerview.addOnScrollListener(scrollListener);
        } else {
            mSidebar.setVisibility(View.INVISIBLE);
            mRecyclerview.removeOnScrollListener(scrollListener);
        }

    }
}
