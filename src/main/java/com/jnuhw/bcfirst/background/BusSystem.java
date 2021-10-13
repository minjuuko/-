package com.jnuhw.bcfirst.background;

import java.util.HashMap;

public class BusSystem {

    public enum RegisterType {
        AR, PC, DR, AC, IR, TR, INPR, OUTR, E
    }

    public enum CalculationType {
        AND, ADD
    }

    private final HashMap<RegisterType, Register> busSystem; // Bus System Register
    private final Register[] memory; // 4096개의 레지스터를 저장할 수 있는 32-bit Memory ( 16-bit로 구현 하고 싶었으나, Java의 short형으로는 불가능, unsigned short는 없음 )
    private int popedData; // bus System에 pop 되어있는 Data
    private int memoryKey; // Memory의 0부터 되어지는 Key, 데이터가 Insert될때 1씩 늘어나며 아래서 부터 채움.
    private boolean E; // E Data

    public BusSystem() {
        busSystem = new HashMap<>();
        memory = new Register[4096]; //
        popedData = 0;
        memoryKey = 0;

        for(RegisterType type : RegisterType.values()) { // bus System Regsiter 초기값 SET
            if(type == RegisterType.E) continue;
            busSystem.put(type, new Register());
        }
    }


    /*
        void popData
        args :
            type, key : 어떤 데이터를 Bus System에 pop할 지 결정한다.
     */
    public void popData(int key) {
        popedData = memory[key].getData();
    }

    public void popData(RegisterType type) {
        popedData = busSystem.get(type).getData();
    }


    /*
        void getOutData
        args :
            type : 어떤 레지스터의 데이터를 가져올지 결정함
     */
    public int getOutData(RegisterType type) {
        return busSystem.get(type).getData();
    }

    
    /*
        void setData
        args :
            type : 어떤 레지스터의 데이터 값을 설정할지 결정
        description : 입력되는 데이터는 poped된 데이터만 가능
     */
    public void setData(RegisterType type) {
        busSystem.get(type).setData(popedData);
    }

    
    /*
        void insertMemory
        args:
            @nullable key : Memory의 몇번째 Key에 입력할지 정함. 기본값은 0으로, method가 실행될 때 마다 1씩 상승
            data : Memory에 입력될 data
            poped : data를 poped Data로 대체함.
     */
    public void insertMemory(int data) { // Memory에 데이터를 입력함.
        insertMemory(memoryKey++, data, false);
    }

    public void insertMemory(int key, int data, boolean poped) { // Memory에 데이터를 입력함. key를 입력하지 않을 경우
        if(key < 4096) {
            memory[key] = new Register();
        }
        memory[key].setData(poped ? popedData : data);
    }


    
    /*
        void increaseRegister
        args :
            type : 어떤 레지스터에 INR 신호를 줄지 결정
     */
    public void increaseRegister(RegisterType type){
        busSystem.get(type).increase();
    }

    
    /*
        void getMemoryData
        args :
            key : Memory의 데이터를 받아옴
     */
    public int getMemoryData(int key) {
        return memory[key].getData();
    }


    /*
        void useAdder
        args :
            type : AND 인지 ADD인지
            dr : DR의 데이터를 계산할 것인가?
            ac : AC의 데이터를 계산할 것인가?
            inpr : INPR의 데이터를 계산할 것인가?
     */
    public void useAdder(CalculationType type, boolean dr, boolean ac, boolean inpr) {
        int data = 0x0000;

        switch(type) {
            case AND:
                if (dr) data = busSystem.get(RegisterType.DR).getData();
                if (inpr){
                    if(dr) data = Integer.parseInt(Integer.toBinaryString(data & busSystem.get(RegisterType.INPR).getData()), 2);
                    else data = busSystem.get(RegisterType.INPR).getData();
                }
                if(ac) {
                    data = Integer.parseInt(Integer.toBinaryString(data & busSystem.get(RegisterType.AC).getData()), 2);
                }
                break;
            case ADD:
                if (dr) data += busSystem.get(RegisterType.DR).getData();
                if (ac) data += busSystem.get(RegisterType.AC).getData();
                if (inpr) data += busSystem.get(RegisterType.INPR).getData();

                while (data > 0xFFFF) {
                    E = !E;
                    data -= 0xFFFF;
                }
                break;
        }

        busSystem.get(RegisterType.AC).setData(data);
    }
}
