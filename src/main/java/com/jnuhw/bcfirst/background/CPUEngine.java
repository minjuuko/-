package com.jnuhw.bcfirst.background;

/**
 * @deprecated class
 * BusSystem 설계는 확정되지 않았기 때문에, 대부분의 코드가 변경될 예정입니다.
 */

import com.jnuhw.bcfirst.domain.BitData;
import com.jnuhw.bcfirst.domain.Memory;

import java.util.HashMap;

public class CPUEngine {

    private static CPUEngine instance;

//    public enum CalculationType {
//        AND, ADD
//    }

    private final HashMap<RegisterType, BitData> registers;
    private final HashMap<FlipFlopType, BitData> flipflops;
    private final Memory memory = new Memory();

    private CPUEngine() {
        registers = new HashMap<>();
        flipflops = new HashMap<>();

        for(RegisterType registerType : RegisterType.values()) {
            registers.put(registerType, new BitData(registerType.bitSize));
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

//    public void useAdder(CalculationType type, boolean dr, boolean ac, boolean inpr) {
//        int data = 0x0000;
//
//        switch(type) {
//            case AND:
//                if (dr) data = getRegisterData(RegisterType.DR);
//                if (inpr){
//                    if(dr) data = Integer.parseInt(Integer.toBinaryString(data & getRegisterData(RegisterType.INPR)), 2);
//                    else data = getRegisterData(RegisterType.INPR);
//                }
//                if(ac) {
//                    data = Integer.parseInt(Integer.toBinaryString(data & getRegisterData(RegisterType.AC)), 2);
//                }
//                break;
//            case ADD:
//                if (dr) data += getRegisterData(RegisterType.DR);
//                if (ac) data += getRegisterData(RegisterType.AC);
//                if (inpr) data += getRegisterData(RegisterType.INPR);
//
//                while (data > 0xFFFF) {
//                    int eData = getFlipFlopData(FlipFlopType.E);
//                    if(eData == 0) setFlipFlopData(FlipFlopType.E, 1);
//                    else setFlipFlopData(FlipFlopType.E, 0);
//
//                    data -= 0xFFFF;
//                }
//                break;
//        }
//
//        setRegisterData(RegisterType.AC, data);
//    }

    public int getRegisterData(RegisterType type) {
        return registers.get(type).getData();
    }

    public void setRegisterData(RegisterType type, int data) {
        registers.get(type).setData(data);
    }

    public void increaseRegister(RegisterType type){
        registers.get(type).increase();
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
