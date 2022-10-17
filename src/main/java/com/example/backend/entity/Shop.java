package com.example.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Shop {

    @Id
    private int id;

    private String name;

    private int price;

    private String sortation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSortation() {
        return sortation;
    }

    public void setSortation(String sortation) {
        this.sortation = sortation;
    }
}
