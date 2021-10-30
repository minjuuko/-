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
        if (isSigned && checkSignedOverflow(data)) {
            OutputView.printDataOverflowError(data);
            return;
        }
        if (!isSigned && checkUnsignedOverflow(data)) {
            OutputView.printDataOverflowError(data);
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void increase() {
        if (isSigned && checkSignedOverflow(data + 1)) {
            OutputView.printDataOverflowError(data + 1);
            return;
        }
        if (!isSigned && checkUnsignedOverflow(data + 1)) {
            OutputView.printDataOverflowError(data + 1);
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkSignedOverflow(int newData) {
        int bitRange = bitSize - 1;
        int minRange = -(int) Math.pow(2, bitRange);
        int maxRange = (int) Math.pow(2, bitRange) - 1;

        if (newData < minRange || maxRange < newData) {
            OutputView.printDataOverflowError(newData);
            return false;
        }

        return true;
    }

    private boolean checkUnsignedOverflow(int newData) {
        int bitRange = bitSize;
        int maxRange = (int) Math.pow(2, bitRange) - 1;

        if (newData < 0 || maxRange < newData) {
            OutputView.printDataOverflowError(newData);
            return false;
        }

        return true;
    }

//    private boolean checkOverflow(int data) {
//        int bitRange = isSigned ? bitSize-1 : bitSize;
////        int minRange = isSigned ? -(int) Math.pow(2, bitRange) : 0;
////        int maxRange = (int) Math.pow(2, bitRange) - 1;
//
//        return Math.abs(data) > (Math.pow(2, bitRange)-1);
//    }
}
