package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.view.OutputView;

/*
 * 메모리, 레지스터, 플립플롭의 비트데이터(워드)를 정의한 클래스
 * 값을 조작하거나, 오버플로우를 감지함
 */

public class BitData {

    private final int bitSize;

    private boolean isSigned;
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
        if (checkOverflow(data + 1)) {
            OutputView.printDataOverflowError(data+1);
            return;
        }

        data++;
    }

    public void clear() {
        data = 0;
    }

    private boolean checkOverflow(int value) {
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
}
