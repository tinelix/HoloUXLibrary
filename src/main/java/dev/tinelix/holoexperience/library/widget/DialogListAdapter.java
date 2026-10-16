package dev.tinelix.holoexperience.library.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

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

        return new DialogListAdapter.Holder(
                View.inflate(mDialog.getContext(), layoutResId, null)
        );
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

            TypedValue value = new TypedValue();
            float dp = mDialog.getContext().getResources().getDisplayMetrics().scaledDensity;

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

            itemView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int)(40 * dp)
            ));

            itemTitleTv.setCheckMarkDrawable(R.drawable.holo4ux_dark_background_radiobtn);

            itemTitleTv.setChecked(mCheckedItemPosition == position);

            itemView.setOnClickListener(new View.OnClickListener() {
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
