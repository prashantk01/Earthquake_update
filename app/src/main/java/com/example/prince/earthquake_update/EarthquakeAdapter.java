package com.example.prince.earthquake_update;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View listitemView=convertView;
        if(listitemView == null) {
        listitemView=LayoutInflater.from(getContext()).inflate(
                R.layout.earthquake_list_item,parent,false);

        }
        Earthquake currentEarthquake=getItem(position);
        TextView magnitude=(TextView) listitemView.findViewById(R.id.mag);
        magnitude.setText(currentEarthquake.getMagnitude());
        TextView location=(TextView) listitemView.findViewById(R.id.loc);
        location.setText(currentEarthquake.getLocation());
        TextView date=(TextView) listitemView.findViewById(R.id.date);
        date.setText(currentEarthquake.getDate());
        return listitemView;
    }
}