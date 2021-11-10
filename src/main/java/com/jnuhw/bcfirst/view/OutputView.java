package com.jnuhw.bcfirst.view;

import com.jnuhw.bcfirst.domain.assembler.Memory;
import com.jnuhw.bcfirst.domain.cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.cpu.RegisterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private static HashMap<Integer, Integer> prevMemoryDatas;

    public static void printMemory(int address, int value) {
        System.out.println("Memory[" + address + "] data : " + String.format("0X%04X", value));
    }

    public static void printErrorAnnouncement() {
        System.out.println("프로그램이 실행 중 오류로 인해 종료되었습니다.");
    }

    public static void printUnknownInstructionError(int lc, String command) {
        System.out.println("알 수 없는 Instruction 입니다. >");
        System.out.printf("[%d] %s", lc, command);
    }

    public static void printDataOverflowError(int data) {
        System.out.println("overflow 가 발생하였습습니다. data=" + data);
    }

    public static void saveMemoryData() {
        prevMemoryDatas = new HashMap<>();

        for(int i = 0 ; i < Memory.MEMORY_SIZE ; i++) {
            int data = CPUEngine.getInstance().getMemoryData(i);
            prevMemoryDatas.put(i, data);
        }
    }

    public static void printResultView() {
        List<Integer> keys = findChangedMemories();

        System.out.println("프로그램 실행 후, 저장된 데이터가 변경된 메모리 목록입니다.");
        for(int key : keys) {
            String message = "Memory[0x%s] data : 0x%s";
            String keyHex = keepLength(Integer.toHexString(key).toUpperCase(Locale.ROOT), 3);
            String valHex = keepLength(Integer.toHexString(CPUEngine.getInstance().getMemoryData(key)).toUpperCase(Locale.ROOT), 4);

            System.out.println(String.format(message, keyHex, valHex));
        }

        System.out.println("\n프로그램 종료 시점 레지스터의 상태입니다.");
        for(RegisterType type : RegisterType.values()) {
            String message = "%s[0-%d] : 0x%s";
            String regName = type.name();
            int regSize = type.getBitSize();
            String regData = keepLength(Integer.toHexString(CPUEngine.getInstance().getRegisterData(type)).toUpperCase(Locale.ROOT), 4);

            System.out.println(String.format(message, regName, regSize-1, regData));
        }
    }

    private static List<Integer> findChangedMemories() {
        List<Integer> keys = new ArrayList<>();

        for(int key : prevMemoryDatas.keySet()) {
            int data = CPUEngine.getInstance().getMemoryData(key);
            if(prevMemoryDatas.get(key) != data) {
                keys.add(key);
            }
        }

        return keys;
    }

    private static String keepLength(String val, int length) {
        while(val.length() < length) {
            val = "0" + val;
        }
        if(val.length() > length) {
            val = val.substring(val.length()-length);
        }

        return val;
    }
}
