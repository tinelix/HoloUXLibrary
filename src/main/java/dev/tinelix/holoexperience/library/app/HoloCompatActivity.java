/*
 *  Copyright (C) 2013 The Android Open Source Project
 *  Copyright (C) 2026 Dmitry Tretyakov
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.tinelix.holoexperience.library.app;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ListPopupWindowCompat;
import android.support.v4.widget.PopupMenuCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dev.tinelix.holoexperience.library.HoloCompatSupport;
import dev.tinelix.holoexperience.library.R;
import dev.tinelix.holoexperience.library.menu.MenuItemAdapter;
import dev.tinelix.holoexperience.library.widget.HoloCompatToolbar;

public class HoloCompatActivity extends FragmentActivity {

    private HoloCompatToolbar mActionBarView;
    private LinearLayout mLayout;
    private HoloCompatActionBar mActionBarWrapper;
    private ListPopupWindow mPopupWindow;
    private View mMenuAnchor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarWrapper = new HoloCompatActionBar(this);
        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        float dp = getResources().getDisplayMetrics().scaledDensity;

        if(!HoloCompatSupport.isTablet(this)) {
            getSupportActionBar().setHeight(
                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                            (int) (50 * dp) : (int) (42 * dp)
            );
        }
    }

    @Override
    public void setContentView(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setContentView(view);
            return;
        }

        mMenuAnchor = new View(this);
        mMenuAnchor.setLayoutParams(
                new LinearLayout.LayoutParams(0, 0)
        );
        ((LinearLayout.LayoutParams) mMenuAnchor.getLayoutParams()).gravity = Gravity.CENTER;

        ViewGroup contentParent = findViewById(android.R.id.content);
        contentParent.removeAllViews();
        contentParent.addView(mLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        params.weight = 1;

        view.setLayoutParams(params);

        mLayout.removeAllViews();
        mLayout.addView(mActionBarView);
        mLayout.addView(view);
        mLayout.addView(mMenuAnchor);
    }

    @Override
    public void setContentView(int layoutResID) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setContentView(layoutResID);
            return;
        }

        View view = getLayoutInflater().inflate(layoutResID, null, false);
        mMenuAnchor = new View(this);
        mMenuAnchor.setLayoutParams(
                new LinearLayout.LayoutParams(0, 0)
        );
        ((LinearLayout.LayoutParams) mMenuAnchor.getLayoutParams()).gravity = Gravity.CENTER;

        ViewGroup contentParent = findViewById(android.R.id.content);

        contentParent.removeAllViews();
        contentParent.addView(mLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        params.weight = 1;

        view.setLayoutParams(params);

        mLayout.removeAllViews();
        mLayout.addView(mActionBarView);
        mLayout.addView(view);
        mLayout.addView(mMenuAnchor);
    }

    public void preparePopupMenu(int menuRes) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            return;

        mPopupWindow = new ListPopupWindow(this);
        mPopupWindow.setAnchorView(mMenuAnchor);
        final PopupMenu menu = new PopupMenu(this, mMenuAnchor);
        menu.getMenuInflater().inflate(menuRes, menu.getMenu());

        ArrayList<String> items = new ArrayList<>();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = getWindowManager();

        for(int i = 0; i < menu.getMenu().size(); i++) {
            items.add(menu.getMenu().getItem(i).getTitle().toString());
        }

        MenuItemAdapter adapter = new MenuItemAdapter(this, items);

        wm.getDefaultDisplay().getMetrics(metrics);

        mPopupWindow.setBackgroundDrawable(
                getResources().getDrawable(R.drawable.holo4ux_dark_dialog_full)
        );

        mPopupWindow.setWidth(metrics.widthPixels);
        mPopupWindow.setHorizontalOffset(-mMenuAnchor.getLeft());
        mPopupWindow.setAdapter(adapter);

        if(mPopupWindow.getListView() != null) {
            mPopupWindow.getListView().setCacheColorHint(
                    getResources().getColor(android.R.color.transparent)
            );
        }

        mPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMenuItemSelected(0, menu.getMenu().getItem(position));
                mPopupWindow.dismiss();
            }
        });
    }

    public void setActionBar(HoloCompatToolbar layout) {
        mActionBarView = layout;

    }

    public HoloCompatActionBar getSupportActionBar() {
        return mActionBarWrapper;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                if (mPopupWindow != null)
                    if(!mPopupWindow.isShowing())
                        mPopupWindow.show();
                    else
                        mPopupWindow.dismiss();
                else
                    Toast.makeText(
                            this, R.string.menu_is_not_available,
                            Toast.LENGTH_LONG
                    ).show();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        float dp = getResources().getDisplayMetrics().scaledDensity;

        if(!HoloCompatSupport.isTablet(this)) {
            getSupportActionBar().setHeight(
                    newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ?
                    (int) (50 * dp) : (int) (42 * dp)
            );
        }
    }
}
