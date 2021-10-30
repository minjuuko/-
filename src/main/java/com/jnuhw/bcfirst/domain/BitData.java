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

    public BitData(int bitSize, boolean isSigned) {
        this.bitSize = bitSize;
        this.isSigned = isSigned;
    }

    public void setData(int data) {
        if (checkOverflow(data)) {
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void increase() {
        if (checkOverflow(data + 1)) {
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkOverflow(int value) {
        if (isSigned && checkSignedOverflow(data + 1)) {
            OutputView.printDataOverflowError(data + 1);
            return false;
        }
        if (!isSigned && checkUnsignedOverflow(data + 1)) {
            OutputView.printDataOverflowError(data + 1);
            return false;
        }

        return true;
    }

    private boolean checkSignedOverflow(int value) {
        int bitRange = bitSize - 1;
        int minRange = -(int) Math.pow(2, bitRange);
        int maxRange = (int) Math.pow(2, bitRange) - 1;

        if (value < minRange || maxRange < value) {
            OutputView.printDataOverflowError(value);
            return false;
        }

        return true;
    }

    private boolean checkUnsignedOverflow(int value) {
        int bitRange = bitSize;
        int maxRange = (int) Math.pow(2, bitRange) - 1;

        if (value < 0 || maxRange < value) {
            OutputView.printDataOverflowError(value);
            return false;
        }

        return true;
    }
}
