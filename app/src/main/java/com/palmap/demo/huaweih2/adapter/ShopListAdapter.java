package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.ActivityShopDetail;
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
        viewHolder.margin = (TextView) convertView.findViewById(R.id.margin);


        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ShopListAdapter.ViewHolder) convertView.getTag();
      }


      if (mShopList.get(position).getName().equals("电影院")){
        viewHolder.item.setVisibility(View.GONE);
        viewHolder.margin.setVisibility(View.VISIBLE);
        viewHolder.margin.setText("电影院");
      }else if (mShopList.get(position).getName().equals("美食")){
        viewHolder.item.setVisibility(View.GONE);
        viewHolder.margin.setVisibility(View.VISIBLE);
        viewHolder.margin.setText("美食");
      }else if (mShopList.get(position).getName().equals("便利店")){
        viewHolder.item.setVisibility(View.GONE);
        viewHolder.margin.setVisibility(View.VISIBLE);
        viewHolder.margin.setText("便利店");
      }else {


        viewHolder.item.setVisibility(View.VISIBLE);
        viewHolder.margin.setVisibility(View.GONE);
        viewHolder.img.setBackgroundResource(R.drawable.image5_1);

        // 设置控件内容
        viewHolder.name.setText(mShopList.get(position).getName());
        viewHolder.distance.setText(mShopList.get(position).getDistance() + "m");
        viewHolder.type.setText(mShopList.get(position).getType());
        viewHolder.location.setText(mShopList.get(position).getLocation());

        if (mShopList.get(position).getName().equals("韩客莱"))
          viewHolder.img.setBackgroundResource(R.drawable.hankelai);
        if (mShopList.get(position).getName().equals("松哥油焖大虾"))
          viewHolder.img.setBackgroundResource(R.drawable.youmendaxia);
        if (mShopList.get(position).getName().equals("illy新天地咖啡吧"))
          viewHolder.img.setBackgroundResource(R.drawable.illy);
        if (mShopList.get(position).getName().equals("中影博亚影城(坂田店)"))
          viewHolder.img.setBackgroundResource(R.drawable.boya);
        if (mShopList.get(position).getName().equals("千味涮"))
          viewHolder.img.setBackgroundResource(R.drawable.qianweishuan);
        if (mShopList.get(position).getName().equals("永和大王"))
          viewHolder.img.setBackgroundResource(R.drawable.yonghe);
        if (mShopList.get(position).getName().equals("华莱士"))
          viewHolder.img.setBackgroundResource(R.drawable.hualaishi);
        if (mShopList.get(position).getName().equals("麦当劳"))
          viewHolder.img.setBackgroundResource(R.drawable.maidanglao);
        if (mShopList.get(position).getName().equals("四云奶盖贡茶"))
          viewHolder.img.setBackgroundResource(R.drawable.gongcha);
        if (mShopList.get(position).getName().equals("7-11便利店"))
          viewHolder.img.setBackgroundResource(R.drawable.bianlidian711);

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            if (!mShopList.get(position).getName().equals("7-11便利店"))
              onItemClickListener.onClicked(mShopList.get(position));
            else {
              mContext.startActivity(new Intent(mContext, ActivityShopDetail.class));
            }
          }
        });
      }

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

  TextView margin;

}

}