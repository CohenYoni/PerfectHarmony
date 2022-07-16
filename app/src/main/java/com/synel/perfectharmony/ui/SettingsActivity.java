package com.synel.perfectharmony.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.synel.perfectharmony.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.settings_container, new SettingsFragment())
            .commit();
    }
}
