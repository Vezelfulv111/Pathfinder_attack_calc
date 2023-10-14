package com.pathfinder.attackcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pathfinder.attackcalc.R;

public class SpinAdapter extends ArrayAdapter<String> {

    Context context;
    int[] images;
    public SpinAdapter(Context context, String[] nums, int[] images) {
        super(context, R.layout.spinner_dice,nums);
        this.context=context;
        this.images=images;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.spinner_dice,null);
        ImageView v1 = row.findViewById(R.id.flag);
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
