package com.jnuhw.bcfirst.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    static Scanner scanner = new Scanner(System.in);

    public static List<String> inputAssemblerProgram() {
        List<String> program = new ArrayList<>();
        String command;
        do {
            String code = scanner.nextLine().toUpperCase(Locale.ROOT);
            if(code.startsWith("SUB")) {
                program = addSubroutine(program, code.split(" ")[1]);
            } else {
                program.add(code);
            }
            command = code.split(" ")[0];
        } while (!command.equals("END"));

        return program;
    }

    public static int inputStartLc() {
        System.out.println("실행을 시작할 메모리 주소를 입력하세요 (제일 첫 주소는 0)");
        return Integer.parseInt(scanner.nextLine());
    }

    public static String getExternalInput() {
        System.out.println("외부 인터럽트를 통한 입력을 작성하세요 > ");
        return scanner.nextLine();
    }


    /*
        Subroutine Debuging용 자동입력 메소드
     */
    private static List<String> addSubroutine(List<String> program, String key) {
        switch(key) {
            case "SH4":
                program.add("SH4, HEX 0");
                program.add("CIL");
                program.add("CIL");
                program.add("CIL");
                program.add("CIL");
                program.add("AND MSK");
                program.add("BUN SH4 I");
                program.add("MSK, HEX FFF0");
                return program;
            case "OR":
                program.add("OR, HEX 0");
                program.add("CMA");
                program.add("STA TMP");
                program.add("LDA OR I");
                program.add("CMA");
                program.add("AND TMP");
                program.add("CMA");
                program.add("ISZ OR");
                program.add("BUN OR I");
                program.add("TMP, HEX 0");
                return program;
            case "MVE":
                program.add("MVE, HEX 0");
                program.add("LDA MVE I");
                program.add("STA PT1");
                program.add("ISZ MVE");
                program.add("LDA MVE I");
                program.add("STA PT2");
                program.add("ISZ MVE");
                program.add("LDA MVE I");
                program.add("STA CTR");
                program.add("ISZ MVE");
                program.add("LOP, LDA PT1 I");
                program.add("STA PT2 I");
                program.add("ISZ PT1");
                program.add("ISZ PT2");
                program.add("ISZ CTR");
                program.add("BUN LOP");
                program.add("BUN MVE I");
                program.add("PT1, DEC 0");
                program.add("PT2, DEC 0");
                program.add("CTR, DEC 0");
                return program;


        }
        return program;
    }
}
