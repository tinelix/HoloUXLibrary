package dev.tinelix.holoexperience.library.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import dev.tinelix.holoexperience.library.R;

public class MenuItemAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<String> mItems;

    public MenuItemAdapter(Context context, ArrayList<String> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder")
        View view = View.inflate(mContext, R.layout.holo4ux_simple_list_item, null);

        ((TextView) view.findViewById(android.R.id.text1)).setText((String) getItem(position));

        return view;
    }
}
