package com.jnuhw.bcfirst.domain.Assembler;

import java.util.ArrayList;
import java.util.List;

public class Memory {

    public static int MEMORY_SIZE = 4096;



    private List<BitData> memory;

    public Memory() {
        memory = new ArrayList<>();
        for(int i = 0 ; i < 4096 ; i++) {
            memory.add(new BitData(16));
        }
    }


    public void initializeMemoryData(int address, boolean isNumeralData, int data) {
        memory.get(address).setIsSigned(isNumeralData);
        memory.get(address).setData(data);
    }


    public void setMemoryData(int address, int data) {
        memory.get(address).setData(data);
    }

    public int getMemoryData(int address) {
        return memory.get(address).getData();
    }
}
