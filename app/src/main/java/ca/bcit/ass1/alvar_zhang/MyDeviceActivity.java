package ca.bcit.ass1.alvar_zhang;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MyDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Device Information
        String manufacturer = "Manufacturer: " + Build.MANUFACTURER;
        String model = "Model: " + Build.MODEL;
        String version = "Version: " + Build.VERSION.SDK_INT;
        String version_release = "Version Release: " + Build.VERSION.RELEASE;
        String serial_number = "Serial Number: " + Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        // TextView Variables
        TextView manufacturerTextView = findViewById(R.id.manufacturer);
        TextView modelTextView = findViewById(R.id.model);
        TextView versionTextView = findViewById(R.id.version);
        TextView versionReleaseTextView = findViewById(R.id.version_release);
        TextView serialNumberTextView = findViewById(R.id.serial_number);

        // Setting Each TextView Text
        manufacturerTextView.setText(manufacturer);
        modelTextView.setText(model);
        versionTextView.setText(version);
        versionReleaseTextView.setText(version_release);
        serialNumberTextView.setText(serial_number);

    }

}
