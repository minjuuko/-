package com.jnuhw.bcfirst.domain.Assembler;

import com.jnuhw.bcfirst.view.OutputView;

public class BitData {

    private final int bitSize;

    private boolean isSigned;
   
    private int data = 0;

    public BitData(int bitSize) {
        this.bitSize = bitSize;
        isSigned = false;
    }

    public BitData(int bitSize, boolean isSigned) {
        this.bitSize = bitSize;
        this.isSigned = isSigned;
    }

    public void setIsSigned(boolean isSigned) {
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
        if (isSigned && !checkSignedOverflow(value)) {
            return false;
        }

        if (!isSigned && !checkUnsignedOverflow(value)) {
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
            return true;
        }

        return false;
    }

    private boolean checkUnsignedOverflow(int value) {
        int bitRange = bitSize;
        int maxRange = (int) Math.pow(2, bitRange) - 1;

        if (value < 0 || maxRange < value) {
            OutputView.printDataOverflowError(value);
            return true;
        }

        return false;
    }
}
