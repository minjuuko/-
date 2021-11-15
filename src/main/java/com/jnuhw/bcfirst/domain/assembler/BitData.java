package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.view.OutputView;

import java.util.Locale;

public class BitData {

    private final int bitSize;

    private boolean isSigned;
    // 데이터는 2진수로 관리되고, 계산되어야 하지만, 실제 저장값은 10진수
    private int data = 0;

    public BitData(int bitSize) {
        this.bitSize = bitSize;
        isSigned = true;
    }

    public BitData(int bitSize, boolean isSigned) {
        this.bitSize = bitSize;
        this.isSigned = isSigned;
    }

    public void setIsSigned(boolean isSigned) {
        this.isSigned = isSigned;
    }

    public void setData(int data) {
        if (checkOverflowHex(data)) {
            OutputView.printDataOverflowError(data);
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void increase() {
        if (checkOverflowHex(data + 1)) {
            OutputView.printDataOverflowError(data+1);
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkOverflowHex(int value) {
        int data = value;
        int size = isSigned ? bitSize-1 : bitSize;
        int maxValue = (int) Math.pow(2, size) - 1;

        int negCheck = maxValue / 2;
        int posCheck = negCheck + 1;

        if(isSigned) { // isSigned = true의 경우, 15비트만 검사하기 위해 [0] bit를 임의적으로 1로 세팅
            if(data < 0) data = Math.abs(data - negCheck);
            else data += posCheck;
        }

        if(isSigned)
            return data > maxValue;
        else
            return data > maxValue && value < 0;
    }

//    private boolean checkOverflow(int value) {
//        if (isSigned && !checkSignedOverflow(value)) {
//            return false;
//        }
//
//        if (!isSigned && !checkUnsignedOverflow(value)) {
//            return false;
//        }
//
//        return true;
//    }
//
//    private boolean checkSignedOverflow(int value) {
//        int bitRange = bitSize - 1;
//        int minRange = -(int) Math.pow(2, bitRange);
//        int maxRange = (int) Math.pow(2, bitRange) - 1;
//
//        if (value < minRange || maxRange < value) {
//            OutputView.printDataOverflowError(value);
//            return true;
//        }
//
//        return false;
//    }
//
//    private boolean checkUnsignedOverflow(int value) {
//        int bitRange = bitSize;
//        int maxRange = (int) Math.pow(2, bitRange) - 1;
//
//        if (value < 0 || maxRange < value) {
//            OutputView.printDataOverflowError(value);
//            return true;
//        }
//
//        return false;
//    }
}
