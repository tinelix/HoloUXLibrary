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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import dev.tinelix.holoexperience.library.R;

public class DialogListAdapter extends RecyclerView.Adapter {

    private final ArrayList<String> mItems;
    private final DialogInterface.OnClickListener mListener;
    private final int mItemType;
    private int mCheckedItemPosition = -1;
    private int mPreviousItemPosition = -1;
    private final AlertDialog mDialog;

    public DialogListAdapter(
            AlertDialog dialog, String[] items, int type,
            DialogInterface.OnClickListener listener
    ) {
        mDialog = dialog;
        mItems = new ArrayList<>();
        mItems.addAll(Arrays.asList(items));
        mItemType = type;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutResId;
        float dp = mDialog.getContext().getResources().getDisplayMetrics().scaledDensity;

        switch (viewType) {
            case 1:
                layoutResId = android.R.layout.select_dialog_singlechoice;
                break;
            case 2:
                layoutResId = android.R.layout.select_dialog_multichoice;
                break;
            default:
                layoutResId = android.R.layout.simple_list_item_1;
                break;
        }

        LinearLayout layout = new LinearLayout(mDialog.getContext());
        layout.addView(View.inflate(mDialog.getContext(), layoutResId, null));
        layout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int)(46 * dp)
                )
        );
        layout.setOrientation(LinearLayout.VERTICAL);

        View divider = new View(mDialog.getContext());
        divider.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int)(1 * dp)
                )
        );
        divider.setBackgroundColor(Color.parseColor("#444444"));

        divider.setId(R.id.divider);
        layout.addView(divider, 1);

        return new DialogListAdapter.Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DialogListAdapter.Holder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemType;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final CheckedTextView itemTitleTv;

        public Holder(View view) {
            super(view);
            itemTitleTv = view.findViewById(android.R.id.text1);
        }

        public void bind(final int position) {

            View divider = itemView.findViewById(R.id.divider);

            divider.setVisibility(
                    position == getItemCount() - 1 ?
                            View.GONE : View.VISIBLE
            );

            TypedValue value = new TypedValue();
            float dp = mDialog.getContext().getResources().getDisplayMetrics().scaledDensity;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            layoutParams.weight = 1;

            mDialog.getContext().getTheme().resolveAttribute(
                    android.R.attr.textColorPrimary, value, true
            );

            itemTitleTv.setBackgroundDrawable(
                    mDialog.getContext().getResources().getDrawable(R.drawable.holo4ux_highlight)
            );

            itemTitleTv.setPadding((int)(16 * dp), 0, (int)(12 * dp), 0);
            itemTitleTv.setText(mItems.get(position));

            if(value.resourceId != 0)
                itemTitleTv.setTextColor(
                        mDialog.getContext().getResources().getColor(value.resourceId)
                );

            itemTitleTv.setTextSize(16.0f);
            itemTitleTv.setLayoutParams(layoutParams);
            itemTitleTv.setCheckMarkDrawable(R.drawable.holo4ux_dark_background_radiobtn);
            itemTitleTv.setChecked(mCheckedItemPosition == position);

            itemTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPreviousItemPosition = mCheckedItemPosition;
                    mCheckedItemPosition = position;

                    itemTitleTv.setChecked(true);
                    notifyItemChanged(mPreviousItemPosition);
                    mListener.onClick(mDialog, position);
                }
            });
        }
    }
}
