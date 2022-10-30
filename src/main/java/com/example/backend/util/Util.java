package com.example.backend.util;

import com.example.backend.entity.Shop;

import java.util.List;

public class Util {
    public Integer TotalPrice(List<Shop> list){
        int TotalPrice = 0;

        for(int i=0;i<list.size();i++){
            TotalPrice += list.get(i).getPrice();
        }
        System.out.println(TotalPrice);
        return TotalPrice;
    }
}
