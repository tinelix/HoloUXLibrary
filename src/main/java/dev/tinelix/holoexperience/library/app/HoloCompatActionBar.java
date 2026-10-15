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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import dev.tinelix.holoexperience.library.R;

public class HoloCompatActionBar extends ActionBar {
    private final ActionBar mActionBar;
    private final View mView;
    private final Context mContext;

    public HoloCompatActionBar(Context context, ActionBar bar) {
        mContext = context;
        mActionBar = bar;
        mView = View.inflate(context, R.layout.holo4ux_layout_actionbar, null);

        mActionBar.setCustomView(mView);
        mActionBar.setDisplayOptions(DISPLAY_SHOW_CUSTOM);

        if(context instanceof AppCompatActivity) {
            if(mActionBar.getTitle() != null)
                setTitle(mActionBar.getTitle());
            else
                setTitle(((AppCompatActivity) context).getTitle());

            if(mActionBar.getTitle() != null)
                setSubtitle(mActionBar.getSubtitle());
            else
                mView.findViewById(android.R.id.text2).setVisibility(View.GONE);

            setLogo(((AppCompatActivity) context).getApplication().getApplicationInfo().icon);
            setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void setCustomView(View view) {
        mActionBar.setCustomView(view);
    }

    @Override
    public void setCustomView(View view, LayoutParams layoutParams) {
        mActionBar.setCustomView(view, layoutParams);
    }

    @Override
    public void setCustomView(int resId) {
        mActionBar.setCustomView(resId);
    }

    @Override
    public void setIcon(int resId) {
        ImageView iconView = mView.findViewById(android.R.id.icon);
        iconView.setImageDrawable(mContext.getResources().getDrawable(resId));
    }

    @Override
    public void setIcon(Drawable icon) {
        ImageView iconView = mView.findViewById(android.R.id.icon);
        iconView.setImageDrawable(icon);
    }

    @Override
    public void setLogo(int resId) {
        ImageView iconView = mView.findViewById(android.R.id.icon);
        iconView.setImageDrawable(mContext.getResources().getDrawable(resId));
    }

    @Override
    public void setLogo(Drawable logo) {
        ImageView iconView = mView.findViewById(android.R.id.icon);
        iconView.setImageDrawable(logo);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter adapter, OnNavigationListener callback) {

    }

    @Override
    public void setSelectedNavigationItem(int position) {

    }

    @Override
    public int getSelectedNavigationIndex() {
        return 0;
    }

    @Override
    public int getNavigationItemCount() {
        return 0;
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView textView = mView.findViewById(android.R.id.text1);
        textView.setText(title);
    }

    @Override
    public void setTitle(int resId) {
        TextView textView = mView.findViewById(android.R.id.text1);
        textView.setText(mContext.getResources().getString(resId));
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        TextView textView = mView.findViewById(android.R.id.text2);
        textView.setText(subtitle);
    }

    @Override
    public void setSubtitle(int resId) {
        TextView textView = mView.findViewById(android.R.id.text2);
        textView.setText(mContext.getResources().getString(resId));
    }

    @Override
    public void setDisplayOptions(int options) {

    }

    @Override
    public void setDisplayOptions(int options, int mask) {

    }

    @Override
    public void setDisplayUseLogoEnabled(boolean useLogo) {
        mView.findViewById(android.R.id.icon).setVisibility(
                useLogo ? View.VISIBLE : View.GONE
        );
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean showHome) {

    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        mView.findViewById(R.id.home_button).setVisibility(
                showHomeAsUp ? View.VISIBLE : View.INVISIBLE
        );
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean showTitle) {
        mView.findViewById(android.R.id.text1).setVisibility(
                showTitle ? View.VISIBLE : View.GONE
        );
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean showCustom) {

    }

    @Override
    public void setBackgroundDrawable(@Nullable Drawable d) {
        mActionBar.setBackgroundDrawable(d);
    }

    @Override
    public View getCustomView() {
        return mActionBar.getCustomView();
    }

    @Nullable
    @Override
    public CharSequence getTitle() {
        return mActionBar.getTitle();
    }

    @Nullable
    @Override
    public CharSequence getSubtitle() {
        return mActionBar.getSubtitle();
    }

    @Override
    public int getNavigationMode() {
        return mActionBar.getNavigationMode();
    }

    @Override
    public void setNavigationMode(int mode) {

    }

    @Override
    public int getDisplayOptions() {
        return mActionBar.getDisplayOptions();
    }

    @Override
    public Tab newTab() {
        return null;
    }

    @Override
    public void addTab(Tab tab) {

    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {

    }

    @Override
    public void addTab(Tab tab, int position) {

    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {

    }

    @Override
    public void removeTab(Tab tab) {

    }

    @Override
    public void removeTabAt(int position) {

    }

    @Override
    public void removeAllTabs() {

    }

    @Override
    public void selectTab(Tab tab) {

    }

    @Nullable
    @Override
    public Tab getSelectedTab() {
        return null;
    }

    @Override
    public Tab getTabAt(int index) {
        return null;
    }

    @Override
    public int getTabCount() {
        return 0;
    }

    @Override
    public int getHeight() {
        return mActionBar.getHeight();
    }

    @Override
    public void show() {
        mActionBar.show();
    }

    @Override
    public void hide() {
        mActionBar.hide();
    }

    @Override
    public boolean isShowing() {
        return mActionBar.isShowing();
    }

    @Override
    public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

    }

    @Override
    public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {

    }
}
