package com.seneca.cashapp.utils;

import com.seneca.cashapp.model.Item;

import java.util.ArrayList;

public class ModelUtils {
    public ArrayList<Item> createItemData(){
        ArrayList<Item> itemList = new ArrayList<Item>();

        Item pante = new Item("Pante", 10, 20.44);

        Item shoes = new Item("Shoes", 100, 10.44);

        Item hats = new Item("Hats", 30, 5.9);

        itemList.add(pante);
        itemList.add(shoes);
        itemList.add(hats);

        return itemList;
    }
}
