package dev.tinelix.irc.android.legacy.core.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.SortedMap;

import dev.tinelix.holoexperience.library.test.HoloCompatTestActivity;
import dev.tinelix.irc.android.legacy.R;
import dev.tinelix.irc.android.legacy.core.activities.base.BaseActivity;
import dev.tinelix.irc.android.legacy.utils.IRCProfileManager;

public class MainActivity extends BaseActivity {

    private SortedMap<String, Charset> charsets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);

        installLayout();
        setActionBar();
    }

    private void installLayout() {
        TextView placeholderTitle = findViewById(R.id.placeholder_title);
        TextView placeholderDesc = findViewById(R.id.placeholder_description);
        Button placeholderButton = findViewById(R.id.placeholder_button);

        placeholderTitle.setText(getResources().getString(R.string.empty_profiles_list_title));
        placeholderDesc.setText(getResources().getString(R.string.empty_profiles_list_description));
        placeholderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IRCProfileManager.showCreateProfileDialog(MainActivity.this);
            }
        });
    }

    private void showAboutApplication() {
        Intent intent = new Intent(this, AboutApplicationActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_item) {
            showAboutApplication();
        } else if (id == R.id.holo4ux_item) {
            Intent intent = new Intent(this, HoloCompatTestActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
