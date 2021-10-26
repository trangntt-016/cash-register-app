package com.seneca.cashapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.seneca.cashapp.R;
import com.seneca.cashapp.model.Item;

import java.util.List;

public class ItemListAdapter extends ArrayAdapter<Item> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;



    public ItemListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the item information
        String name = getItem(position).getName();
        double unitPrice = getItem(position).getUnitPrice();
        int quantity = getItem(position).getQuantity();

        //Create the item object with the information
        Item item = new Item(name,quantity,unitPrice);


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        tvQuantity.setText(String.valueOf(quantity));
        tvPrice.setText(String.valueOf(unitPrice));


        return convertView;
    }
}
