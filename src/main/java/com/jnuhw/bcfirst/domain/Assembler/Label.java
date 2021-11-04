package com.jnuhw.bcfirst.domain.Assembler;


public class Label {

    private String name;
   
    private int lc;
    private int data;

    public Label(String name, int lc, int data) {
        this.name = name;
        this.lc = lc;
        this.data = data;
    }

    public int getLc() {
        return lc;
    }

    public int getData() {
        return data;
    }

    public String getName() {
        return name;
    }
}
