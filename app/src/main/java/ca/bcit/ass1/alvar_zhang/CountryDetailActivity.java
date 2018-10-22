package ca.bcit.ass1.alvar_zhang;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;


public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        // Get Country Object from previous Activity
        Bundle countryData = getIntent().getExtras();
        Country countryPassed = countryData.getParcelable("countryObject");

        // Image Flag
        String flagURL = countryPassed.getFlag();
        WebView flagView = findViewById(R.id.flag);
        flagView.setInitialScale(1);
        flagView.getSettings().setLoadWithOverviewMode(true);
        flagView.getSettings().setUseWideViewPort(true);
        flagView.setBackgroundColor(Color.TRANSPARENT);
        String dataHTML = "<html><body align='center'><img src=\"" + flagURL + "\" width='80%'></body></html>";
        flagView.loadData(dataHTML, "text/html", null);

        // Country Name
        TextView name = findViewById(R.id.name);
        name.setText(countryPassed.getName());

        // Country Capital
        TextView capital = findViewById(R.id.capital);
        capital.setText("Capital: " + countryPassed.getCapital());

        // Country Region
        TextView region = findViewById(R.id.region);
        region.setText("Region: " + countryPassed.getRegion());

        // Country Population
        TextView population = findViewById(R.id.population);
        population.setText("Population: " + countryPassed.getPopulation());

        // Country Area
        TextView area = findViewById(R.id.area);
        area.setText("Area: " + countryPassed.getArea());

        // Country Borders
        TextView borders = findViewById(R.id.borders);
        borders.setText("Borders: " + countryPassed.getBorders());
    }

}
