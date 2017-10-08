package com.example.night.neteasemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintProgressBar;
import com.example.night.neteasemusic.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Night on 2017/10/6.
 * Desc:
 */

public class BottomFragment extends BaseFragment {
    private SimpleDraweeView mPlaybarImg;
    private TextView mTvSongName;
    private TextView mTvSinger;
    private TintImageView mPlayList;
    private TintImageView mControl;
    private TintImageView mPlayNext;
    private TintProgressBar mSongProgressNormal;
    private static BottomFragment mBottomFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_main, container, false);
        initView(v);
        return v;
    }


    public static BottomFragment newInstance() {
        return new BottomFragment();
    }

    private void initView(View v) {
        mPlaybarImg = (SimpleDraweeView) v.findViewById(R.id.playbar_img);
        mTvSongName = (TextView) v.findViewById(R.id.tv_song_name);
        mTvSinger = (TextView) v.findViewById(R.id.tv_singer);
        mPlayList = (TintImageView) v.findViewById(R.id.play_list);
        mControl = (TintImageView) v.findViewById(R.id.control);
        mPlayNext = (TintImageView) v.findViewById(R.id.play_next);
        mSongProgressNormal = (TintProgressBar) v.findViewById(R.id.song_progress_normal);
        mSongProgressNormal.setProgressTintList(ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary));
        mSongProgressNormal.setProgress(50);

    }

    @Override
    public void changeTheme() {
        super.changeTheme();
        mSongProgressNormal.setProgressTintList(ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary));

    }
}
