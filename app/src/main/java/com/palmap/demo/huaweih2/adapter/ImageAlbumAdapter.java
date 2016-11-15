package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DeviceUtils;

import java.util.ArrayList;

/**
 * Created by 王天明 on 2016/10/22
 */

public class ImageAlbumAdapter extends RecyclerView.Adapter<ImageAlbumAdapter.ViewCache> {

    private Context context;
    private ArrayList<String> imageUrl;

    private AdapterView.OnItemClickListener onItemClickListener;

    public ImageAlbumAdapter(Context context) {
        this.context = context;
    }

    public ImageAlbumAdapter(Context context, ArrayList<String> imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public ViewCache onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewCache(LayoutInflater.from(context).inflate(R.layout.item_album, null, false));
    }

    @Override
    public void onBindViewHolder(ViewCache holder, final int position) {

        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DeviceUtils.dip2px(context, 220 + position % 2 * 20)
        ));


        String url = imageUrl.get(position);
        switch (url) {
            case Constant.ICS办公区:
                Glide.with(context).load(R.mipmap.ics_office)
                        .override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;
            case Constant.ICS实验室:
                Glide.with(context).load(R.mipmap.ics_laboratory).override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;

            case Constant.H2大楼:
                Glide.with(context).load(R.mipmap.ics_building).override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;

            case Constant.会议室:
                Glide.with(context).load(R.mipmap.ics_meeting_room).override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;

            case Constant.H2大厅:
                Glide.with(context).load(R.mipmap.ics_h2_dating).override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;

            default:
                Glide.with(context).load(imageUrl.get(position))
                        .override(DeviceUtils.getWidth(context) / 2 - 10, DeviceUtils.dip2px(context, 220 + position % 2 * 20))
                        .placeholder(R.mipmap.c_foot_nopic)
                        .error(R.mipmap.c_foot_fail)
                        .into(holder.imageView);
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, v, position, v.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    public void addAll(ArrayList<String> urlData2) {
        imageUrl.addAll(urlData2);
        notifyDataSetChanged();
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewCache extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewCache(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
