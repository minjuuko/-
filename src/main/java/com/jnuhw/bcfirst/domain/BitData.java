package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.view.OutputView;

public class BitData {

    private final int bitSize;
    private final boolean isSigned;

    // 데이터는 2진수로 관리되고, 계산되어야 함;
    private int data = 0;

    public BitData(int bitSize, boolean isSigned) {
        this.bitSize = bitSize;
        this.isSigned = isSigned;
    }

    public void setData(int data) {
        int bitRange = bitSize-1;
        if (isSigned) {
            bitRange--;
        }

        int minRange = -(int) Math.pow(2, bitRange);
        int maxRange = (int) Math.pow(2, bitRange) - 1;
        if (data < minRange || maxRange < data) {
            OutputView.printDataOverflowError(data);
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }
}
