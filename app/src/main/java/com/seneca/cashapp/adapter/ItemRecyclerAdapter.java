package com.seneca.cashapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seneca.cashapp.R;
import com.seneca.cashapp.model.PurchasedItem;

import java.util.ArrayList;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {
    ArrayList<PurchasedItem> purchasedItemList;
    Context mContext;

    public interface OnItemClickListener {
        void onItemClicked(PurchasedItem item);
    }

    private final OnItemClickListener listener;

    public ItemRecyclerAdapter(ArrayList<PurchasedItem> purchasedItemList, Context context, OnItemClickListener listener) {
        this.purchasedItemList = purchasedItemList;
        this.mContext = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView itemName;
        private TextView itemPrice;
        private  TextView itemQuantity;

        public ViewHolder(View view) {
            super(view);
            itemName =(TextView) view.findViewById(R.id.textView4);
            itemQuantity =(TextView) view.findViewById(R.id.textView5);
            itemPrice =(TextView) view.findViewById(R.id.textView6);
        }

        public TextView getItemName() {
            return itemName;
        }
        public TextView getItemPrice() {
            return itemPrice;
        }
        public TextView getItemQuantity() {
            return itemQuantity;
        }
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_purchased_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ViewHolder holder,int position) {
        holder.getItemName().setText(purchasedItemList.get(position).getName());
        holder.getItemPrice().setText(String.format("%.2f", purchasedItemList.get(position).getTotal()));
        holder.getItemQuantity().setText(String.valueOf(purchasedItemList.get(position).getQuantity()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(purchasedItemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchasedItemList.size();
    }
}
