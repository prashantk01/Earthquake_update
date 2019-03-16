package com.example.prince.earthquake_update;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOC_SEPRATOR="of";
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
        TextView magnitude=(TextView) listitemView.findViewById(R.id.magnitude);
        String formattedmag=FormatMagnitude(currentEarthquake.getMagnitude());
        magnitude.setText(formattedmag);

// Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);




        //string partition
        String original_location= currentEarthquake.getLocation();
        String primary_location;
        String location_offset;
        if(original_location.contains(LOC_SEPRATOR)) {
            String[] divide = original_location.split(LOC_SEPRATOR);
            location_offset  = divide[0]+LOC_SEPRATOR;
            primary_location = divide[1];
        }
        else{
            location_offset=getContext().getString(R.string.near_the);
            primary_location=original_location;
        }

        TextView locationOff=(TextView) listitemView.findViewById(R.id.location_offset);
        locationOff.setText(location_offset);

        TextView locationPrimary=(TextView) listitemView.findViewById(R.id.primary_location);
        locationPrimary.setText(primary_location);





        TextView date=(TextView) listitemView.findViewById(R.id.date);
        Date dateObject = new Date(currentEarthquake.getDate());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        date.setText(dateToDisplay);

        TextView time=(TextView) listitemView.findViewById(R.id.time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormat.format(dateObject);
        time.setText(timeToDisplay);
        return listitemView;
    }

    private int getMagnitudeColor(Double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    private String FormatMagnitude(Double magnitude) {
        DecimalFormat magformat=new DecimalFormat("0.0");
        return magformat.format(magnitude);
    }
}