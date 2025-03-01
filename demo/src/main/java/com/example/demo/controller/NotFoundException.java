package com.example.demo.controller;

public class NotFoundException extends RuntimeException{

    private int id;


    public NotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

