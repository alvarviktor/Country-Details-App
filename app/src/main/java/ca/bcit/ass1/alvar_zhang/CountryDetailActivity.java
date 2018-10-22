package ca.bcit.ass1.alvar_zhang;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class CountryDetailActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private Country countryPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        // Sets the ActionBar as a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Country Object from previous Activity
        Bundle countryData = getIntent().getExtras();
        countryPassed = countryData.getParcelable("countryObject");

        // Image Flag
        String flagURL = countryPassed.getFlag();
        WebView flagView = findViewById(R.id.flag);
        flagView.setInitialScale(1);
        flagView.getSettings().setLoadWithOverviewMode(true);
        flagView.getSettings().setUseWideViewPort(true);
        flagView.setBackgroundColor(Color.TRANSPARENT);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            // Portrait Orientation
            String dataHTML = "<html><body align='center'><img src=\"" + flagURL + "\" width='80%'></body></html>";
            flagView.loadData(dataHTML, "text/html", null);
        } else {
            // Landscape Orientation
            String dataHTML = "<html><body align='center'><img src=\"" + flagURL + "\" width='20%'></body></html>";
            flagView.loadData(dataHTML, "text/html", null);
        }

        // Country Name
        TextView name = findViewById(R.id.name);
        name.setText(countryPassed.getName());

        // Country Capital
        TextView capital = findViewById(R.id.capital);
        String capitalText = "Capital: " + countryPassed.getCapital();
        capital.setText(capitalText);

        // Country Region
        TextView region = findViewById(R.id.region);
        String regionText = "Region: " + countryPassed.getRegion();
        region.setText(regionText);

        // Country Population
        TextView population = findViewById(R.id.population);
        String populationText = "Population: " + countryPassed.getPopulation();
        population.setText(populationText);

        // Country Area
        TextView area = findViewById(R.id.area);
        String areaText = "Area: " + countryPassed.getArea();
        area.setText(areaText);

        // Country Borders
        TextView borders = findViewById(R.id.borders);
        String bordersText = "Borders: " + countryPassed.getBorders();
        borders.setText(bordersText);
    }

    // Sends String Data of Country to Messaging App
    // PRE: Parameters must be a menu
    // POST: Shares Country Details to Messaging App
    // RETURN: boolean
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_message);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        String messageBody = "Country: " + countryPassed.getName() +
                "\nCapital: " + countryPassed.getCapital() +
                "\nRegion: " + countryPassed.getRegion() +
                "\nPopulation: " + countryPassed.getPopulation() +
                "\nArea: " + countryPassed.getArea() +
                "\nBorders: " + countryPassed.getBorders();
        setShareActionIntent(messageBody);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // Determines which activity to start based on user click
    // PRE: Parameter must be a MenuItem
    // POST: Starts an activity based on user click
    // RETURN: boolean
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_message:
                return true;
            case R.id.action_menu:
                Intent i = new Intent(CountryDetailActivity.this, MyDeviceActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sets the messaging app intent
    // PRE: Parameter must be a String
    // POST: Determines what type of share intent is used
    // RETURN: void
    private void setShareActionIntent(String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(i);
    }

}
