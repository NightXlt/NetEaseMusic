/**
 * Created by Night on 2017/10/14.
 * Desc:
 */
package com.example.night.neteasemusic.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.example.night.neteasemusic.R;
import com.example.night.neteasemusic.info.FolderInfo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @author Night
 * @since 2017-10-14
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private final Context context;
    private List<FolderInfo> items;

    public FolderAdapter(List<FolderInfo> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FolderInfo item = items.get(position);
        holder.mainTitle.setText(item.folder_name);
        holder.title.setText(item.folder_count + "首 " + item.folder_path);
        holder.draweeView.setImageResource(R.mipmap.list_icn_folder);
    }

    //更新adpter的数据
    public void updateDataSet(List<FolderInfo> list) {
        this.items = list;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
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
                case R.id.linearlayout:
                    //TODO:点击布局播放音乐
                    Snackbar.make(rootView, "click rootview", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
