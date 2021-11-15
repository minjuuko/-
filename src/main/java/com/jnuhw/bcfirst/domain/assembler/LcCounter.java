package com.jnuhw.bcfirst.domain.assembler;

public class LcCounter {

    public static final int DEFAULT_LC = 0;

    private int lc;

    public LcCounter() {
        this.lc = DEFAULT_LC;
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
        lc = DEFAULT_LC;
    }
}
