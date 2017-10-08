package com.example.night.neteasemusic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.example.night.neteasemusic.utils.ThemeHelper;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.night.neteasemusic.utils.Constants.CARD_BLACK;
import static com.example.night.neteasemusic.utils.Constants.CARD_FIREY;
import static com.example.night.neteasemusic.utils.Constants.CARD_HOPE;
import static com.example.night.neteasemusic.utils.Constants.CARD_LIGHT;
import static com.example.night.neteasemusic.utils.Constants.CARD_SAND;
import static com.example.night.neteasemusic.utils.Constants.CARD_STORM;
import static com.example.night.neteasemusic.utils.Constants.CARD_THUNDER;
import static com.example.night.neteasemusic.utils.Constants.CARD_WOOD;

/**
 * Created by Night on 2017/9/30.
 * Desc:Application
 */

public class NetEaseApplication extends Application implements ThemeUtils.switchColor {

    public static ExecutorService fixedThreadPool;
    public static SharedPreferences sSp;
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeUtils.setSwitchColor(this);
        Fresco.initialize(this);
        sContext = this;
        sSp = PreferenceManager.getDefaultSharedPreferences(this);
        fixedThreadPool = Executors.newFixedThreadPool(5);
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme()) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int color) {
        if (ThemeHelper.isDefaultTheme()) {
            return color;
        }
        String theme = getTheme(context);
        int colorId = -1;
        if (theme != null) {
            colorId = getThemeColor(context, color, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : color;

    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xd20000:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
        }
        return -1;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme() == CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme() == CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme() == CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme() == CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme() == CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme() == CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme() == CARD_FIREY) {
            return "red";
        } else if (ThemeHelper.getTheme() == CARD_BLACK) {
            return "black";
        }
        return null;
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_accent:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

}

