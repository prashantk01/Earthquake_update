package com.example.prince.earthquake_update;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Earthquake> earthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a fake list of earthquake locations.
        setContentView(R.layout.activity_main);
        earthquakes=Query_Util.earthquakes;
       /* earthquakes.add(new Earthquake("4.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("3.5","Turkee","April 6, 2018"));
        earthquakes.add(new Earthquake("2.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("5.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("6.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("1.5","Japan","May 9, 2018"));*/

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
      final EarthquakeAdapter adapter=new EarthquakeAdapter(this,earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Earthquake currentearthquake= adapter.getItem(position);
                Uri earthquakeUri=Uri.parse(currentearthquake.getMurl());
                Intent newIntent= new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(newIntent);
            }
        });
    }
}
