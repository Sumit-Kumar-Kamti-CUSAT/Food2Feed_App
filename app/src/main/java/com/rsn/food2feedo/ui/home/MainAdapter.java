package com.rsn.food2feedo.ui.home;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rsn.food2feedo.R;

public class MainAdapter extends BaseAdapter {
    private Context c;
    private LayoutInflater inflater;
    private String[] name;
    private int[] nimage;
    MainAdapter(Context c,String[] name,int[] nimage){
        this.c=c;
        this.name=name;
        this.nimage=nimage;
    }
    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(inflater==null){
            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.rowgrid,null);
        }
        ImageView img=convertView.findViewById(R.id.img);
        TextView tv=convertView.findViewById(R.id.txt);
        img.setImageResource(nimage[position]);
        tv.setText(name[position]);
        return convertView;
    }
}

