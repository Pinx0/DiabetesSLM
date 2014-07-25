package com.pablolopezponce.diabetesslm.resources;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pablolopezponce.diabetesslm.R;

import java.util.ArrayList;

public class NavigationAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<DrawerMenuItem> arrayitems;

    public NavigationAdapter(Activity activity, ArrayList<DrawerMenuItem> listarray) {
        super();
        this.activity = activity;
        this.arrayitems = listarray;
    }
    @Override
    public Object getItem(int position) {
        return arrayitems.get(position);
    }
    public int getCount() {
        return arrayitems.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class Row {
        TextView itemTitle;
        ImageView icon;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Row view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null) {
            view = new Row();
            DrawerMenuItem item = arrayitems.get(position);
            convertView = inflator.inflate(R.layout.drawer_menu_item, null);
            view.itemTitle = (TextView) convertView.findViewById(R.id.drawer_menu_item_text);
            view.itemTitle.setText(item.getTitle());
            view.icon = (ImageView) convertView.findViewById(R.id.drawer_menu_item_icon);
            view.icon.setImageResource(item.getIcon());
            convertView.setTag(view);
        } else {
            view = (Row) convertView.getTag();
        }
        return convertView;
    }
}
