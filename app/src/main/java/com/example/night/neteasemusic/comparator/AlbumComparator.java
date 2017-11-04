package com.example.night.neteasemusic.comparator;

import android.text.TextUtils;

import com.example.night.neteasemusic.info.AlbumInfo;

import java.util.Comparator;

/**
 * Created by Night on 2017/10/28.
 * Desc:
 */

public class AlbumComparator implements Comparator<AlbumInfo> {
    @Override
    public int compare(AlbumInfo o1, AlbumInfo o2) {
        String p1 = o1.album_sort;
        String p2 = o2.album_sort;
        if (TextUtils.equals("", p1) && TextUtils.equals("", p2))
            return 0;
        if (TextUtils.equals("", p1))
            return -1;
        if (TextUtils.equals("", p2))
            return 1;
        return p1.compareTo(p2);
    }
}
