package com.jnuhw.bcfirst.domain.assembler;


public class Label {

    private String name;
    // 선언 명령어의 LC
    private int lc;
    private int data;

    public Label(String name, int lc) { // For not (hex, dec) variables
        this.name = name;
        this.lc = lc;
    }

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
