package com.example.night.neteasemusic.bean;

/**
 * Created by Night on 2017/10/3.
 * Desc:Main menu item
 */

public class MainFragmentItem {
    private int mAvatar;
    private String mTitle;
    private int mCount;

    public MainFragmentItem(int avatar, String name, int count) {
        mAvatar = avatar;
        mTitle = name;
        mCount = count;
    }

    public MainFragmentItem() {
    }

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int avatar) {
        mAvatar = avatar;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
