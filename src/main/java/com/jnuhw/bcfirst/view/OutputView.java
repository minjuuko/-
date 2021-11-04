package com.jnuhw.bcfirst.view;

public class OutputView {

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
}
