/**
 * Created by Night on 2017/10/13.
 * Desc:
 */
package com.example.night.neteasemusic.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.info.MusicInfo;

import java.util.List;


/**
 * @author Night
 * @since 2017-10-13
 */
public class SingleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<MusicInfo> items;


    public SingleAdapter(List<MusicInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v;
        if (getItemViewType(viewType) == 0) {
            return new FirstViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_single_first, parent, false));
        } else {
            return new SecondViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_single, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicInfo item = items.get(position);
        if (holder instanceof FirstViewHolder) {
        } else {
            ((SecondViewHolder) holder).mainTitle.setText(item.musicName);
            ((SecondViewHolder) holder).title.setText(item.artist);

        }

        //TODO:播放状态图标显示的控制
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void updateDataSet(List<MusicInfo> folderInfos) {
        items = folderInfos;
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View rootView;
        public ImageView moreOverflow;
        public TextView mainTitle, title;
        public TintImageView playState;

        public SecondViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mainTitle = (TextView) rootView.findViewById(R.id.viewpager_list_toptext);
            this.title = (TextView) rootView.findViewById(R.id.viewpager_list_bottom_text);
            this.playState = (TintImageView) rootView.findViewById(R.id.play_state);
            this.moreOverflow = (ImageView) rootView.findViewById(R.id.viewpager_list_button);
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

    public static class FirstViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TintImageView mPlayAll;
        private TextView mPlayAllText;
        private TextView mPlayAllNumber;
        private LinearLayout mLlSelect;
        private View rootView;

        public FirstViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mPlayAll = (TintImageView) rootView.findViewById(R.id.play_all);
            this.mPlayAllText = (TextView) rootView.findViewById(R.id.play_all_text);
            this.mPlayAllNumber = (TextView) rootView.findViewById(R.id.play_all_number);
            this.mLlSelect = (LinearLayout) rootView.findViewById(R.id.ll_select);
            this.mLlSelect.setOnClickListener(this);
            this.rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_select:
                    Snackbar.make(mLlSelect, "click select", Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.rl_play_all:
                    Snackbar.make(rootView, "click play all", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
