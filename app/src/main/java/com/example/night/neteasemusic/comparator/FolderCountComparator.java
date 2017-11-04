package com.example.night.neteasemusic.comparator;


import com.example.night.neteasemusic.info.FolderInfo;

import java.util.Comparator;

/**
 * @author Night
 * @desc
 * @since 2017/10/28 23:49
 */
public class FolderCountComparator implements Comparator<FolderInfo> {

    @Override
    public int compare(FolderInfo a1, FolderInfo a2) {
        Integer p1 = a1.folder_count;
        Integer p2 = a2.folder_count;
        return p2.compareTo(p1);
    }

}