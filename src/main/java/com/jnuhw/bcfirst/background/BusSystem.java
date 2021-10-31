package com.jnuhw.bcfirst.background;

/**
 * @deprecated class
 * BusSystem 설계는 확정되지 않았기 때문에, 대부분의 코드가 변경될 예정입니다.
 */

import com.jnuhw.bcfirst.domain.BitData;
import com.jnuhw.bcfirst.domain.Memory;

import java.util.HashMap;

public class BusSystem {

    private static BusSystem instance;

    public interface BusPart {}

    public enum RegisterType implements BusPart {
        AR(12), PC(12), DR(16), AC(16), IR(16), TR(16), OUTR(8), INPR(8), SC(4); // SC는 임의의 Size 부여, 책에서 못찾음.

        int bitSize;

        RegisterType(int size) {
            bitSize = size;
        }
    }

    public enum FlipFlopType implements BusPart {
        I, S, E, R, IEN, FGI, FGO
    }

    public enum CalculationType {
        AND, ADD
    }

    private final HashMap<BusPart, BitData> busSystem;
    private final Memory memory = new Memory();
    private int popedData;

    private BusSystem() {
        busSystem = new HashMap<>();
        popedData = 0;

        for(RegisterType registerType : RegisterType.values()) {
            busSystem.put(registerType, new BitData(registerType.bitSize));
        }
        for(FlipFlopType flipFlopType : FlipFlopType.values()) {
            busSystem.put(flipFlopType, new BitData(1));
        }

    }

    public static BusSystem getInstance() {
        if (instance == null) {
            instance = new BusSystem();
        }

        return instance;
    }

    public void popData(int key) {
        popedData = memory.getMemoryData(key);
    }

    public void popData(RegisterType type) {
        popedData = busSystem.get(type).getData();
    }

    public int getOutData(RegisterType type) {
        return busSystem.get(type).getData();
    }

    public void setData(RegisterType type) {
        busSystem.get(type).setData(popedData);
    }

    public void initializeMemoryData(int address, boolean isNumeralData, int data) {
        memory.initializeMemoryData(address, isNumeralData, data);
    }

    public void setMemoryData(int address, int data) {
        memory.setMemoryData(address, data);
    }

    public int getMemoryData(int address) {
        return memory.getMemoryData(address);
    }

    public void increaseRegister(RegisterType type){
        busSystem.get(type).increase();
    }

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
                    busSystem.get(FlipFlopType.E).setData(busSystem.get(FlipFlopType.E).getData() == 0 ? 1 : 0);
                    data -= 0xFFFF;
                }
                break;
        }

        busSystem.get(RegisterType.AC).setData(data);
    }
}
