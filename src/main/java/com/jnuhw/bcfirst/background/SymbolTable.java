package com.jnuhw.bcfirst.background;


/*
    SymbolTable
    Variables :
        symbol : A, B, C, 부분이다.
        locCount : 이 Symbol이 발견된 LC Data
        data : 이 심볼이 어떤 Data를 의미 하는지. ( DEC 83 에서 83 부분 )
 */

public class SymbolTable {

    private String symbol;
    private int locCount;
    private int data;

    public SymbolTable(String symbol, int lc, int data) {
        this.symbol = symbol;
        this.locCount = lc;
        this.data = data;
    }

    public int getLocCount() {
        return locCount;
    }

    public int getData() {
        return data;
    }

    public String getSymbol() {
        return symbol;
    }
}
