package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.ICSModel;

import java.util.List;

/**
 * Created by wtm on 2016/11/14.
 */

public class ICSListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ICSModel> data;

    public ICSListViewAdapter(Context context, List<ICSModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ICSModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ics_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ICSModel model = getItem(position);
        viewHolder.tv_title.setText(model.getTitle());
        viewHolder.tv_description.setText(model.getDescription());
        viewHolder.imageView.setImageResource(model.getResId());
        return convertView;
    }

    private class ViewHolder{

        TextView tv_title,tv_description;
        ImageView imageView;

        public ViewHolder(View convertView) {
            tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
        }
    }
}
