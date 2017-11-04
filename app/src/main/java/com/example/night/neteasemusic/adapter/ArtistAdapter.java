/**
 * Created by Night on 2017/10/14.
 * Desc:
 */
package com.example.night.neteasemusic.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.info.ArtistInfo;
import com.example.night.neteasemusic.json.ArtistJson;
import com.facebook.drawee.view.SimpleDraweeView;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;


/**
 * @author Night
 * @since 2017-10-14
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private final Context context;
    private List<ArtistInfo> items;
    private String url;

    public ArtistAdapter(List<ArtistInfo> items, Context context) {
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
        final ArtistInfo item = items.get(position);
        holder.mainTitle.setText(item.artist_name);
        holder.title.setText(item.number_of_tracks + "首");
        holder.draweeView.setImageResource(R.mipmap.placeholder_disk_210);
        if (holder.rootView.getTag() == null || holder.rootView.getTag() != item.uri) {
            Request request = ItheimaHttp.newGetRequest("?method=artist.getinfo&artist=" + item.artist_name + "&api_key=fdb3a51437d4281d4d64964d333531d4&format=json");
            ItheimaHttp.send(request, new HttpResponseListener<ArtistJson>() {
                @Override
                public void onResponse(ArtistJson artistJson) {
                    url = artistJson.getArtist().getImage().get(2).get_$Text17();
                    item.uri = Uri.parse(url);
                    holder.draweeView.setImageURI(item.uri);
                    holder.rootView.setTag(item.uri);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable e) {
                    super.onFailure(call, e);
                }
            });
        } else {
            holder.draweeView.setImageURI((Uri) holder.rootView.getTag());
        }

    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void updateDataSet(List<ArtistInfo> artistInfos) {
        items = artistInfos;
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
