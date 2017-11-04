package com.example.night.neteasemusic.utils;

import android.provider.MediaStore;

/**
 * Created by Night on 2017/10/2.
 * Desc:
 */

public class Constants {
    public static final String CURRENT_THEME = "theme_current";
    public static final int CARD_SAKURA = 0x1;
    public static final int CARD_HOPE = 0x2;
    public static final int CARD_STORM = 0x3;
    public static final int CARD_WOOD = 0x4;
    public static final int CARD_LIGHT = 0x5;
    public static final int CARD_THUNDER = 0x6;
    public static final int CARD_SAND = 0x7;
    public static final int CARD_FIREY = 0x8;
    public static final String MUSIC_ONLY_SELECTION = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1"
            + " AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''";
    public final static int READ_PHONE_STATE_CODE = 101;

    public final static int WRITE_EXTERNAL_STORAGE_CODE = 102;

    public final static int REQUEST_OPEN_APPLICATION_SETTINGS_CODE = 12345;

    public static final int CARD_BLACK = 0x9;


}
