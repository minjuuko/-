package com.jnuhw.bcfirst.view;

public class OutputView {

    public static void printMemory(int address, int value) {
        System.out.println("Memory[" + address + "] data : " + String.format("0X%04X", value));
    }

    public static void printErrorAnnouncement() {
        System.out.println("���α׷��� ���� �� ������ ���� ����Ǿ����ϴ�.");
    }

    public static void printUnknownInstructionError(int lc, String command) {
        System.out.println("�� �� ���� Instruction �Դϴ�. >");
        System.out.printf("[%d] %s", lc, command);
    }

    public static void printDataOverflowError(int data) {
        System.out.println("overflow �� �߻��Ͽ������ϴ�. data=" + data);
    }
}
