package com.example.night.neteasemusic.utils;

import com.example.night.neteasemusic.NetEaseApplication;

import static com.example.night.neteasemusic.utils.Constants.CARD_FIREY;
import static com.example.night.neteasemusic.utils.Constants.CARD_HOPE;
import static com.example.night.neteasemusic.utils.Constants.CARD_LIGHT;
import static com.example.night.neteasemusic.utils.Constants.CARD_SAKURA;
import static com.example.night.neteasemusic.utils.Constants.CARD_SAND;
import static com.example.night.neteasemusic.utils.Constants.CARD_STORM;
import static com.example.night.neteasemusic.utils.Constants.CARD_THUNDER;
import static com.example.night.neteasemusic.utils.Constants.CARD_WOOD;
import static com.example.night.neteasemusic.utils.Constants.CURRENT_THEME;

/**
 * Created by Night on 2017/10/2.
 * Desc:
 */

public class ThemeHelper {

    public static int getTheme() {
        return NetEaseApplication.sSp.getInt(CURRENT_THEME, CARD_SAKURA);
    }

    public static void setTheme(int themeId) {
        NetEaseApplication.sSp.edit()
                .putInt(CURRENT_THEME, themeId)
                .apply();
    }

    public static boolean isDefaultTheme() {
        return getTheme() == CARD_SAKURA;
    }

    public static String getName(int currentTheme) {
        switch (currentTheme) {
            case CARD_SAKURA:
                return "THE SAKURA";
            case CARD_STORM:
                return "THE STORM";
            case CARD_WOOD:
                return "THE WOOD";
            case CARD_LIGHT:
                return "THE LIGHT";
            case CARD_HOPE:
                return "THE HOPE";
            case CARD_THUNDER:
                return "THE THUNDER";
            case CARD_SAND:
                return "THE SAND";
            case CARD_FIREY:
                return "THE FIREY";
        }
        return "THE RETURN";
    }
}
