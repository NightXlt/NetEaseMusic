package com.example.night.neteasemusic.utils;

import android.provider.MediaStore;

/**
 * Created by Night on 2017/10/11.
 * Desc:静态类不能被继承
 */

public final class SortOrder {

    /**
     * Song sort order
     */
    public interface SongOrder {
        //Song sort order A-Z
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        //Song sort order Z-A
        String SONG_Z_A = MediaStore.Audio.Media.DEFAULT_SORT_ORDER + " DESC";
        //Song sort order artist
        String SONG_ARTIST = MediaStore.Audio.Media.ARTIST;
        //Song sort order album
        String SONG_ALBUM = MediaStore.Audio.Media.ALBUM;
        //Song sort order year
        String SONG_DURATION = MediaStore.Audio.Media.DURATION;
        //Song sort order date
        String SONG_DATE = MediaStore.Audio.Media.DATE_ADDED;
        //Song sort order file name
        String SONG_FILE_NAME = MediaStore.Audio.Media.DATA;
    }

    /**
     * Album sort order
     */
    public interface AlbumOrder {
        /* Album sort order A-Z */
        String ALBUM_A_Z = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;

        /* Album sort order Z-A */
        String ALBUM_Z_A = ALBUM_A_Z + " DESC";

        /* Album sort order songs */
        String ALBUM_NUMBER_OF_SONGS = MediaStore.Audio.Albums.NUMBER_OF_SONGS
                + " DESC";

        /* Album sort order artist */
        String ALBUM_ARTIST = MediaStore.Audio.Albums.ARTIST;

        /* Album sort order year */
        String ALBUM_YEAR = MediaStore.Audio.Albums.FIRST_YEAR + " DESC";

    }

    /**
     * Artist album sort order.
     */
    public interface ArtistAlbumOrder {
        /* Artist album sort order A-Z */
        String ALBUM_A_Z = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;

        /* Artist album sort order Z-A */
        String ALBUM_Z_A = ALBUM_A_Z + " DESC";

        /* Artist album sort order songs */
        String ALBUM_NUMBER_OF_SONGS = MediaStore.Audio.Artists.Albums.NUMBER_OF_SONGS
                + " DESC";

        /* Artist album sort order year */
        String ALBUM_YEAR = MediaStore.Audio.Artists.Albums.FIRST_YEAR
                + " DESC";
    }

    /**
     * Artist sort order .
     */
    public interface ArtistOrder {
        /* Artist sort order A-Z */
        String ARTIST_A_Z = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;

        /* Artist sort order Z-A */
        String ARTIST_Z_A = ARTIST_A_Z + " DESC";

        /* Artist sort order number of songs */
        String ARTIST_NUMBER_OF_SONGS = MediaStore.Audio.Artists.NUMBER_OF_TRACKS
                + " DESC";

        /* Artist sort order number of albums */
        String ARTIST_NUMBER_OF_ALBUMS = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
                + " DESC";
    }

    /**
     * Artist song sort order.
     */
    public interface ArtistSongOrder {
        /* Artist song sort order A-Z */
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Artist song sort order Z-A */
        String SONG_Z_A = SONG_A_Z + " DESC";

        /* Artist song sort order album */
        String SONG_ALBUM = MediaStore.Audio.Media.ALBUM;

        /* Artist song sort order year */
        String SONG_YEAR = MediaStore.Audio.Media.YEAR + " DESC";

        /* Artist song sort order duration */
        String SONG_DURATION = MediaStore.Audio.Media.DURATION + " DESC";

        /* Artist song sort order date */
        String SONG_DATE = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        /* Artist song sort order filename */
        String SONG_FILENAME = SongOrder.SONG_FILE_NAME;
    }

    /**
     * Album song sort order.
     */
    public interface AlbumSongOrder {
        /* Album song sort order A-Z */
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Album song sort order Z-A */
        String SONG_Z_A = SONG_A_Z + " DESC";

        /* Album song sort order track list */
        String SONG_TRACK_LIST = MediaStore.Audio.Media.TRACK + ", "
                + MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Album song sort order duration */
        String SONG_DURATION = SongOrder.SONG_DURATION;

        /* Album Song sort order year */
        String SONG_YEAR = MediaStore.Audio.Media.YEAR + " DESC";

        /* Album song sort order filename */
        String SONG_FILENAME = SongOrder.SONG_FILE_NAME;
    }

    public interface FolderOrder {
        String FOLDER_A_Z = "foler_az";
        String FOLDER_NUMBER = "foler_number";
    }
}
