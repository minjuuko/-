package com.jnuhw.bcfirst.domain.cpu;

import com.jnuhw.bcfirst.domain.assembler.BitData;
import com.jnuhw.bcfirst.domain.assembler.Instruction;
import com.jnuhw.bcfirst.domain.assembler.Memory;

import java.util.HashMap;

public class CPUEngine {

    private static CPUEngine instance;


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
            flipflops.put(flipFlopType, new BitData(1));
        }

    }

    public static CPUEngine getInstance() {
        if (instance == null) {
            instance = new CPUEngine();
        }

        return instance;
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
