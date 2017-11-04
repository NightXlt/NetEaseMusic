package com.example.night.neteasemusic.comparator;


import android.text.TextUtils;

import com.example.night.neteasemusic.info.MusicInfo;

import java.util.Comparator;

/**
 * @author Night
 * @desc
 * @since 2017/10/28 23:44
 */
public class MusicComparator implements Comparator<MusicInfo> {

    @Override
    public int compare(MusicInfo m1, MusicInfo m2) {
        String p1 = m1.sort;
        String p2 = m2.sort;
        // 判断是否为空""
        if (TextUtils.equals("", p1) && TextUtils.equals("", p2))
            return 0;
        if (TextUtils.equals("", p1))
            return -1;
        if (TextUtils.equals("", p2))
            return 1;
        return p1.compareTo(p2);
    }

}