package com.jnuhw.bcfirst.background;

/*
    Register
    variables:
        data : 레지스터가 보유하고 있는 데이터
    
 */

public class Register {

    private int data;

    public Register() {
        clear();
    }

    public Register(byte data){
        this.data = data;
    }

    public void increase() {
        data += 1;
    }

    public void clear() {
        data = 0x0000;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
