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

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup;

import dev.tinelix.holoexperience.library.R;
import dev.tinelix.holoexperience.library.app.HoloCompatActivity;
import dev.tinelix.holoexperience.library.widget.HoloCompatToolbar;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class HoloCompatActionBar {

    private final Context mContext;
    private HoloCompatToolbar mActionBar;
    private ActionBar mActionBarHoneycomb;

    public HoloCompatActionBar(Context context) {
        mContext = context;

        float dp = context.getResources().getDisplayMetrics().scaledDensity;

        if(context instanceof HoloCompatActivity) {
            HoloCompatActivity activity = (HoloCompatActivity) context;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mActionBarHoneycomb = activity.getActionBar();
            else {
                mActionBar = new HoloCompatToolbar(mContext);
                mActionBar.setId(R.id.action_bar);
                activity.setActionBar(mActionBar);
            }
        }
    }

    public void setDisplayHomeAsUpEnabled(boolean value) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mActionBarHoneycomb.setDisplayHomeAsUpEnabled(true);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                mActionBarHoneycomb.setHomeButtonEnabled(true);
        } else {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setLogo(int resId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            mActionBarHoneycomb.setLogo(resId);
        else if(mActionBar != null)
            mActionBar.setLogo(resId);
    }

    public void setLogo(Drawable drawable) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            mActionBarHoneycomb.setLogo(drawable);
        else if(mActionBar != null)
            mActionBar.setLogo(drawable);
    }

    public void setTitle(int resId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mActionBarHoneycomb.setTitle(resId);
        else if(mActionBar != null)
            mActionBar.setTitle(resId);
    }

    public void setTitle(CharSequence title) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mActionBarHoneycomb.setTitle(title);
        else if(mActionBar != null)
            mActionBar.setTitle(title);
    }

    public void setSubtitle(int resId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mActionBarHoneycomb.setSubtitle(resId);
        else if(mActionBar != null)
            mActionBar.setSubtitle(resId);
    }

    public void setSubtitle(CharSequence subtitle) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mActionBarHoneycomb.setSubtitle(subtitle);
        else if(mActionBar != null)
            mActionBar.setSubtitle(subtitle);
    }

    public void setHeight(int px) {
        if(mActionBar != null) {
            if(mActionBar.getLayoutParams() != null) {
                mActionBar.getLayoutParams().height = px;
            } else
                mActionBar.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                px
                        )
                );
        }
    }
}
