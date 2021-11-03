package com.jnuhw.bcfirst.domain.Assembler;

import java.util.ArrayList;
import java.util.List;

public class Memory {

    public static int MEMORY_SIZE = 4096;

    // 4096 개의 16비트 숫자 데이터들
    // private List<BitData> memory = new ArrayList<>(Collections.nCopies(4096, new BitData(16, true)));

    private List<BitData> memory;

    public Memory() {
        memory = new ArrayList<>();
        for(int i = 0 ; i < 4096 ; i++) {
            memory.add(new BitData(16));
        }
    }

    /**
     * 처음 저장할 땐, numeral data(signed data)인지 non-numeral data(unsigned data)(instruction code/address)인지를 설정하며, 값을 저장
     * @param address 저장될 메모리 주소
     * @param isNumeralData 저장할 데이터가 숫자 데이터인지 아닌지
     * @param data 저장할 데아터
     */
    public void initializeMemoryData(int address, boolean isNumeralData, int data) {
        memory.get(address).setIsSigned(isNumeralData);
        memory.get(address).setData(data);
    }

    /**
     * 처음 저장 이후에 값만 바꾸기 위한 함수
     * @param address
     * @param data
     */
    public void setMemoryData(int address, int data) {
        memory.get(address).setData(data);
    }

    public int getMemoryData(int address) {
        return memory.get(address).getData();
    }
}
