package com.ozzmhmt.customcamera.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ozzmhmt.customcamera.Manager.BitmapManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by mehmet on 19.11.2017.
 */

public class IconAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> ids;
    private HashMap<String, String> idIcon;
    private HashMap<String, String> idName;
    private int iconSize;
    private BitmapManager bitmapManager;
    private int assetsSum;
    private int selectedPos;

    @SuppressWarnings("unchecked")
    public IconAdapter(Context context, LinkedHashMap<String, String> idicon,
                       HashMap<String, String> idname, int iconsize) {
        this.context = context;
        ids = new ArrayList<String>();
        if(idicon != null) {
            idIcon = (HashMap<String, String>) idicon.clone();
            for(String id : idicon.keySet()) {
                ids.add(id);
            }//for id
        }
        else
            idIcon = new HashMap<String, String>();

        if(idname != null)
            idName = (HashMap<String, String>) idname.clone();
        else
            idName = new HashMap<String, String>();

        iconSize = iconsize;
        bitmapManager = new BitmapManager(iconsize, iconsize);
        assetsSum = ids.size();
    }
    @SuppressWarnings("unchecked")
    public IconAdapter(Context context, LinkedHashMap<String, String> idicon, int iconsize) {
        this.context = context;
        ids = new ArrayList<String>();
        if(idicon != null) {
            idIcon = (HashMap<String, String>) idicon.clone();
            for(String id : idicon.keySet()) {
                ids.add(id);
            }//for id
        }
        else
            idIcon = new HashMap<String, String>();

        idName = null;

        iconSize = iconsize;
        bitmapManager = new BitmapManager(iconsize, iconsize);
        assetsSum = ids.size();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ids.size();
    }
    public void addItems(LinkedHashMap<String, String> idicon, HashMap<String, String> idname) {
        idIcon.putAll(idicon);
        idName.putAll(idname);
        for(String id : idicon.keySet()) {
            ids.add(id);
        }//for id

        this.notifyDataSetChanged();
    }
    public void addItems(LinkedHashMap<String, String> idicon) {
        idIcon.putAll(idicon);
        for(String id : idicon.keySet()) {
            ids.add(id);
        }//for id

        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        String iconpath = idIcon.get(ids.get(position));
        if(position < assetsSum)
            return bitmapManager.getBitmapFromAssets(context, iconpath);
        else
            return bitmapManager.getBitmap(iconpath);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public String getIconId(int position) {
        return ids.get(position);
    }
    //
    public void setSelected(int position) {
        selectedPos = position;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        String iconpath = idIcon.get(ids.get(position));
        Bitmap icon;
        if(position < assetsSum) {
            icon = bitmapManager.getBitmapFromAssets(context, iconpath);
        }
        else
            icon = bitmapManager.getBitmap(iconpath);

        int width = (int)(iconSize * 1.2);
        int height = (int)(iconSize * 1.2);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        if(selectedPos == position)
            layout.setBackgroundColor(0x88ff8800);

        ImageView iconview = new ImageView(context);
        iconview.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        iconview.setScaleType(ImageView.ScaleType.CENTER);
        iconview.setImageBitmap(icon);
        layout.addView(iconview);

        if(idName != null) {
            String name = idName.get(ids.get(position));
            if(name != null) {
                TextView textview = new TextView(context);
                textview.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
                textview.setGravity(Gravity.CENTER);
                textview.setTextSize(14);
                textview.setTextColor(Color.WHITE);
                textview.setText(name);
                layout.addView(textview);
            }
        }

        return layout;

    }

    public void recycleAll() {
        bitmapManager.recycleAll();
    }

}

