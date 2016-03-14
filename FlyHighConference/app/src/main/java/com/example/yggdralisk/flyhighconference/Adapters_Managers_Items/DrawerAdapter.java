package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 25.02.16.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
    private Context context;
    private List<DrawerItem> items;
    private int textViewResourceId = -1;

    public DrawerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DrawerAdapter(Context context, int textViewResourceId, List<DrawerItem> items) {
        super(context, textViewResourceId, items);

        this.context = context;
        this.items = items;
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if (textViewResourceId != -1)
            rowView = inflater.inflate(textViewResourceId, parent, false);
        else
            rowView = inflater.inflate(R.layout.drawer_item, parent, false);
        TextView textView = ButterKnife.findById(rowView,R.id.drawer_item_text);
        ImageView imageView = ButterKnife.findById(rowView,R.id.drawer_item_image);

        textView.setText(items.get(position).text);
        imageView.setImageResource(items.get(position).iconId);

        return rowView;
    }

    public DrawerItem getItemAt(int position)
    {
        return items.get(position);
    }
}
