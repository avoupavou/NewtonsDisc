package com.avoupavou.newtonsdisc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;



public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //public static final String KEY_PREF_SYNC_CONN = "pref_syncConnectionType";
    SeekBarPreference _seekBarPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //background image discontinued because of incompatibility
        //getListView().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_settings));
        addPreferencesFromResource(R.xml.preferences);

        // Get widgets :
        _seekBarPref = (SeekBarPreference) this.findPreference("SEEKBAR_VALUE");

        // Set listener :
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        // Set seekbar summary :
        int radius = PreferenceManager.getDefaultSharedPreferences(this).getInt("SEEKBAR_VALUE", 50);
        _seekBarPref.setSummary(this.getString(R.string.sensitivity_string).replace("$1", ""+radius));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Set seekbar summary :
        int radius = PreferenceManager.getDefaultSharedPreferences(this).getInt("SEEKBAR_VALUE", 50);
        _seekBarPref.setSummary(this.getString(R.string.sensitivity_string).replace("$1", ""+radius));
    }
}
