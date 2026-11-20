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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.tinelix.holoexperience.library.R;
import dev.tinelix.holoexperience.library.widget.DialogListAdapter;

public class HoloAlertDialog extends AlertDialog {

    private View mDialogView;
    DialogParams mParams;

    protected HoloAlertDialog(@NonNull Context context) {
        super(context);
        mDialogView = View.inflate(getContext(), R.layout.holo4ux_alert_dialog, null);
    }

    protected HoloAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mDialogView = View.inflate(getContext(), R.layout.holo4ux_alert_dialog, null);
    }

    protected HoloAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mDialogView = View.inflate(getContext(), R.layout.holo4ux_alert_dialog, null);
    }

    public HoloAlertDialog(Context context, DialogParams params) {
        super(context);
        mParams = params;

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialogView = View.inflate(getContext(), R.layout.holo4ux_alert_dialog, null);
            mDialogView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );
            return;
        }

        setTitle(mParams.mTitle);
        if(mParams.listItems != null)
            setItems(mParams.listItems, mParams.defListItemPos, false);
        else if(mParams.mMsgText != null)
            setMessage(mParams.mMsgText);
        else
            setView(mParams.mView);

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

    public void setItems(final String[] listItems, int defPosition, boolean multiChoice) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            final ListView listView = new ListView(getContext());
            listView.setAdapter(
                    new ArrayAdapter<>(
                            getContext(),
                            multiChoice ?
                                    android.R.layout.simple_list_item_multiple_choice :
                                    android.R.layout.simple_list_item_single_choice,
                            listItems
                    )
            );

            listView.setChoiceMode(
                    multiChoice ?
                            ListView.CHOICE_MODE_MULTIPLE :
                            ListView.CHOICE_MODE_SINGLE
            );

            listView.setItemChecked(defPosition, true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listView.setItemChecked(position, true);
                    mParams.listListener.onClick(HoloAlertDialog.this, position);
                }
            });
            setView(listView);

        } else {
            RecyclerView view = new RecyclerView(getContext());
            view.setLayoutManager(new LinearLayoutManager(getContext()));
            view.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            view.setAdapter(
                new DialogListAdapter(
                    this, listItems, defPosition,
                    multiChoice ? 2 : 1, mParams.listListener
                )
            );
            setView(view);
        }
    }

    private void setButton(final int buttonId, String title, final OnClickListener listener) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setButton(buttonId, title, listener);
            return;
        }

        Button button = null;
        switch (buttonId) {
            case DialogInterface.BUTTON_NEGATIVE:
                button = mDialogView.findViewById(android.R.id.button2);
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                button = mDialogView.findViewById(android.R.id.button3);
                break;
            case DialogInterface.BUTTON_POSITIVE:
                button = mDialogView.findViewById(android.R.id.button1);
                break;
        }

        if(button != null) {
            button.setTextColor(getContext().getResources().getColor(android.R.color.white));
            button.setText(title);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onClick(HoloAlertDialog.this, buttonId);
                    } else {
                        dismiss();
                    }
                }
            });
        }
    }

    public void setView(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setView(view);
            return;
        }

        if(view != null) {
            mDialogView.findViewById(R.id.contentPanel).setVisibility(View.GONE);
            FrameLayout layout = mDialogView.findViewById(R.id.custom);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            view.setLayoutParams(params);
            layout.addView(view);
        }
    }

    @Override
    public void setIcon(int resId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setIcon(resId);
            return;
        }

        Drawable icon = getContext().getResources().getDrawable(resId);

        ImageView iconView = mDialogView.findViewById(android.R.id.icon);

        if(icon != null) {
            iconView.setImageDrawable(icon);
        }

        iconView.setVisibility(icon != null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setIcon(Drawable icon) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if(icon != null)
                super.setIcon(icon);

            if(getWindow() != null)
                getWindow().getDecorView().findViewById(android.R.id.icon)
                           .setVisibility(icon != null ? View.VISIBLE : View.GONE);
            return;
        }

        ImageView iconView = mDialogView.findViewById(android.R.id.icon);

        if(icon != null)
            iconView.setImageDrawable(icon);

        iconView.setVisibility(icon != null ? View.VISIBLE : View.GONE);
    }

    public static class DialogParams {
        protected int mThemeRes;
        protected String mTitle;
        protected String mMsgText;
        protected View mView;
        protected DialogButton mNegativeButton;
        protected DialogButton mNeutralButton;
        protected DialogButton mPositiveButton;
        private int mButtonCount;

        public OnClickListener listListener;
        public String[] listItems;
        public int defListItemPos;


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
    public static class Builder {

        private Context mContext;
        private DialogParams mParams;

        public Builder(@NonNull Context context) {
            mContext = context;
            mParams = new DialogParams();
        }

        public Builder(@NonNull Context context, int themeResId) {
            mContext = context;
            mParams = new DialogParams();
            mParams.mThemeRes = themeResId;
        }

        public HoloAlertDialog.Builder setTitle(CharSequence title) {
            mParams.mTitle = title.toString();
            return this;
        }

        public HoloAlertDialog.Builder setTitle(int titleId) {
            mParams.mTitle = mContext.getResources().getString(titleId);
            return this;
        }

        public HoloAlertDialog.Builder setView(int layoutResId) {
            mParams.mView = View.inflate(mContext, layoutResId, null);
            return this;
        }

        public HoloAlertDialog.Builder setView(View view) {
            mParams.mView = view;
            return this;
        }

        public HoloAlertDialog.Builder setNegativeButton(int textId, OnClickListener listener) {
            mParams.mNegativeButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        public HoloAlertDialog.Builder setNeutralButton(int textId, OnClickListener listener) {
            mParams.mNeutralButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        public HoloAlertDialog.Builder setPositiveButton(int textId, OnClickListener listener) {
            mParams.mPositiveButton =
                    new DialogButton(mContext.getResources().getString(textId), listener);
            return this;
        }

        public HoloAlertDialog.Builder setSingleChoiceItems(
                int arrayResId, int defValuePos, OnClickListener listener
        ) {
            mParams.listItems = mContext.getResources().getStringArray(arrayResId);
            mParams.defListItemPos = defValuePos;
            mParams.listListener = listener;
            return this;
        }

        public HoloAlertDialog create() {
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

        public HoloAlertDialog show() {
            HoloAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public HoloAlertDialog.Builder setMessageText(int stringId) {
            mParams.mMsgText = mContext.getResources().getString(stringId);
            return this;
        }

        public HoloAlertDialog.Builder setMessageText(String str) {
            mParams.mMsgText = str;
            return this;
        }
    }

    @Override
    public void setTitle(int titleId) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setTitle(titleId);
            return;
        }

        ((TextView) findViewById(R.id.alertTitle)).setText(
                getContext().getResources().getString(titleId)
        );
        findViewById(R.id.titleDivider).setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setTitle(title);
            return;
        }

        ((TextView) mDialogView.findViewById(R.id.alertTitle)).setText(title);

        if(title != null)
            mDialogView.findViewById(R.id.titleDivider).setVisibility(View.VISIBLE);
    }

    @Override
    public void setMessage(CharSequence message) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            super.setMessage(message);
            return;
        }

        mParams.mMsgText = (String) message;

        TextView msgText = mDialogView.findViewById(R.id.message);

        msgText.setVisibility(View.VISIBLE);
        msgText.setText(message);
    }

    @Override
    public void show() {
        if(!isShowing())
            super.show();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setIcon(null);
            return;
        }

        if(mParams == null)
            return;

        float dp = getContext().getResources().getDisplayMetrics().scaledDensity;

        if(getWindow() != null) {

            Button negativeButton = mDialogView.findViewById(android.R.id.button2);
            Button neutralButton = mDialogView.findViewById(android.R.id.button3);
            Button positiveButton = mDialogView.findViewById(android.R.id.button1);

            getWindow().setBackgroundDrawable(
                    getContext().getResources().getDrawable(android.R.color.transparent)
            );


            getWindow().getDecorView().setPadding((int)(12 * dp), 0, (int)(12 * dp), 0);

            if(mParams.mTitle != null)
                setTitle(mParams.mTitle);
            else
                mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);

            if(mParams.mMsgText != null)
                setMessage(mParams.mMsgText);

            if(mParams.getButtonCount() > 0) {
                if (mParams.mNegativeButton != null)
                    setButton(BUTTON_NEGATIVE, mParams.mNegativeButton.mTitle, mParams.mNegativeButton.mListener);
                else
                    negativeButton.setVisibility(View.GONE);

                if (mParams.mNeutralButton != null)
                    setButton(BUTTON_NEUTRAL, mParams.mNeutralButton.mTitle, mParams.mNeutralButton.mListener);
                else {
                    neutralButton.setVisibility(View.GONE);
                    mDialogView.findViewById(R.id.bottomDivider2).setVisibility(View.GONE);
                }

                if (mParams.mPositiveButton != null)
                    setButton(BUTTON_POSITIVE, mParams.mPositiveButton.mTitle, mParams.mPositiveButton.mListener);
                else {
                    positiveButton.setVisibility(View.GONE);
                    mDialogView.findViewById(R.id.bottomDivider1).setVisibility(View.GONE);
                }
            } else {
                mDialogView.findViewById(R.id.buttonPanel).setVisibility(View.GONE);
                getWindow().getDecorView().setLayoutParams(
                        new WindowManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                );
            }

            if(mParams.mView != null)
                setView(mParams.mView);
            else if (mParams.listItems != null)
                setItems(mParams.listItems, mParams.defListItemPos, false);

            setIcon(null);

            setContentView(mDialogView);

            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
            );

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
            );

            negativeButton.setTextColor(
                    getContext().getResources().getColorStateList(R.color.holo4ux_dark_text_primary)
            );

            neutralButton.setTextColor(
                    getContext().getResources().getColorStateList(R.color.holo4ux_dark_text_primary)
            );

            positiveButton.setTextColor(
                    getContext().getResources().getColorStateList(R.color.holo4ux_dark_text_primary)
            );
        }
    }

    public View getDialogView() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            return null;

        return mDialogView;
    }
}
