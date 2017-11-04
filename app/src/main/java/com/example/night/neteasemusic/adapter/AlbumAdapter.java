/**
 * Created by Night on 2017/10/14.
 * Desc:
 */
package com.example.night.neteasemusic.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.info.AlbumInfo;
import com.facebook.drawee.view.SimpleDraweeView;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;

import java.util.List;


/**
 * @author Night
 * @since 2017-10-14
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private final Context context;
    private List<AlbumInfo> items;
    private String url;

    public AlbumAdapter(List<AlbumInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AlbumInfo item = items.get(position);
        if (holder.rootView.getTag() != null) {
            Log.i("TAG", "onBindViewHolder: ");
        }
        holder.mainTitle.setText(item.album_name);
        holder.title.setText(item.number_of_songs + "首" + " " + item.album_artist);
        Request request = ItheimaHttp.newGetRequest("?method=album.getinfo&album=" + item.album_name + "&artist=" + item.album_artist + "&api_key=fdb3a51437d4281d4d64964d333531d4&format=json");
        holder.draweeView.setImageURI(item.album_art);
    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void updateDataSet(List<AlbumInfo> folderInfos) {
        items = folderInfos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View rootView;
        SimpleDraweeView draweeView;
        TextView mainTitle, title;
        TintImageView moreOverflow;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mainTitle = (TextView) rootView.findViewById(R.id.viewpager_list_toptext);
            this.title = (TextView) rootView.findViewById(R.id.viewpager_list_bottom_text);
            this.draweeView = (SimpleDraweeView) rootView.findViewById(R.id.viewpager_list_img);
            this.moreOverflow = (TintImageView) rootView.findViewById(R.id.viewpager_list_button);
            rootView.setOnClickListener(this);
            moreOverflow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.viewpager_list_button:
                    //TODO:点击弹出更多选项
                    Snackbar.make(moreOverflow, "click moreOverflow", Snackbar.LENGTH_SHORT).show();

                    break;
                case R.id.relativelayout:
                    //TODO:点击布局播放音乐
                    Snackbar.make(rootView, "click rootview", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
