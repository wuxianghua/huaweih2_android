package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.ParkInfo;

import java.util.List;

/**
 * Created by eric3 on 2016/11/12.
 */

public class ParkInfoAdapter extends BaseAdapter{
  private Context mContext;
  private List<ParkInfo> parkInfoList;
  private ParkInfoAdapter.OnItemClickListener onItemClickListener;

  public ParkInfoAdapter(Context context, List<ParkInfo> parkInfoList,ParkInfoAdapter.OnItemClickListener onItemClickListener){
    this.mContext = context;
    this.parkInfoList = parkInfoList;
    this.onItemClickListener = onItemClickListener;
  }


  public void notifyDataChanged(List<ParkInfo> parkInfoList) {
    this.parkInfoList = parkInfoList;
    super.notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    if (parkInfoList == null){
      return 0;
    }
    return parkInfoList.size();
  }

  @Override
  public ParkInfo getItem(int position) {
    return parkInfoList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ParkInfoAdapter.ViewHolder viewHolder;
    if (convertView == null){
      viewHolder = new ParkInfoAdapter.ViewHolder();
      convertView = LayoutInflater.from(mContext).inflate(R.layout.carinfo_list_item, null);
      viewHolder.name = (TextView) convertView.findViewById(R.id.tv_park_name);

      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ParkInfoAdapter.ViewHolder) convertView.getTag();
    }

    // 设置控件内容
    String carNum =parkInfoList.get(position).getCarNum();
    viewHolder.name.setText(carNum);

    viewHolder.name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onItemClickListener.onClicked(parkInfoList.get(position));
      }
    });

    return convertView;
  }

  public interface OnItemClickListener{
    void onClicked(ParkInfo parkInfo);
  }
  class ViewHolder{
    TextView name;
  }
}
