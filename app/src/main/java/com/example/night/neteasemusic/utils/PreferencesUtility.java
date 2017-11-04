package com.example.night.neteasemusic.utils;

import com.example.night.neteasemusic.NetEaseApplication;

/**
 * Created by Night on 2017/10/28.
 * Desc:
 */

public class PreferencesUtility {

    private static final String THEME_PREFERNCE = "theme_preference";
    private static final String ARTIST_ORDER = "artist_order";
    private static final String ARTIST_SONG_ORDER = "artist_song_order";
    private static final String ALBUM_ORDER = "album_order";
    private static final String ALBUM_SONG_ORDER = "album_song_order";
    private static final String SONG_ORDER = "song_order";
    private static final String FOLDER_SONG_ORDER = "folder_song_order";

    private static PreferencesUtility sInstance;

    private PreferencesUtility() {
    }

    public static final PreferencesUtility getInstance() {
        if (sInstance == null) {
            sInstance = new PreferencesUtility();
        }
        return sInstance;
    }

    public String getTheme() {
        return NetEaseApplication.sSp.getString(THEME_PREFERNCE, "light");
    }

    private void setSortOrder(final String key, final String value) {
        NetEaseApplication.sSp.edit().putString(key, value).apply();
    }

    public final String getArtistOrder() {
        return NetEaseApplication.sSp.getString(ARTIST_ORDER, SortOrder.ArtistOrder.ARTIST_A_Z);
    }

    public void setArtistOrder(final String value) {
        setSortOrder(ARTIST_ORDER, value);
    }

    public final String getArtistSongOrder() {
        return NetEaseApplication.sSp.getString(ARTIST_SONG_ORDER,
                SortOrder.ArtistSongOrder.SONG_A_Z);
    }

    public void setArtistSongOrder(final String value) {
        setSortOrder(ARTIST_SONG_ORDER, value);
    }

    public final String getAlbumOrder() {
        return NetEaseApplication.sSp.getString(ALBUM_ORDER, SortOrder.AlbumOrder.ALBUM_A_Z);
    }

    public void setAlbumOrder(final String value) {
        setSortOrder(ALBUM_ORDER, value);
    }

    public final String getAlbumSongOrder() {
        return NetEaseApplication.sSp.getString(ALBUM_SONG_ORDER,
                SortOrder.AlbumSongOrder.SONG_TRACK_LIST);
    }

    public void setAlbumSongOrder(final String value) {
        setSortOrder(ALBUM_SONG_ORDER, value);
    }

    public final String getSongOrder() {
        return NetEaseApplication.sSp.getString(SONG_ORDER, SortOrder.SongOrder.SONG_A_Z);
    }

    public void setSongOrder(final String value) {
        setSortOrder(SONG_ORDER, value);
    }

    public void setFolderOrder(final String value) {
        setSortOrder(FOLDER_SONG_ORDER, value);
    }

    public final String getFolderOrder() {
        return NetEaseApplication.sSp.getString(FOLDER_SONG_ORDER, SortOrder.FolderOrder.FOLDER_A_Z);
    }

}
