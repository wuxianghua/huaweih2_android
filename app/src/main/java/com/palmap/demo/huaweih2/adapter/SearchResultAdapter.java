package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmaplus.nagrand.data.LocationModel;

import java.util.List;

/**
 * Created by eric3 on 2016/10/11.
 */

public class SearchResultAdapter extends BaseAdapter{
    private Context mContext;
    private List<LocationModel> mLocationList;
    private OnItemClickListener onItemClickListener;

    public SearchResultAdapter(Context context, List<LocationModel> locationModels,OnItemClickListener onItemClickListener){
      this.mContext = context;
      this.mLocationList = locationModels;
      this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
      if (mLocationList == null){
        return 0;
      }
      return mLocationList.size();
    }

    @Override
    public LocationModel getItem(int position) {
      return mLocationList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (convertView == null){
        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.location_list_item, null);
        viewHolder.name = (TextView) convertView.findViewById(R.id.tv_location_name);
        viewHolder.floor = (TextView) convertView.findViewById(R.id.floor);
        viewHolder.item = (RelativeLayout) convertView.findViewById(R.id.item_t);

        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }

      // 设置控件内容
      String poiName = LocationModel.display.get(mLocationList.get(position));
      if ("办公室".equals(poiName)||"会议室".equals(poiName)){//加门牌号
        poiName = poiName +"  "+ LocationModel.address.get(mLocationList.get(position));
      }

      viewHolder.name.setText(poiName);
      long floorID= LocationModel.parent.get(mLocationList.get(position));
      if (floorID==Constant.FLOOR_ID_B1){
        viewHolder.floor.setText("B1");
      }else if (floorID==Constant.FLOOR_ID_F1){
        viewHolder.floor.setText("F1");
      }else {
        viewHolder.floor.setText("H2");
      }


      viewHolder.item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onItemClickListener.onClicked(mLocationList.get(position));
        }
      });

      return convertView;
    }

  public interface OnItemClickListener{
    void onClicked(LocationModel locationModel);
  }
class ViewHolder{
  RelativeLayout item;
  TextView name;
  TextView floor;
}

}