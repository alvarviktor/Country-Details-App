package ca.bcit.ass1.alvar_zhang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TopLevelActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    private String TAG = TopLevelActivity.class.getSimpleName(); // get simple name of class
    // ProgressDialog Box
    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String SERVICE_URL = "https://restcountries.eu/rest/v2/all"; // where data is coming from

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        // Sets the ActionBar as a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Executes AsyncTask to retrieve and sort Countries
        new GetContacts().execute();

        // Create a ListView of Regions
        ListView regionList = findViewById(R.id.list_regions);
        regionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long l) {
                Intent intent = new Intent(TopLevelActivity.this, CountryActivity.class);
                intent.putExtra("regionIndex", id);
                startActivity(intent);
            }
        });
    }

    // Creates the Option Menu on the App Bar
    // PRE: Parameters must be a menu
    // POST:
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_message);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Join us on this vacation.");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_message:
                return true;
            case R.id.action_menu:
                Intent i = new Intent(TopLevelActivity.this, MyDeviceActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setShareActionIntent(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(i);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        // Displays the Progress Dialog of the background AsyncTask
        // PRE: Zero Parameters
        // POST: Creates a new ProgressDialog
        // RETURN: void
        protected void onPreExecute() {
            super.onPreExecute();
//            // Progress Dialog Setup
//            pDialog = new ProgressDialog(TopLevelActivity.this);
//            pDialog.setCancelable(true); // allows user to cancel process
//            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // sets style
//            pDialog.setMax(100); // max amount of progress units
//            pDialog.setMessage(getString(R.string.downloadingCountries));
//
//            // Make Cancel Button
//            pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.progressCancel),
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss(); // removes progress dialog
//                        }
//                    });
//            pDialog.show();
        }

//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            pDialog.setProgress(values[0]);
//        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray countryJsonArray = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < countryJsonArray.length(); i++) {
                        JSONObject c = countryJsonArray.getJSONObject(i);

                        String name = c.getString("name");
//                        String capital = c.getString("capital");
                        String region = c.getString("region");
//                        String population = c.getString("area");
//                        String borders = c.getString("borders");
//                        String flag = c.getString("flag");

                        Country country = new Country();

                        // adding each child node to HashMap key => value
                        country.setName(name);
//                        country.setCapital(capital);
                        country.setRegion(region);
//                        country.setPopulation(Integer.parseInt(population));
//                        country.setBorders(new ArrayList<String>(Arrays.asList(borders.split(","))));
//                        country.setFlag(flag);

                        // Add Countries to their Region
                        switch (region) {
                            case "Africa":
                                Country.AFRICA.add(country);
                                break;
                            case "Americas":
                                Country.AMERICAS.add(country);
                                break;
                            case "Asia":
                                Country.ASIA.add(country);
                                break;
                            case "Europe":
                                Country.EUROPE.add(country);
                                break;
                            case "Oceania":
                                Country.OCEANIA.add(country);
                                break;
                            default:
                                Log.e(TAG, "Incorrectly Passed Region: " + region);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();

//            for (Country myCountry: Country.AFRICA) {
//                if (myCountry.getName().equals("Afghanistan")) {
//                    Log.e(TAG, "AFGHAN FOUND");
//                }
//            }

            //Toon[] toonArray = toonList.toArray(new Toon[toonList.size()]);

            //CountryAdapter adapter = new CountryAdapter(CountryActivity.this, countryList);

            // Attach the adapter to a ListView
//            lv.setAdapter(adapter);
        }
    }

}
