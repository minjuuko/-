package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.view.OutputView;

public class BitData {

    private final int bitSize;
    private final boolean isSigned;

    // 데이터는 2진수로 관리되고, 계산되어야 함;
    private int data = 0;

    public BitData(int bitSize) {
        this.bitSize = bitSize;
        isSigned = false;
    }

    @Deprecated
    public BitData(int bitSize, boolean isSigned) {
        this.bitSize = bitSize;
        this.isSigned = isSigned;
    }

    public void setData(int data) {
        if (checkOverflow(data)) {
            OutputView.printDataOverflowError(data);
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void increase() {
        if (checkOverflow(data)) {
            OutputView.printDataOverflowError(data);
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkOverflow(int data) {
        int bitRange = isSigned ? bitSize-1 : bitSize;
//        int minRange = isSigned ? -(int) Math.pow(2, bitRange) : 0;
//        int maxRange = (int) Math.pow(2, bitRange) - 1;

        return Math.abs(data) > (Math.pow(2, bitRange)-1);
    }
}
