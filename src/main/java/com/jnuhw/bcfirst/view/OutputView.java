package com.jnuhw.bcfirst.view;

import com.jnuhw.bcfirst.domain.Utility;
import com.jnuhw.bcfirst.domain.assembler.Memory;
import com.jnuhw.bcfirst.domain.cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.cpu.FlipFlopType;
import com.jnuhw.bcfirst.domain.cpu.RegisterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OutputView {

    private static HashMap<Integer, Integer> prevMemoryDatas;

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

    public static void printOutput(int data) {
        System.out.println("[ OUTPUT ] " + data);
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

        System.out.println();
        System.out.println();
        System.out.println("[ 실행 결과 ]");
        System.out.println("선언된 데이터 중 변경된 메모리 목록");
        for(int key : keys) {
            String message = "Memory[0x%s] data : 0x%s";
            String keyHex = Utility.toFormatHexString(4, key).toUpperCase(Locale.ROOT);
            String valHex = Utility.toFormatHexString(4, CPUEngine.getInstance().getMemoryData(key)).toUpperCase(Locale.ROOT);

            System.out.println(String.format(message, keyHex, valHex));
        }

        System.out.println("\n프로그램 종료 시점 레지스터의 상태");
        for(RegisterType type : RegisterType.values()) {
            String message = "%s[0-%d] : 0x%s";
            String name = type.name();
            int size = type.getBitSize();
            String data = Utility.toFormatHexString(4, CPUEngine.getInstance().getRegisterData(type)).toUpperCase(Locale.ROOT);

            System.out.println(String.format(message, name, size-1, data));
        }

        System.out.println("\n프로그램 종료 시점 플립플롭의 상태");
        for(FlipFlopType type : FlipFlopType.values()) {
            String message = "%s : 0x%s";
            String name = type.name();
            String data = Utility.toFormatHexString(4, CPUEngine.getInstance().getFlipFlopData(type)).toUpperCase(Locale.ROOT);

            System.out.println(String.format(message, name, data));
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
}
