package com.icey.weather_list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ice on 4/5/2015.
 */
public class ForecastFragment extends Fragment
{

    public ArrayAdapter<String> myForecastAdapter;
    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == R.id.action_refresh)
        {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //array adapter begins

        myForecastAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast,R.id.list_item_forecast_textview,new ArrayList<String>());

        myForecastAdapter.notifyDataSetChanged();
        //array adapter ends

        //Find listview begins
        ListView myListView=(ListView) rootView.findViewById(R.id.listview_forecast);

        ///setting adapter
        myListView.setAdapter(myForecastAdapter);

        //Find listview ends

        //Doing Toast
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        @Override
        public void onItemClick(AdapterView <?> adapterView,View view,int position,long l) {
            String forecast = myForecastAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, forecast);
            startActivity(intent);
        }
        });
        //Toasted


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        return rootView;
    }
    private void updateWeather()
    {
       
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), myForecastAdapter);
        ///allow user to choose location based on postal code
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location= prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        updateWeather();
    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
 * so for convenience we're breaking it out into its own method now.
 */


    /**
     * Prepare the weather high/lows for presentation.
     */


    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */




}
