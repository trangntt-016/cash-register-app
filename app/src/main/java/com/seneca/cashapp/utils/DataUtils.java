package com.seneca.cashapp.utils;

import com.seneca.cashapp.model.Item;

import java.util.ArrayList;

public class DataUtils {
    public static boolean isNumberic(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static Item findItem(ArrayList<Item> itemList, String name){
        for(Item item:itemList){
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;

    }

    public static String extractChars(String str) {
        return str.substring(0, str.length() - 1);
    }
}
