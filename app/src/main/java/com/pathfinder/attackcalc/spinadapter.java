package com.pathfinder.attackcalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pathfinder.attackcalc.R;

public class spinadapter extends ArrayAdapter<String> {

    Context context;
    int[] images;


    public spinadapter(Context context, String[] Languages,int[] images) {
        super(context, R.layout.spinner_dice,Languages);
        this.context=context;
        this.images=images;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.spinner_dice,null);
        ImageView v1 = (ImageView) row.findViewById(R.id.flag);
        v1.setImageResource(images[position]);
        return row;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  row =inflater.inflate(R.layout.spinner_dice,null);
        ImageView v1 = (ImageView) row.findViewById(R.id.flag);
        v1.setImageResource(images[position]);
        return row;
    }
}
