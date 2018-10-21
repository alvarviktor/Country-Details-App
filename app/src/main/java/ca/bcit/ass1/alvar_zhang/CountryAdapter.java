package ca.bcit.ass1.alvar_zhang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<Country> {
    Context _context;
    // List of Characters read from array
    public CountryAdapter(Context context, ArrayList<Country> countries) {
        super(context, 0, countries);
        _context = context;
    }

    @Override
    // For every row
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        Country country = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // LayoutInflater knows to inflate a Fragment
            // Fragments are Layouts that live inside other layouts you can dynamically create
            // Inflate Layout based on this row & start adding to it
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.countryName);
        // Populate the data into the template view using the data object
        tvName.setText(country.getName());

//        ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.flagImage);
//        //DownloadImageTask dit = new DownloadImageTask(_context, imgOnePhoto);
//        //dit.execute(toon.getPicture());
//        // Use the Downloader task to get the picture & attach to the View
//        if (country.getFlag() != null) {
//            new ImageDownloaderTask(imgOnePhoto).execute(country.getFlag());
//        }

        // Return the completed view to render on screen
        return convertView;
    }
}


