package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;

/**
 * Created by 王天明 on 2016/11/4.
 */

public class PeopleGridAdapter extends BaseAdapter {

    String[] names;
    Context context;

    public PeopleGridAdapter(String[] names, Context context) {
        this.names = names;
        this.context = context;
    }

    class ViewHolder {
        TextView tv_peopleName;
        ViewHolder(View v) {
            tv_peopleName = (TextView) v.findViewById(R.id.tv_peopleName);
        }
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public String getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_office, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_peopleName.setText(getItem(position));
        return convertView;
    }

}
