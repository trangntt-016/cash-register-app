package com.seneca.cashapp;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.seneca.cashapp.adapter.ItemListAdapter;
import com.seneca.cashapp.model.Item;
import com.seneca.cashapp.model.PurchasedItem;
import com.seneca.cashapp.utils.DataUtils;
import com.seneca.cashapp.utils.ModelUtils;
import com.google.android.material.snackbar.Snackbar;
import com.seneca.cashapp.ManagerActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<PurchasedItem> purchasedItemList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();

    private boolean isReset = true;
    private ModelUtils utils = new ModelUtils();

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            getDataFromCurrentState(savedInstanceState);
        }else{
            itemList = utils.createItemData();
        }
        setDataToListView();

        getSupportActionBar().hide();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        TextView pdName = (TextView) findViewById(R.id.productType);
        TextView qty = (TextView) findViewById(R.id.quantity);
        TextView totalP = (TextView) findViewById(R.id.totalPrice);

        outState.putString("productName", pdName.getText().toString());
        outState.putString("quantity", qty.getText().toString());
        outState.putString("totalPrice", totalP.getText().toString());

        outState.putParcelableArrayList("itemList", itemList);
        outState.putParcelableArrayList("purchasedItemList", purchasedItemList);

        super.onSaveInstanceState(outState);
    }

    public void setDataToListView() {
        ListView mListView = (ListView) findViewById(R.id.theList);
        // assign item list to the adapter list
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.layout_product_list, itemList);
        mListView.setAdapter(adapter);
    }

    public void navigateToManagerOnClick(View v) {
        Intent myIntent = new Intent(this, ManagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("purchasedItemList", purchasedItemList);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }


    // this function is to avoid losing data when the screen rotates
    public void getDataFromCurrentState(Bundle savedInstanceState){
        TextView productName = (TextView) findViewById(R.id.productType);
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        productName.setText(savedInstanceState.getString("productName"));
        quantity.setText(savedInstanceState.getString("quantity"));
        totalPrice.setText(savedInstanceState.getString("totalPrice"));

        purchasedItemList = savedInstanceState.getParcelableArrayList("soldItemList");
        itemList = savedInstanceState.getParcelableArrayList("itemList");
    }

    public void numberOnClick(View v) {
        if(isReset){
            clearTextView();
            isReset = false;
        }
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        TextView resultView = (TextView) findViewById(R.id.quantity);
        resultView.append(buttonText);
        setPriceToTextView();
    }

    public void renderProductTypeOnClick(View v) {
        TextView itemName = (TextView) v.findViewById(R.id.textView1);
        String buttonText = itemName.getText().toString();
        TextView resultView = (TextView) findViewById(R.id.productType);
        resultView.setText(buttonText);
        setPriceToTextView();
    }


    public void deleteOnClick(View v) {
        TextView resultView = (TextView) findViewById(R.id.quantity);

        String str = resultView.getText().toString();
        if(str.equals("Quantity")){
            clearTextView();
            resultView.setText("0");
        }else{
            if(str.length() > 0 && !str.equals("0")){
                resultView.setText(DataUtils.extractChars(str));
                if(str.length()==1){
                    resultView.setText("0");
                    isReset=true;
                }
            }
        }
        setPriceToTextView();
    }

    public void buyOnClick(View v){
        boolean flagSnackBar = true;
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView productType = (TextView) findViewById(R.id.productType);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        Item product=DataUtils.findItem(itemList, productType.getText().toString());
        String snackbarText="";

        if(quantity.getText().toString().equals("Quantity") || productType.getText().toString().equals("productType") || totalPrice.getText().toString().equals("Total") || quantity.getText().toString().equals("0") || product == null){
            snackbarText="All field are required!!!";
            flagSnackBar=false;
        }
        else{
            int number = Integer.parseInt(quantity.getText().toString());
            if(Integer.parseInt(quantity.getText().toString()) > product.getQuantity()){
                snackbarText="No Enough quantity in the stock!!!";
                flagSnackBar=false;
            }
        }

        if(!flagSnackBar){
            View contextView = (View)findViewById(R.id.snackBar);
            contextView.getLayoutParams().height=10;

            Snackbar mSnackbar = Snackbar.make(contextView, snackbarText, Snackbar.LENGTH_LONG);
            View mView = mSnackbar.getView();

            TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackbar.setBackgroundTint(getColor(R.color.backgroundSnackBar)).setTextColor(getColor(R.color.black));

            mSnackbar.show();
        }else{
            product.sellAmount(Integer.parseInt(quantity.getText().toString()));
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Thank You for your purchase").setMessage("Your purchase is "+quantity.getText().toString()+" "+productType.getText().toString()+" for "+totalPrice.getText().toString());
            builder.show();
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = localDateTime.format(formatter);
            PurchasedItem purchasedItem = new PurchasedItem(productType.getText().toString(), parseInt(quantity.getText().toString()), parseDouble(totalPrice.getText().toString()), formatDateTime);
            purchasedItemList.add(purchasedItem);
            setDataToListView();
        }
    }



    public void clearTextView() {
        TextView resultView = (TextView) findViewById(R.id.quantity);
        resultView.setText("");
    }

    public void clearInput(View v){
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("Quantity");
        TextView productType = (TextView) findViewById(R.id.productType);
        productType.setText("Product Type");
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        totalPrice.setText("Total");
        isReset=true;
    }


    public void setPriceToTextView(){
        TextView productName = (TextView) findViewById(R.id.productType);
        Item product = DataUtils.findItem(itemList, productName.getText().toString());

        TextView quantity = (TextView) findViewById(R.id.quantity);

        if(product!=null && DataUtils.isNumberic(quantity.getText().toString())){
            TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
            double price= Integer.parseInt(quantity.getText().toString())*product.getUnitPrice();
            String priceString=String.format("%.2f",price);
            totalPrice.setText(priceString);
        }
    }


}