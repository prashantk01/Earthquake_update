package com.example.prince.earthquake_update;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    // public static ArrayList<Earthquake> earthquakes;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2.5&limit=100";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 0;
    private TextView mEmptyStateTextView;

    private EarthquakeAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a fake list of earthquake locations.
        setContentView(R.layout.activity_main);

        /* earthquakes=Query_Util.earthquakes;
        earthquakes.add(new Earthquake("4.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("3.5","Turkee","April 6, 2018"));
        earthquakes.add(new Earthquake("2.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("5.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("6.5","Fransico","May 5, 2018"));
        earthquakes.add(new Earthquake("1.5","Japan","May 9, 2018"));*/

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        //final EarthquakeAdapter adapter=new EarthquakeAdapter(this,earthquakes);
        madapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(madapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Earthquake currentearthquake = madapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentearthquake.getMurl());
                Intent newIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(newIntent);
            }
        });
        /* no need of Asyntask excute because we are deling with AsyncTaskLoader
        EarthquakeAsyncTask task=new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);*/

// Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {

        // Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        madapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            madapter.addAll(earthquakes);


        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        madapter.clear();
    }
}
