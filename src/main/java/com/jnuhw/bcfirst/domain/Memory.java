package com.jnuhw.bcfirst.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memory {

    // 4096 개의 16비트 숫자 데이터들
    private List<BitData> memory = new ArrayList<>(Collections.nCopies(4096, new BitData(16, true)));

    public void setMemoryData(int address, int data) {
        memory.get(address).setData(data);
    }

    public int getMemoryData(int address) {
        return memory.get(address).getData();
    }
}
