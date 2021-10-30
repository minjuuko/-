package com.jnuhw.bcfirst.background;

@Deprecated
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
