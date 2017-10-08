package com.example.night.neteasemusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.view.TintTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Night on 2017/10/5.
 * Desc:
 */

public class TabNetPagerFragment extends BaseFragment {

    private TintTabLayout mTabs;
    private ViewPager mViewpager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_net, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mTabs = (TintTabLayout) v.findViewById(R.id.tabs);
        mViewpager = (ViewPager) v.findViewById(R.id.viewpager);
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new SocialFragment(), "ssd");
        adapter.addFragment(new SocialFragment(), "ssd");
        adapter.addFragment(new SocialFragment(), "ssd");
        mViewpager.setAdapter(adapter);
        mTabs.setTabTextColors(R.color.text_color, ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary).getDefaultColor());
        mTabs.setSelectedTabIndicatorColor(ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary).getDefaultColor());
        mTabs.setupWithViewPager(mViewpager);
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
