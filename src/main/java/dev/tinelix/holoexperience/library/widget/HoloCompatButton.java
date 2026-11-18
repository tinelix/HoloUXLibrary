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
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import dev.tinelix.holoexperience.library.R;

@SuppressLint("AppCompatCustomView")
public class HoloCompatButton extends Button {

    private boolean mTextAllCaps;
    private String mOriginalText;

    public HoloCompatButton(Context context) {
        super(context);
    }

    public HoloCompatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatButton, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatButton_textAllCaps, false);

        mOriginalText = getText().toString();

        setAllCaps(mTextAllCaps);

        a.recycle();
    }

    public HoloCompatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatButton, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatButton_textAllCaps, false);

        mOriginalText = getText().toString();

        setAllCaps(mTextAllCaps);

        a.recycle();
    }

    @Override
    public void setAllCaps(boolean value) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.mTextAllCaps = value;

            setText(value ? mOriginalText.toUpperCase() : mOriginalText);
            return;
        }
        super.setAllCaps(value);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(mTextAllCaps)
            super.setText(text.toString().toUpperCase(), type);
        else
            super.setText(text, type);
    }
}
