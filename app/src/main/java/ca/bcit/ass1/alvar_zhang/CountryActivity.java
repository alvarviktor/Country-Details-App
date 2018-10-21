package ca.bcit.ass1.alvar_zhang;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryActivity extends ListActivity {

    private String TAG = CountryActivity.class.getSimpleName(); // get simple name of class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int regionIndex = (int) getIntent().getExtras().get("regionIndex");
        ArrayAdapter<Country> countryAdapter = null;
        switch (regionIndex) {
            case 0:
                countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Country.AFRICA);
                break;
            case 1:
                countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Country.AMERICAS);
                break;
            case 2:
                countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Country.ASIA);
                break;
            case 3:
                countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Country.EUROPE);
                break;
            case 4:
                countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Country.OCEANIA);
                break;
            default:
                Log.e(TAG, "Incorrect regionIndex: " + regionIndex);
        }
        final ListView listCountries = getListView();
        listCountries.setAdapter(countryAdapter);

        listCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(CountryActivity.this, CountryDetailActivity.class);
                i.putExtra("countryObject", (Country) listCountries.getItemAtPosition(position));
                startActivity(i);
            }
        });
    }

}
