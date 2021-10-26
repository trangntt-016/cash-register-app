package com.seneca.cashapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PurchasedItem implements Parcelable {
    private String name;
    private double total;
    private int quantity;
   public String purchasedOn;

    public PurchasedItem(String name, int quantity, double total, String purchasedOn) {
        this.name = name;
        this.quantity = quantity;
        this.total = total;
        this.purchasedOn = purchasedOn;
    }


    protected PurchasedItem(Parcel in) {
        name = in.readString();
        total = in.readDouble();
        quantity = in.readInt();
        purchasedOn = in.readString();
    }

    public static final Creator<PurchasedItem> CREATOR = new Creator<PurchasedItem>() {
        @Override
        public PurchasedItem createFromParcel(Parcel in) {
            return new PurchasedItem(in);
        }

        @Override
        public PurchasedItem[] newArray(int size) {
            return new PurchasedItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(total);
        parcel.writeInt(quantity);
        parcel.writeString(purchasedOn);
    }

    public String getName() { return this.name;};
    public int getQuantity() { return this.quantity;};
    public double getTotal() { return this.total;};
    public String getPurchasedOn() { return "Purchased day: "+ this.purchasedOn;};
}
