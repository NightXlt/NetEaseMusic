package com.example.night.neteasemusic.bean;

/**
 * Created by Night on 2017/10/4.
 * Desc:
 */

public class Playlist {
    public final long id;
    public final String name;
    public final int songCount;
    public final String albumArt;
    public final String author;

    public Playlist() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumArt = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
        this.author = "";
    }

    public Playlist(long _id, String _name, int _songCount, String _albumArt, String author) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
        this.albumArt = _albumArt;
        this.author = author;
    }
}
