package com.example.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private int num;

    private String id;

    private String pw;

}
