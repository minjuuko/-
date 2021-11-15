package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.view.OutputView;

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
            return;
        }

        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void increase() {
        if (checkOverflowHex(data + 1)) {
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkOverflowHex(int value) {
        String hex = Integer.toHexString(value);
        if(hex.length() > 4) {
            hex = hex.substring(hex.length()-4);
        }
        int decimal = Integer.parseInt(hex, 16);
        return decimal > 0xFFFF;
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
