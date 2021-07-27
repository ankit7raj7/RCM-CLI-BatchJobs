package com.techprimers.springbatchexample1.model;

import java.math.BigDecimal;

public class Folders {
    String name;
    BigDecimal size;

    public Folders(String name, BigDecimal size) {
        this.name = name;
        this.size = size;
    }

    public Folders() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
