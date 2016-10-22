package com.palmap.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by aoc on 2016/10/22.
 */

public class ImageAlbumAdapter extends RecyclerView.Adapter<ImageAlbumAdapter.ViewCache> {

    private Context context;
    private List<String> imageUrl;

    public ImageAlbumAdapter(Context context) {
        this.context = context;
    }

    public ImageAlbumAdapter(Context context, List<String> imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public ViewCache onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewCache(LayoutInflater.from(context).inflate(R.layout.item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewCache holder, int position) {

        int height = position % 2 == 0 ? DeviceUtils.dip2px(context, 165) : DeviceUtils.dip2px(context, 210);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        holder.imageView.setLayoutParams(lp);
        Glide.with(context).load(imageUrl.get(position))
                .centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    class ViewCache extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewCache(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
