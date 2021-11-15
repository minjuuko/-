package com.jnuhw.bcfirst.domain.cpu;

import com.jnuhw.bcfirst.domain.assembler.BitData;
import com.jnuhw.bcfirst.domain.assembler.Instruction;
import com.jnuhw.bcfirst.domain.assembler.Memory;

import java.util.HashMap;

/*
 * CPU를 코드로 구현한 가상 엔진
 * 모든 레지스터와 플립플롭을 HashMap 형태로 가지고 있으며,
 * 각각의 값을 조작 및 반환함
 */

public class CPUEngine {
    // 하나만 존재해야하고, 어디서든 접근할 수 있도록 싱글톤으로 관리
    private static CPUEngine instance;
    public static CPUEngine getInstance() {
        if (instance == null) {
            instance = new CPUEngine();
        }

        return instance;
    }


    private final HashMap<RegisterType, BitData> registers;
    private final HashMap<FlipFlopType, BitData> flipflops;
    private final Memory memory = new Memory();

    private CPUEngine() {
        registers = new HashMap<>();
        flipflops = new HashMap<>();

        for(RegisterType registerType : RegisterType.values()) {
            registers.put(registerType, new BitData(registerType.getBitSize(), registerType.isSigned()));
        }

        for(FlipFlopType flipFlopType : FlipFlopType.values()) {
            flipflops.put(flipFlopType, new BitData(1, false));
        }

    }

    public void useALU(Instruction instruction) {
        int drData = getRegisterData(RegisterType.DR);
        int acData = getRegisterData(RegisterType.AC);
        int result = 0;

        switch(instruction) {
            case AND:
                String binary = Integer.toBinaryString(drData & acData);
                if(binary.length() > 16) binary = binary.substring(binary.length()-16);
                result = Integer.parseInt(binary, 2);
                break;
            case ADD:
                result = drData + acData;

                if(result > 0xFFFF) {
                    setFlipFlopData(FlipFlopType.E, 1);
                    result -= 0xFFFF;
                }
                break;
        }

        setRegisterData(RegisterType.AC, result);
    }

    public int getRegisterData(RegisterType type) {
        return registers.get(type).getData();
    }

    public void setRegisterData(RegisterType type, int data) {
        registers.get(type).setData(data);
    }

    public void increaseRegister(RegisterType type){
        registers.get(type).increase();
    }

    public void clearRegister(RegisterType type) {
        registers.get(type).setData(0x0000);
    }

    public int getFlipFlopData(FlipFlopType type) {
        return flipflops.get(type).getData();
    }

    public void setFlipFlopData(FlipFlopType type, int data) {
        flipflops.get(type).setData(data);
    }

    public int getMemoryData(int address) {
        return memory.getMemoryData(address);
    }

    public void setMemoryData(int address, int data) {
        memory.setMemoryData(address, data);
    }

    public void initializeMemoryData(int address, boolean isNumeralData, int data) {
        memory.initializeMemoryData(address, isNumeralData, data);
    }
}
