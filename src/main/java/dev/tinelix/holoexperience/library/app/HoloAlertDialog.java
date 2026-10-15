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
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import dev.tinelix.holoexperience.library.R;

public class HoloAlertDialog extends AlertDialog {

    DialogParams mParams;

    protected HoloAlertDialog(@NonNull Context context) {
        super(context);
    }

    protected HoloAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HoloAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public HoloAlertDialog(Context context, DialogParams params) {
        super(context);
        mParams = params;
        setTitle(params.mTitle);
        setView(params.mView);

        if(params.mNegativeButton != null)
            setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    params.mNegativeButton.mTitle,
                    params.mNegativeButton.mListener
            );

        if(params.mNeutralButton != null)
            setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    params.mNeutralButton.mTitle,
                    params.mNeutralButton.mListener
            );

        if(params.mPositiveButton != null)
            setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    params.mPositiveButton.mTitle,
                    params.mPositiveButton.mListener
            );
    }

    public static class DialogParams {
        protected int mThemeRes;
        protected String mTitle;
        protected View mView;
        protected DialogButton mNegativeButton;
        protected DialogButton mNeutralButton;
        protected DialogButton mPositiveButton;
        private int mButtonCount;

        public int getButtonCount() {

            mButtonCount = 0;

            if(mNeutralButton != null)
                mButtonCount++;

            if(mNegativeButton != null)
                mButtonCount++;

            if(mPositiveButton != null)
                mButtonCount++;

            return mButtonCount;
        }
    }

    public static class DialogButton {
        protected String mTitle;
        protected OnClickListener mListener;

        public DialogButton(String title, OnClickListener listener) {
            mTitle = title;
            mListener = listener;
        }
    }

    @SuppressWarnings("deprecation")
    public static class Builder extends AlertDialog.Builder {

        private Context mContext;
        private DialogParams mParams;

        public Builder(@NonNull Context context) {
            super(context);

            mContext = context;
            mParams = new DialogParams();
        }

        public Builder(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            mContext = context;
            mParams = new DialogParams();
            mParams.mThemeRes = themeResId;
        }

        @Override
        public AlertDialog.Builder setTitle(CharSequence title) {
            mParams.mTitle = title.toString();
            return this;
        }

        @Override
        public AlertDialog.Builder setTitle(int titleId) {
            mParams.mTitle = mContext.getResources().getString(titleId);
            return this;
        }

        @Override
        public AlertDialog.Builder setView(int layoutResId) {
            mParams.mView = View.inflate(mContext, layoutResId, null);
            return this;
        }

        @Override
        public AlertDialog.Builder setView(View view) {
            mParams.mView = view;
            return this;
        }

        @Override
        public AlertDialog.Builder setNegativeButton(int textId, OnClickListener listener) {
            mParams.mNegativeButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        @Override
        public AlertDialog.Builder setNeutralButton(int textId, OnClickListener listener) {
            mParams.mNeutralButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        @Override
        public AlertDialog.Builder setPositiveButton(int textId, OnClickListener listener) {
            mParams.mPositiveButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        @Override
        public AlertDialog create() {
            return new HoloAlertDialog(mContext, mParams);
        }

        public android.app.AlertDialog createCompatibleDialog() {
            android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(mContext)
                            .setTitle(mParams.mTitle)
                            .setView(mParams.mView);

            if (mParams.mNegativeButton != null)
                builder.setNegativeButton(
                        mParams.mNegativeButton.mTitle, mParams.mNegativeButton.mListener
                );

            if (mParams.mNeutralButton != null)
                builder.setNeutralButton(
                        mParams.mNeutralButton.mTitle, mParams.mNeutralButton.mListener
                );

            if (mParams.mPositiveButton != null)
                builder.setPositiveButton(
                        mParams.mPositiveButton.mTitle, mParams.mPositiveButton.mListener
                );

            return builder.create();
        }

        @Override
        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public android.app.AlertDialog showCompat() {
            android.app.AlertDialog dialog = createCompatibleDialog();
            dialog.show();
            return dialog;
        }
    }

    @Override
    public void show() {
        super.show();

        if(mParams.mThemeRes == 0 && getWindow() != null) {
            float dp = getContext().getResources().getDisplayMetrics().scaledDensity;
            int dividerSize = (int)(1.0 * dp);
            int bigDividerSize = (int)(2.0 * dp);
            int dividerColor = Color.parseColor("#444444");
            LinearLayout panel = getWindow().findViewById(android.support.v7.appcompat.R.id.parentPanel);

            if(mParams.mTitle != null) {
                View bigTitleDivider = new View(getContext());
                bigTitleDivider.setBackgroundColor(
                        getContext().getResources().getColor(R.color.holo_blue_bright)
                );
                bigTitleDivider.setLayoutParams(
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, bigDividerSize)
                );

                panel.addView(bigTitleDivider, mParams.mTitle == null ? 1 : 2);
            }

            Space space = getWindow().findViewById(android.support.v7.appcompat.R.id.spacer);
            space.setVisibility(View.GONE);

            View bottomButtonsDivider = new View(getContext());
            bottomButtonsDivider.setBackgroundColor(dividerColor);
            bottomButtonsDivider.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dividerSize)
            );

            /*
             * Since it is fourth element after R.id.customPanel in the 'abc_alert_dialog_material' layout,
             * we add it at the appropriate index.
             */
            panel.addView(bottomButtonsDivider, mParams.mTitle == null ? 3 : 4);

            ButtonBarLayout layout = getWindow().findViewById(android.support.v7.appcompat.R.id.buttonPanel);
            layout.setPadding(0, 0, 0, 0);

            View buttonBarDivider = new View(getContext());
            buttonBarDivider.setBackgroundColor(dividerColor);
            buttonBarDivider.setLayoutParams(
                    new LinearLayout.LayoutParams(dividerSize, ViewGroup.LayoutParams.MATCH_PARENT)
            );

            if(mParams != null) {
                if(mParams.getButtonCount() == 3)
                    layout.addView(buttonBarDivider, 4);
                else if(mParams.getButtonCount() == 2)
                    layout.addView(buttonBarDivider, 3);
            }

            Button negativeButton = layout.findViewById(android.R.id.button2);
            negativeButton.setBackgroundDrawable(
                    getContext().getResources().getDrawable(R.drawable.holo4ux_highlight)
            );

            Button neutralButton = layout.findViewById(android.R.id.button3);
            neutralButton.setBackgroundDrawable(
                    getContext().getResources().getDrawable(R.drawable.holo4ux_highlight)
            );

            Button positiveButton = layout.findViewById(android.R.id.button1);
            positiveButton.setBackgroundDrawable(
                    getContext().getResources().getDrawable(R.drawable.holo4ux_highlight)
            );
        }
    }
}
