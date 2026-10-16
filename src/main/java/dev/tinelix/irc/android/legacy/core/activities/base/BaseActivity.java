package dev.tinelix.irc.android.legacy.core.activities.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import dev.tinelix.holoexperience.library.app.HoloCompatActivity;
import dev.tinelix.irc.android.legacy.R;

public class BaseActivity extends HoloCompatActivity {

    private SharedPreferences appPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences getAppPreferences() {
        return appPrefs;
    }

    public void setActionBar() {

    }
}
