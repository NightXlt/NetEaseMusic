/**
 * Created by Night on 2017/10/4.
 * Desc:
 */
package com.example.night.neteasemusic.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.bean.MainFragmentItem;
import com.example.night.neteasemusic.bean.Playlist;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;


/**
 * @author Night
 * @since 2017-10-04
 */
public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private final Context context;
    private ArrayList itemResults = new ArrayList();
    private boolean isLoveList = true;
    private boolean createdExpanded = true;
    private boolean collectExpanded = true;
    private ArrayList<Playlist> playlists, netplaylists = new ArrayList<>();

    public MainFragmentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        switch (viewType) {
            case 0:
                View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_item, parent, false);
                ViewHolder ml0 = new ViewHolder(v0);
                return ml0;
            case 1:
                if (isLoveList) {
                    View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_create_playlist, parent, false);
                    ViewHolder ml1 = new ViewHolder(v1);
                    isLoveList = false;
                    return ml1;
                }
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_collect_playlist, parent, false);
                ViewHolder ml1 = new ViewHolder(v1);
                return ml1;
            case 2:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                ViewHolder ml2 = new ViewHolder(v2);
                return ml2;
            case 3:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item, parent, false);
                ViewHolder ml3 = new ViewHolder(v3);
                return ml3;

        }
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                MainFragmentItem item = (MainFragmentItem) itemResults.get(position);
                holder.itemtitle.setText(item.getTitle());
                holder.count.setText("(" + item.getCount() + ")");
                holder.image.setImageResource(item.getAvatar());
                holder.image.setImageTintList(R.color.theme_color_primary);
                // setOnListener(holder, i);
                break;
            case 1:
                Playlist playlist = (Playlist) itemResults.get(position);
                if (createdExpanded && playlist.author.equals("local")) {
                    if (playlist.albumArt != null)
                        holder.albumArt.setImageURI(Uri.parse(playlist.albumArt));
                    holder.title.setText(playlist.name);
                    holder.songcount.setText(playlist.songCount + "首");

                }
                if (collectExpanded && !playlist.author.equals("local")) {
                    if (playlist.albumArt != null)
                        holder.albumArt.setImageURI(Uri.parse(playlist.albumArt));
                    holder.title.setText(playlist.name);
                    holder.songcount.setText(playlist.songCount + "首");
                }
                //setOnPlaylistListener(holder, i, playlist.id, playlist.albumArt, playlist.name);
                isLoveList = false;
                break;
            case 2:
                holder.sectionItem.setText("创建的歌单" + "(" + playlists.size() + ")");
                holder.sectionImg.setImageResource(R.mipmap.list_icn_arr_right);
                //setSectionListener(holder, i);
                break;
            case 3:
                holder.sectionItem.setText("收藏的歌单" + "(" + netplaylists.size() + ")");
                holder.sectionImg.setImageResource(R.mipmap.list_icn_arr_right);
                //setSectionListener(holder, i);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (itemResults == null) {
            return 0;
        }
        return itemResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return -1;
        }
        if (itemResults.get(position) instanceof MainFragmentItem)
            return 0;
        if (itemResults.get(position) instanceof Playlist) {
            return 1;
        }
        if (itemResults.get(position) instanceof String) {
            if (((String) itemResults.get(position)).equals("收藏的歌单"))
                return 3;
        }
        return 2;
    }

    public void updateResults(ArrayList results, ArrayList<Playlist> playlists, ArrayList<Playlist> netPlaylists) {
        isLoveList = true;
        this.itemResults = results;
        this.playlists = playlists;
        this.netplaylists = netPlaylists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        protected TextView itemtitle, title, count, songcount, sectionItem;
        protected ImageView menu, sectionImg, sectionMenu;
        SimpleDraweeView albumArt;
        protected TintImageView image;

        public ViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.image = (TintImageView) view.findViewById(R.id.fragment_main_item_img);
            this.itemtitle = (TextView) view.findViewById(R.id.fragment_main_item_title);
            this.count = (TextView) view.findViewById(R.id.fragment_main_item_count);

            this.title = (TextView) view.findViewById(R.id.fragment_main_playlist_item_title);
            this.songcount = (TextView) view.findViewById(R.id.fragment_main_playlist_item_count);
            this.albumArt = (SimpleDraweeView) view.findViewById(R.id.fragment_main_playlist_item_img);
            this.menu = (ImageView) view.findViewById(R.id.fragment_main_playlist_item_menu);

            this.sectionItem = (TextView) view.findViewById(R.id.expand_title);
            this.sectionImg = (ImageView) view.findViewById(R.id.expand_img);
            this.sectionMenu = (ImageView) view.findViewById(R.id.expand_menu);
        }
    }
}
