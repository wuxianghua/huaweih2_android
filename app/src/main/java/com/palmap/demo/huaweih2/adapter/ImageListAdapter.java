package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.ImgCell;

import java.util.List;

/**
 * Created by eric3 on 2016/10/20.
 */

public class ImageListAdapter extends BaseAdapter {
  private Context mContext;
  private List<ImgCell> mImageList;
  private ImageListAdapter.OnItemClickListener onItemClickListener;

  public ImageListAdapter(Context context, List<ImgCell> imgList,ImageListAdapter.OnItemClickListener onItemClickListener){
    this.mContext = context;
    this.mImageList = imgList;
    this.onItemClickListener = onItemClickListener;
  }


  @Override
  public int getCount() {
    if (mImageList == null){
      return 0;
    }
    return mImageList.size();
  }

  @Override
  public ImgCell getItem(int position) {
    return mImageList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ImageListAdapter.ViewHolder viewHolder;
    if (convertView == null){
      viewHolder = new ImageListAdapter.ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(R.layout.img_list_item, null);
      viewHolder.img1 = (ImageView) convertView.findViewById(R.id.img1);
      viewHolder.img2 = (ImageView) convertView.findViewById(R.id.img1);
      viewHolder.img3 = (ImageView) convertView.findViewById(R.id.img1);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ImageListAdapter.ViewHolder) convertView.getTag();
    }

    // 设置控件内容
    viewHolder.img1.setImageBitmap(mImageList.get(position).getB1());
    viewHolder.img2.setImageBitmap(mImageList.get(position).getB2());
    viewHolder.img3.setImageBitmap(mImageList.get(position).getB3());

//    viewHolder.item.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        onItemClickListener.onClicked(mImageList.get(position));
//      }
//    });

    return convertView;
  }

  public interface OnItemClickListener{
    void onClicked(ImgCell img);
  }
  class ViewHolder{
    LinearLayout item;
    ImageView img1;
    ImageView img2;
    ImageView img3;
  }

}