package com.example.night.neteasemusic.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.example.night.neteasemusic.NetEaseApplication;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.contract.MainActivityContract;
import com.example.night.neteasemusic.fragment.MainFragment;
import com.example.night.neteasemusic.fragment.SocialFragment;
import com.example.night.neteasemusic.fragment.TabNetPagerFragment;
import com.example.night.neteasemusic.mvp.MVPBaseActivity;
import com.example.night.neteasemusic.presenter.MainActivityPresenter;
import com.example.night.neteasemusic.utils.Constants;
import com.example.night.neteasemusic.utils.ThemeHelper;
import com.example.night.neteasemusic.view.CardPickerDialog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends MVPBaseActivity<MainActivityContract.View, MainActivityPresenter> implements View.OnClickListener, CardPickerDialog.ClickListener, MainActivityContract.View {
    private int mode = 1;//0:night 1:day
    private NavigationView mNavMenu;
    private DrawerLayout mNavLayout;
    private ImageView mBarNet;
    private ImageView mBarMusic;
    private ImageView mBarSocial;
    private ImageView mBarSearch;
    private TintToolbar mToolbar;

    private ArrayList<ImageView> tabs = new ArrayList<>();

    private CustomViewPagerAdapter mCustomViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showBottomControl(true);
        initView();
        mode = NetEaseApplication.sSp.getInt("mode", 1);
        setViewPager();
        mPresenter.requestPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        //getWindow().setStatusBarColor(ThemeUtils.getColorById(this, R.color.theme_color_primary));
        mNavMenu = (NavigationView) findViewById(R.id.nav_menu);
        mNavLayout = (DrawerLayout) findViewById(R.id.nav_layout);
        mNavMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mNavLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_skin:
                        CardPickerDialog dialog = new CardPickerDialog();
                        dialog.setClickListener(MainActivity.this);
                        dialog.show(getSupportFragmentManager(), "theme");
                        mNavLayout.closeDrawers();
                        break;
                    case R.id.nav_exit:
                        finish();
                        break;
                    case R.id.nav_night:

                           /* getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            //调用recreate()使设置生效
                            recreate();*/

                        if (mode == 1) {
                            onConfirm(Constants.CARD_BLACK);
                            mode = 0;
                        } else {
                            onConfirm(Constants.CARD_SAKURA);
                            mode = 1;
                        }
                        NetEaseApplication.sSp.edit().putInt("mode", mode).apply();
                        break;
                    default:

                }
                return true;
            }
        });
        mBarNet = (ImageView) findViewById(R.id.bar_net);
        mBarNet.setOnClickListener(this);
        mBarMusic = (ImageView) findViewById(R.id.bar_music);
        mBarMusic.setOnClickListener(this);
        mBarSocial = (ImageView) findViewById(R.id.bar_friends);
        mBarSocial.setOnClickListener(this);
        mBarSearch = (ImageView) findViewById(R.id.bar_search);
        mBarSearch.setOnClickListener(this);
        mToolbar = (TintToolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(this);
                /*左上角的菜单按钮*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mNavLayout, mToolbar, R.string.app_name, R.string.app_name);
        mNavLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_net:
                mCustomViewPager.setCurrentItem(0);
                break;
            case R.id.bar_music:
                mCustomViewPager.setCurrentItem(1);
                break;
            case R.id.bar_friends:
                mCustomViewPager.setCurrentItem(2);

                break;
        }
    }

    ViewPager mCustomViewPager;

    private void setViewPager() {
        tabs.add(mBarNet);
        tabs.add(mBarMusic);
        tabs.add(mBarSocial);
        mCustomViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        final MainFragment mainFragment = new MainFragment();
        final TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        final SocialFragment socialFragment = new SocialFragment();
        mCustomViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        mCustomViewPagerAdapter.addFragment(tabNetPagerFragment);
        mCustomViewPagerAdapter.addFragment(mainFragment);
        mCustomViewPagerAdapter.addFragment(socialFragment);
        mCustomViewPager.setAdapter(mCustomViewPagerAdapter);
        mCustomViewPager.setCurrentItem(1);
        mBarMusic.setSelected(true);
        mCustomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme() != currentTheme) {
            ThemeHelper.setTheme(currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                                //  recreate();
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                        }
                    }
            );
        }
        changeTheme();
        int l = mCustomViewPagerAdapter.mFragments.size();
        mCustomViewPagerAdapter.mFragments.clear();
        final MainFragment mainFragment = new MainFragment();
        final TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        final SocialFragment socialFragment = new SocialFragment();
        mCustomViewPagerAdapter.addFragment(tabNetPagerFragment);
        mCustomViewPagerAdapter.addFragment(mainFragment);
        mCustomViewPagerAdapter.addFragment(socialFragment);
        mCustomViewPagerAdapter.notifyDataSetChanged();
    }


    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mPresenter.keyDown(keyCode, event);
    }

    @Override
    public boolean KeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 1000) {
                Snackbar.make(mToolbar, "再按一次返回桌面", Snackbar.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
