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

package dev.tinelix.holoexperience.library.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.tinelix.holoexperience.library.HoloCompatSupport;
import dev.tinelix.holoexperience.library.R;
import dev.tinelix.holoexperience.library.app.HoloCompatActivity;

public class HoloCompatToolbar extends LinearLayout {

    private Drawable mActionBarDrawable;
    private int mActionBarColor = Color.TRANSPARENT;
    private Context mContext;

    public HoloCompatToolbar(Context context) {
        super(context);
        mContext = context;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.holo4ux_layout_actionbar, null);

        this.addView(view);

        view.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)(50 * getContext().getResources().getDisplayMetrics().scaledDensity)
        ));

        adjustLayout(getResources().getConfiguration());

        setLogo(context.getApplicationInfo().icon);

        if(context instanceof HoloCompatActivity)
            setTitle(((HoloCompatActivity) context).getTitle());

        findViewById(android.R.id.text2).setVisibility(GONE);
        findViewById(R.id.home_button).setVisibility(INVISIBLE);

        TypedArray a = context.obtainStyledAttributes(
                R.style.Holo4UX_Widget_ActionBar_Solid,
                new int[]{android.R.attr.background}
        );

        mActionBarDrawable = a.getDrawable(0);

        a.recycle();

        if(mActionBarDrawable != null)
            setBackgroundDrawable(mActionBarDrawable);
    }

    public HoloCompatToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context)
                .inflate(R.layout.holo4ux_layout_actionbar, null);

        this.addView(view);

        setLogo(context.getApplicationInfo().icon);
        if(context instanceof HoloCompatActivity)
            setTitle(((HoloCompatActivity) context).getTitle());

        findViewById(android.R.id.text2).setVisibility(GONE);
        findViewById(R.id.home_button).setVisibility(INVISIBLE);
    }

    public void setLogo(int drawableRes) {
        ImageView view = findViewById(android.R.id.icon);
        view.setImageDrawable(
                getContext().getResources().getDrawable(drawableRes)
        );
    }

    public void setLogo(Drawable drawable) {
        ImageView view = findViewById(android.R.id.icon);
        view.setImageDrawable(drawable);
    }

    public void setTitle(int stringRes) {
        TextView tv = findViewById(android.R.id.text1);
        tv.setText(stringRes);
    }

    public void setTitle(CharSequence sequence) {
        TextView tv = findViewById(android.R.id.text1);
        tv.setText(sequence);
    }

    public void setSubtitle(int stringRes) {
        TextView tv = findViewById(android.R.id.text2);
        tv.setText(stringRes);
        tv.setVisibility(tv.getText().length() > 0 ? VISIBLE : GONE);
    }

    public void setSubtitle(CharSequence sequence) {
        TextView tv = findViewById(android.R.id.text2);
        tv.setText(sequence);
        tv.setVisibility(sequence.length() > 0 ? VISIBLE : GONE);
    }

    public void setDisplayHomeAsUpEnabled(boolean value) {
        findViewById(R.id.home_button).setVisibility(value ? VISIBLE : INVISIBLE);
        findViewById(R.id.home_button_layout).setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mContext instanceof Activity) {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
                            ((Activity) mContext).onBackPressed();
                        else
                            ((Activity) mContext).onKeyUp(
                                KeyEvent.KEYCODE_BACK,
                                new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK)
                            );
                    }
                }
            }
        );
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        adjustLayout(newConfig);
    }

    private void adjustLayout(Configuration config) {
        float dp = getResources().getDisplayMetrics().scaledDensity;

        boolean isPortaitOrientation = config.orientation == Configuration.ORIENTATION_PORTRAIT;

        if(!HoloCompatSupport.isTablet(getContext())) {
            TextView tv1 = findViewById(android.R.id.text1);
            TextView tv2 = findViewById(android.R.id.text2);

            ImageView view = findViewById(android.R.id.icon);

            tv1.setTextSize(isPortaitOrientation ? 18.0f : 16.0f);

            ((LayoutParams) tv1.getLayoutParams()).topMargin = !isPortaitOrientation ?
                    (int) (-8 * dp) : 0;

            ((LayoutParams) tv2.getLayoutParams()).topMargin = !isPortaitOrientation ?
                    (int) (-4 * dp) : (int) (-2 * dp);

            ((LayoutParams) view.getLayoutParams()).topMargin = !isPortaitOrientation ?
                    (int) (-4 * dp) : 0;

            view.getLayoutParams().width = isPortaitOrientation ? (int)(38 * dp) : (int)(30 * dp);
            view.getLayoutParams().height = isPortaitOrientation ? (int)(38 * dp) : (int)(30 * dp);
        }
    }
}
