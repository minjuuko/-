package com.jnuhw.bcfirst.domain.assembler;

/*
 * Pass에서 사용되는 Lc값을 조작 및 관리하는 클래스
 */

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
