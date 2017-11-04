package com.example.night.neteasemusic.comparator;

import android.text.TextUtils;

import com.example.night.neteasemusic.info.ArtistInfo;

import java.util.Comparator;

/**
 * @author Night
 * @desc
 * @since 2017/10/28 23:25
 */
public class ArtistComparator implements Comparator<ArtistInfo> {

    @Override
    public int compare(ArtistInfo a1, ArtistInfo a2) {
        String p1 = a1.artist_sort;
        String p2 = a2.artist_sort;
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