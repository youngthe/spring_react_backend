package com.example.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private int num;

    private String id;

    private String pw;

    private String role;

    public int getNum() {
        return num;
    }
    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }


    public String getRole() {
        return role;
    }

}
