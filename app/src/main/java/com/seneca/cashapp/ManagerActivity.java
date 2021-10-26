package com.seneca.cashapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.seneca.cashapp.model.PurchasedItem;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {
    ArrayList<PurchasedItem> purchasedList = new ArrayList<PurchasedItem>();
    public ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_panel);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide status

         purchasedList = this.getIntent().getExtras().getParcelableArrayList("purchasedItemList");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void navigateToHistoryList(View v) {
        purchasedList.forEach(item -> {
            item.getPurchasedOn();
        });
        Intent myIntent = new Intent(this, HistoryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("purchasedItemList", purchasedList);
        myIntent.putExtras(bundle);

        startActivity(myIntent);


    }
}