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

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import dev.tinelix.holoexperience.library.widget.HoloCompatToolbar;

public class HoloCompatActivity extends FragmentActivity {

    private HoloCompatToolbar mActionBarView;
    private LinearLayout mLayout;
    private HoloCompatActionBar mActionBarWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBarWrapper = new HoloCompatActionBar(this);
        mLayout = new LinearLayout(this);
        mLayout.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void setContentView(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setContentView(view);
            return;
        }

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
    }

    @Override
    public void setContentView(int layoutResID) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setContentView(layoutResID);
            return;
        }

        View view = getLayoutInflater().inflate(layoutResID, null, false);
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
    }

    public void setActionBar(HoloCompatToolbar layout) {
        mActionBarView = layout;
    }

    public HoloCompatActionBar getSupportActionBar() {
        return mActionBarWrapper;
    }
}
