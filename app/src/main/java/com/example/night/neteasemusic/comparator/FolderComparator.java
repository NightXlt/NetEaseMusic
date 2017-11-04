package com.example.night.neteasemusic.comparator;

import android.text.TextUtils;

import com.example.night.neteasemusic.info.FolderInfo;

import java.util.Comparator;

/**
 * @author Night
 * @desc
 * @since 2017/10/28 23:49
 */
public class FolderComparator implements Comparator<FolderInfo> {

    @Override
    public int compare(FolderInfo a1, FolderInfo a2) {
        String p1 = a1.folder_sort;
        String p2 = a2.folder_sort;
        if (TextUtils.equals("", p1) && TextUtils.equals("", p2))
            return 0;
        if (TextUtils.equals("", p1))
            return -1;
        if (TextUtils.equals("", p2))
            return 1;
        return p1.compareTo(p2);
    }

}