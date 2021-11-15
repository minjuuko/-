package com.jnuhw.bcfirst.domain.assembler;

/*
 * 슈도/비슈도 명령어에 정의되는 Label을 정의하는 클래스
 */

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
