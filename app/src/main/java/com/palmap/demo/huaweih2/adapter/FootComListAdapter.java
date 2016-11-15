package com.palmap.demo.huaweih2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.json.CommentDown;
import com.palmap.demo.huaweih2.util.VisitorHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wtm on 2016/11/14.
 */

public class FootComListAdapter extends BaseAdapter {

    private List<CommentDown> data;
    private Context context;
    private SimpleDateFormat sdf;

    public FootComListAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CommentDown getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<CommentDown> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.com_list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentDown commentDown = getItem(position);
//        holder.com_name.setText("访客" + commentDown.getId());
        holder.com_name.setText(VisitorHelper.createName(commentDown.getId()));
        holder.com_time.setText(sdf.format(new Date(commentDown.getComTime())));
        holder.loc.setText(commentDown.getLocation());
        holder.com_text.setText(commentDown.getComment());
        return convertView;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    private class ViewHolder {

        private TextView com_name, com_time, loc, com_text;

        public ViewHolder(View convertView) {
            com_name = (TextView) convertView.findViewById(R.id.com_name);
            com_time = (TextView) convertView.findViewById(R.id.com_time);
            loc = (TextView) convertView.findViewById(R.id.loc);
            com_text = (TextView) convertView.findViewById(R.id.com_text);
            convertView.setTag(this);
        }

    }
}
