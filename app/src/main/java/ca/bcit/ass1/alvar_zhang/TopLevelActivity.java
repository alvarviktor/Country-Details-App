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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TopLevelActivity extends AppCompatActivity {

    private String TAG = TopLevelActivity.class.getSimpleName(); // get simple name of class
    // ProgressDialog Box
    private ProgressDialog pDialog;
    // URL to get contacts JSON
    private static String SERVICE_URL = "https://restcountries.eu/rest/v2/all"; // where data is coming from

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

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

    /**
     * Async task class to get json by making HTTP call
     * Parameters <Void, Integer, Void>
     */
    private class GetContacts extends AsyncTask<Void, Integer, Void> {

        @Override
        // Displays the Progress Dialog of the background AsyncTask
        // PRE: Zero Parameters
        // POST: Creates a new ProgressDialog
        // RETURN: void
        protected void onPreExecute() {
            super.onPreExecute();
            // Progress Dialog Setup
            pDialog = new ProgressDialog(TopLevelActivity.this);
            pDialog.setCancelable(true); // allows user to cancel process
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // sets style
            pDialog.setMax(100); // max amount of progress units
            pDialog.setMessage(getString(R.string.downloadingCountries));

            // Make Cancel Button
            pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.progressCancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // removes progress dialog
                        }
                    });
            pDialog.show();
        }

        @Override
        // Progresses and Increments the Progress Dialog
        // PRE: Integer representing progress unit
        // POST: Sets the progress of the dialog
        // RETURN: void
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pDialog.setProgress(values[0]);
        }

        @Override
        // GETS JSON Objects and Creates County Objects
        // PRE: No parameter arguments
        // POST: Adds all Country Objects to ArrayList
        // RETURN: Void
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray countryJsonArray = new JSONArray(jsonStr);

                    // Progress Dialog Counter
                    int countryCounter = 0;

                    // looping through All Contacts
                    for (int i = 0; i < countryJsonArray.length(); i++) {
                        JSONObject c = countryJsonArray.getJSONObject(i);

                        String name = c.getString("name");
                        String capital = c.getString("capital");
                        String region = c.getString("region");
                        String population = c.getString("population");
                        String area = c.getString("area");
                        String borders = c.getString("borders");
                        String flag = c.getString("flag");

                        Country country = new Country();

                        // adding each child node to HashMap key => value
                        country.setName(name);
                        country.setCapital(capital);
                        country.setRegion(region);
                        country.setPopulation(population);
                        country.setArea(area);
                        country.setBorders(borders);
                        country.setFlag(flag);

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
                        // Increment Progress Counter
                        countryCounter++;
                        if (countryCounter % (countryJsonArray.length() / 25) == 0) {
                            // update progress
                            publishProgress(countryCounter * 100 / countryJsonArray.length());
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
        // Dismisses the progress dialog
        // PRE: No parameters
        // POST: Dismiss progress dialog
        // RETURN: void
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
                pDialog.dismiss();
        }
    }

}
