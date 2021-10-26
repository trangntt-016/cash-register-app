package com.seneca.cashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.seneca.cashapp.adapter.ItemRecyclerAdapter;
import com.seneca.cashapp.model.PurchasedItem;
import com.seneca.cashapp.SalesDetailActivity;

import java.util.ArrayList;

public class HistoryListActivity extends AppCompatActivity {
    ArrayList<PurchasedItem> purchasedItemList;
    RecyclerView recyclerViewList;
    ItemRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_history_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerList);
        purchasedItemList = this.getIntent().getExtras().getParcelableArrayList("purchasedItemList");

        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemRecyclerAdapter(purchasedItemList, this, new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(PurchasedItem item) {
                navigateToSaleDetailsOnClick(item);
            }
        });
        recyclerViewList.setAdapter(adapter);
    }

    public void navigateToSaleDetailsOnClick(PurchasedItem item) {
        Intent myIntent = new Intent(this, SalesDetailActivity.class);
        myIntent.putExtra("purchasedItemDetail", item);
        startActivity(myIntent);
    }
}