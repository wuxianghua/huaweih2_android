package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.Shop;

import java.util.List;

/**
 * Created by eric3 on 2016/10/18.
 */

public class ShopListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Shop> mShopList;
    private ShopListAdapter.OnItemClickListener onItemClickListener;

    public ShopListAdapter(Context context, List<Shop> shopList,ShopListAdapter.OnItemClickListener onItemClickListener){
      this.mContext = context;
      this.mShopList = shopList;
      this.onItemClickListener = onItemClickListener;
    }
//  public ShopListAdapter(Context context, List<Shop> shopList,){
//    this.mContext = context;
//    this.mShopList = shopList;
//  }

    @Override
    public int getCount() {
      if (mShopList == null){
        return 0;
      }
      return mShopList.size();
    }

    @Override
    public Shop getItem(int position) {
      return mShopList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ShopListAdapter.ViewHolder viewHolder;
      if (convertView == null){
        viewHolder = new ShopListAdapter.ViewHolder();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_list_item, null);
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.type = (TextView) convertView.findViewById(R.id.type);
        viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
        viewHolder.location = (TextView) convertView.findViewById(R.id.location);
        viewHolder.item = (LinearLayout) convertView.findViewById(R.id.shop_item);
        viewHolder.img = (ImageView) convertView.findViewById(R.id.img_shop);

        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ShopListAdapter.ViewHolder) convertView.getTag();
      }

      // 设置控件内容
      viewHolder.name.setText(mShopList.get(position).getName());
      viewHolder.distance.setText(mShopList.get(position).getDistance()+"m");
      viewHolder.type.setText(mShopList.get(position).getType());
      viewHolder.location.setText(mShopList.get(position).getLocation());

      if (mShopList.get(position).getName().equals("LESSA主题餐厅"))
        viewHolder.img.setBackgroundResource(R.drawable.image5_2);
      if (mShopList.get(position).getName().equals("华莱士"))
        viewHolder.img.setBackgroundResource(R.drawable.image5_3);
      if (mShopList.get(position).getName().equals("illy咖啡"))
        viewHolder.img.setBackgroundResource(R.drawable.image5_4);
      if (mShopList.get(position).getName().equals("四云奶盖贡茶"))
        viewHolder.img.setBackgroundResource(R.drawable.image5_5);
      if (mShopList.get(position).getName().equals("7-11便利店"))
        viewHolder.img.setBackgroundResource(R.drawable.image5_6);

      viewHolder.item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          if (!mShopList.get(position).getName().equals("7-11便利店"))
            onItemClickListener.onClicked(mShopList.get(position));
        }
      });

      return convertView;
    }

public interface OnItemClickListener{
  void onClicked(Shop shop);
}
class ViewHolder{
  LinearLayout item;
  ImageView img;
  TextView name;
  TextView type;
  TextView location;
  TextView distance;
}

}