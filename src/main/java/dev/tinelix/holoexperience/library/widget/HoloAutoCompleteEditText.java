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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import dev.tinelix.holoexperience.library.R;

@SuppressLint("AppCompatCustomView")
public class HoloAutoCompleteEditText extends AutoCompleteTextView {

    private OnCreateContextMenuListener mClipboardMenuListener = null;

    public HoloAutoCompleteEditText(Context context) {
        super(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) return;

        createClipboardMenulistener();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
            setDropDownBackgroundDrawable(
                    getResources().getDrawable(R.drawable.holo4ux_dark_dialog_full)
            );
    }

    public HoloAutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) return;

        createClipboardMenulistener();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR)
            setDropDownBackgroundDrawable(
                    getResources().getDrawable(R.drawable.holo4ux_dark_dialog_full)
            );
    }

    private void createClipboardMenulistener() {
        mClipboardMenuListener = new OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(
                    ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo
            ) {
                final List<MenuItem> items = new ArrayList<>();

                for(int i = 0; i < menu.size(); i++) {
                    items.add(menu.getItem(i));
                }

                menu.clear();

                if(items.isEmpty()) return;

                ArrayAdapter<MenuItem> adapter = new ArrayAdapter<>(
                        getContext(), R.layout.holo4ux_simple_list_item_native, items
                );

                final EditText editText = (EditText) view;

                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle(getResources().getString(R.string.holo4ux_clipboard))
                        .setIcon(null)
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editText.onTextContextMenuItem(items.get(which).getItemId());
                            }
                        }).create();

                dialog.show();
                dialog.getListView().setDivider(getResources().getDrawable(R.color.holo_gray_twilight));
            }
        };

        setOnCreateContextMenuListener(mClipboardMenuListener);
    }
}
