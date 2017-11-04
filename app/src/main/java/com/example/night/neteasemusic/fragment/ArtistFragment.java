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
import com.example.night.neteasemusic.adapter.ArtistAdapter;
import com.example.night.neteasemusic.comparator.ArtistComparator;
import com.example.night.neteasemusic.contract.ArtistContract;
import com.example.night.neteasemusic.info.ArtistInfo;
import com.example.night.neteasemusic.mvp.MVPBaseFragment;
import com.example.night.neteasemusic.presenter.ArtistPresenter;
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

public class ArtistFragment extends MVPBaseFragment<ArtistContract.View, ArtistPresenter> implements ArtistContract.View, View.OnClickListener {

    private RecyclerView mRecyclerview;
    private TextView mDialogText;
    private SideBar mSidebar;
    private ArtistAdapter mArtistAdapter;
    private boolean isAZSort = true;
    private PreferencesUtility mPreferences;
    private HashMap<String, Integer> positionMap = new HashMap<>();
    View popupView;
    PopupWindow popupWindow;
    private ConstraintLayout mClSongNumber;
    private ConstraintLayout mClArtistName;
    private ImageView[] imageviews = new ImageView[3];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artist, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mArtistAdapter = new ArtistAdapter(null, context);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerview.setAdapter(mArtistAdapter);
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
        popupView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.artist_sort_layout, null);
        popupWindow = new PopupWindow(popupView, 264 * density, 240 * density);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        mPresenter.reloadAdapter();
        mClArtistName = (ConstraintLayout) popupView.findViewById(R.id.cl_artist_name);
        mClArtistName.setOnClickListener(this);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
                int c = NetEaseApplication.sSp.getInt("artist_sort_order", 0);
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
    public void updateResults(List<ArtistInfo> artistInfos) {
        isAZSort = mPreferences.getArtistOrder().equals(SortOrder.ArtistOrder.ARTIST_A_Z);
        if (isAZSort) {
            Collections.sort(artistInfos, new ArtistComparator());
            for (int i = 0; i < artistInfos.size(); i++) {
                if (positionMap.get(artistInfos.get(i).artist_sort) == null)
                    positionMap.put(artistInfos.get(i).artist_sort, i);
            }
        }
        mArtistAdapter.updateDataSet(artistInfos);
        if (isAZSort) {
            mRecyclerview.addOnScrollListener(scrollListener);
        } else {
            mSidebar.setVisibility(View.INVISIBLE);
            mRecyclerview.removeOnScrollListener(scrollListener);
        }
        mArtistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_artist_name:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("artist_sort_order", 1).apply();
                mPreferences.setArtistOrder(SortOrder.ArtistOrder.ARTIST_A_Z);
                mPresenter.reloadAdapter();
                break;
            case R.id.cl_song_number:
                popupWindow.dismiss();
                NetEaseApplication.sSp.edit().putInt("artist_sort_order", 2).apply();
                mPreferences.setArtistOrder(SortOrder.ArtistOrder.ARTIST_NUMBER_OF_SONGS);
                mPresenter.reloadAdapter();
                break;
            default:
                break;
        }
    }
}
