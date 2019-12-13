package com.example.mylocation;

import java.io.Serializable;

public class Company implements Serializable {
    private String company;

    public Company(String company){
        this.company = company;
    }

    public String getCompany(){
        return company;
    }

    @Override
    public String toString(){
        return company;
    }
}
