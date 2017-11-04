package com.example.night.neteasemusic.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Artists;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;

import com.example.night.neteasemusic.info.AlbumInfo;
import com.example.night.neteasemusic.info.ArtistInfo;
import com.example.night.neteasemusic.info.FolderInfo;
import com.example.night.neteasemusic.info.MusicInfo;
import com.github.promeg.pinyinhelper.Pinyin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE;

/**
 * Created by Night on 2017/10/10.
 * Desc:
 */

public class MusicUtils implements IConstants {

    public static final int FILE_SIZE = 1 * 1024 * 1024;//1MB

    public static final int FILE_DURATION = 1 * 60 * 1000;//1min

    private static String[] pro_music = new String[]{
            Media._ID, Media.TITLE,
            Media.DATA, Media.ALBUM_ID, Media.ALBUM,
            Media.ARTIST_ID, Media.ARTIST,
            Media.DURATION, Media.SIZE
    };
    private static String[] pro_album = new String[]{
            Albums._ID, Albums.ALBUM_ART,
            Albums.ALBUM, Albums.NUMBER_OF_SONGS, Albums.ARTIST
    };
    private static String[] pro_artist = new String[]{
            Artists._ID,
            Artists.NUMBER_OF_TRACKS,
            Artists.ARTIST
    };
    private static String[] pro_folder = new String[]{MediaStore.Files.FileColumns.DATA};


    /**
     * @param context
     * @param from    请求来自于哪（ARTIST、ALBUM、LOCAL、FOLDER）
     * @return
     */
    public static List<MusicInfo> queryMusic(Context context, int from) {
        return queryMusic(context, null, from);
    }

    public static ArrayList<MusicInfo> queryMusic(Context context, String id, int from) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        final String sortOrder = PreferencesUtility.getInstance().getSongOrder();

        StringBuilder s = new StringBuilder("title!=''");
        s.append(" and " + Media.SIZE + ">" + FILE_SIZE);//文件大于1MB
        s.append(" and " + Media.DURATION + ">" + FILE_DURATION);//文件大于1MB
        switch (from) {
            case START_FROM_LOCAL:
                return getMusicListCursor(contentResolver.query(uri, pro_music, s.toString(), null, sortOrder));
            case START_FROM_FOLDER:
                ArrayList<MusicInfo> list1 = new ArrayList<>();
                ArrayList<MusicInfo> list = getMusicListCursor(contentResolver.query(Media.EXTERNAL_CONTENT_URI, pro_music,
                        s.toString(), null,
                        null));
                for (MusicInfo music : list) {
                    if (music.data.substring(0, music.data.lastIndexOf(File.separator)).equals(id)) {
                        list1.add(music);
                    }
                }
                return list1;
            case START_FROM_ALBUM:

                break;
            case START_FROM_ARTIST:

                break;
            case START_FROM_FAVORITE:

                break;
            default:
                break;
        }
        return null;
    }

    public static ArrayList<MusicInfo> getMusicListCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        ArrayList<MusicInfo> musicInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            MusicInfo music = new MusicInfo();
            music.songId = cursor.getInt(cursor
                    .getColumnIndex(Media._ID));
            music.albumId = cursor.getInt(cursor
                    .getColumnIndex(Media.ALBUM_ID));
            music.albumName = cursor.getString(cursor
                    .getColumnIndex(Albums.ALBUM));
            music.albumData = getAlbumArtUri(music.albumId) + "";
            music.duration = cursor.getInt(cursor
                    .getColumnIndex(Media.DURATION));
            music.musicName = cursor.getString(cursor
                    .getColumnIndex(Media.TITLE));
            music.artist = cursor.getString(cursor
                    .getColumnIndex(Media.ARTIST));
            music.artistId = cursor.getLong(cursor.getColumnIndex(Media.ARTIST_ID));
            String filePath = cursor.getString(cursor
                    .getColumnIndex(Media.DATA));
            music.data = filePath;
            music.folder = filePath.substring(0, filePath.lastIndexOf(File.separator));
            music.size = cursor.getInt(cursor
                    .getColumnIndex(Media.SIZE));
            music.islocal = true;
            music.sort = Pinyin.toPinyin(music.musicName.charAt(0)).substring(0, 1).toUpperCase();
            musicInfos.add(music);
        }
        cursor.close();
        return musicInfos;
    }


    public static List<ArtistInfo> queryArtist(Context context) {
        Uri uri = Artists.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder s = new StringBuilder((MediaStore.Audio.Artists._ID
                + " in (select distinct " + Media.ARTIST_ID
                + " from audio_meta where "));
        s.append(Media.SIZE + ">" + FILE_SIZE);//文件大于1MB
        s.append(" and " + Media.DURATION + ">" + FILE_DURATION);//文件大于1MB
        s.append(")");
        final String sortOrder = PreferencesUtility.getInstance().getArtistOrder();
        Log.i("TAG", "queryArtist: " + sortOrder);
        List<ArtistInfo> artistInfos = getArtistList(contentResolver.query(uri, pro_artist, s.toString(), null, sortOrder));
        return artistInfos;
    }

    private static ArrayList<ArtistInfo> getArtistList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ArrayList<ArtistInfo> artistInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.artist_id = cursor.getInt(
                    cursor.getColumnIndex(Artists._ID));
            artistInfo.artist_name = cursor.getString(
                    cursor.getColumnIndex(Artists.ARTIST));
            artistInfo.number_of_tracks = cursor.getInt(
                    cursor.getColumnIndex(Artists.NUMBER_OF_TRACKS));
            artistInfo.artist_sort = Pinyin.toPinyin(artistInfo.artist_name.charAt(0)).substring(0, 1).toUpperCase();
            artistInfos.add(artistInfo);
        }
        cursor.close();
        return artistInfos;
    }

    public static List<AlbumInfo> queryAlbums(Context context) {
        final String sortOrder = PreferencesUtility.getInstance().getAlbumOrder();
        ContentResolver cr = context.getContentResolver();
        StringBuilder where = new StringBuilder(Albums._ID
                + " in (select distinct " + Media.ALBUM_ID
                + " from audio_meta where (1=1)");
        where.append(" and " + Media.SIZE + " > " + FILE_SIZE);
        where.append(" and " + Media.DURATION + " > " + FILE_DURATION);

        where.append(" )");

        // Media.ALBUM_KEY 按专辑名称排序
        List<AlbumInfo> list = getAlbumList(cr.query(Albums.EXTERNAL_CONTENT_URI, pro_album,
                where.toString(), null, sortOrder));
        return list;

    }

    private static List<AlbumInfo> getAlbumList(Cursor query) {
        List<AlbumInfo> albumInfos = new ArrayList<>();
        while (query.moveToNext()) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.album_name = query.getString(query.getColumnIndex(Albums.ALBUM));
            albumInfo.album_artist = query.getString(query.getColumnIndex(Albums.ARTIST));
            albumInfo.album_id = query.getInt(query.getColumnIndex(Albums._ID));
            albumInfo.album_art = getAlbumArtUri(albumInfo.album_id) + "";
            albumInfo.number_of_songs = query.getInt(query.getColumnIndex(Albums.NUMBER_OF_SONGS));
            albumInfo.album_sort = Pinyin.toPinyin(albumInfo.album_name.charAt(0)).substring(0, 1).toUpperCase();
            albumInfos.add(albumInfo);
        }
        query.close();
        return albumInfos;
    }

    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    public static List<FolderInfo> queryFolder(Context context) {
        Uri uri = MediaStore.Files.getContentUri("external");
        ContentResolver cr = context.getContentResolver();
        StringBuilder mSelection = new StringBuilder(MEDIA_TYPE
                + " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + " and " + "("
                + MediaStore.Files.FileColumns.DATA + " like'%.mp3' or " + Media.DATA
                + " like'%.wma')");
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        mSelection.append(" and " + Media.SIZE + " > " + FILE_SIZE);
        mSelection.append(" and " + Media.DURATION + " > " + FILE_DURATION);
        mSelection.append(") group by ( " + MediaStore.Files.FileColumns.PARENT);

        List<FolderInfo> list = getFolderList(cr.query(uri, pro_folder, mSelection.toString(), null,
                null));

        return list;

    }

    public static List<FolderInfo> getFolderList(Cursor cursor) {
        List<FolderInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            FolderInfo info = new FolderInfo();
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA));
            info.folder_path = filePath.substring(0, filePath.lastIndexOf(File.separator));
            info.folder_name = info.folder_path.substring(info.folder_path
                    .lastIndexOf(File.separator) + 1);
            info.folder_sort = Pinyin.toPinyin(info.folder_name.charAt(0)).substring(0, 1).toUpperCase();
            list.add(info);
        }
        cursor.close();
        return list;
    }

}
