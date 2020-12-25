package com.avoupavou.newtonsdisc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            openAbout();
            return true;
        } else if (itemId == R.id.action_settings) {
            openSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void startWheel(View view) {
        Intent toWheel = new Intent(MainActivity.this, WheelActivity.class);
        startActivity(toWheel);
    }

    public void openAbout() {
        Intent toAbout = new Intent(MainActivity.this, About.class);
        startActivity(toAbout);
    }

    public void openSettings() {
        Intent toSettings = new Intent(MainActivity.this, Settings.class);
        startActivity(toSettings);
    }


}