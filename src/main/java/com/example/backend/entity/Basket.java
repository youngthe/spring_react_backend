package com.example.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Basket {

    @Id
    private int num;

    private String userid;
    private int item_id;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUser() {
        return userid;
    }

    public void setUser(String user) {
        this.userid = user;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
