package com.seneca.cashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.seneca.cashapp.model.PurchasedItem;

public class SalesDetailActivity extends AppCompatActivity {
    PurchasedItem purchasedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_purchased_item_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle data = getIntent().getExtras();
        purchasedItem = (PurchasedItem) data.getParcelable("purchasedItemDetail");
        TextView itemName = (TextView) findViewById(R.id.textView4);
        TextView itemPrice = (TextView) findViewById(R.id.textView5);
        TextView itemDate= (TextView) findViewById(R.id.textView6);
        itemName.setText("Product: " + purchasedItem.getName());
        itemPrice.setText("Price: $" + String.format("%.2f", purchasedItem.getTotal()));
        itemDate.setText(purchasedItem.getPurchasedOn());

    }
}