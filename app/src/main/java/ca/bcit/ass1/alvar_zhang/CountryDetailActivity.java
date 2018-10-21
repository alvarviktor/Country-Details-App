package ca.bcit.ass1.alvar_zhang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        Bundle countryData = getIntent().getExtras();
        Country countryPassed = countryData.getParcelable("countryObject");

        // Country Name
        TextView name = findViewById(R.id.name);
        name.setText(countryPassed.getName());
    }
}
