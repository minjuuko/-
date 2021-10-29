package com.jnuhw.bcfirst.domain;

public class LcCounter {

    private int lc;

    public LcCounter() {
        this.lc = 0;
    }

    public void setLc(int lc) {
        this.lc = lc;
    }

    public int getCurrentLc() {
        return lc;
    }

    public void increaseLc() {
        lc++;
    }

    public void resetLc() {
        lc = 0;
    }
}
