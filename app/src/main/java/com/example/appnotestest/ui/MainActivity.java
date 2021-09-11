package com.example.appnotestest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appnotestest.R;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.container_main);

        if (fragment instanceof OnBackPressed) {

            boolean result = ((OnBackPressed) fragment).onBackPressed();

            if (!result) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}