package com.example.night.neteasemusic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.fragment.AlbumFragment;
import com.example.night.neteasemusic.fragment.ArtistFragment;
import com.example.night.neteasemusic.fragment.FolderFragment;
import com.example.night.neteasemusic.fragment.SingleFragment;
import com.example.night.neteasemusic.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.night.neteasemusic.R.id.toolbar;

/**
 * Created by Night on 2017/10/13.
 * Desc:
 */

public class LocalMusicActivity extends BaseActivity {
    private TextView mToolbarText;
    private ImageView mBarSearch;
    private TintToolbar mToolbar;
    private TabLayout mTabs;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        showBottomControl(true);
        initView();
    }

    private void initView() {
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mBarSearch = (ImageView) findViewById(R.id.bar_search);
        mToolbar = (TintToolbar) findViewById(toolbar);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        setSupportActionBar(mToolbar);
        mToolbar.setPadding(0, CommonUtils.getStatusHeight(this), 0, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new SingleFragment(), "单曲");
        adapter.addFragment(new ArtistFragment(), "歌手");
        adapter.addFragment(new AlbumFragment(), "专辑");
        adapter.addFragment(new FolderFragment(), "文件夹");
        mViewpager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewpager);
        mTabs.setTabTextColors(R.color.text_color, ThemeUtils.getThemeColorStateList(this, R.color.theme_color_primary).getDefaultColor());
        mTabs.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(this, R.color.theme_color_primary).getDefaultColor());
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
