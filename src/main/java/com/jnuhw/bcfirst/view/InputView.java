package com.jnuhw.bcfirst.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    static Scanner scanner = new Scanner(System.in);

    public static List<String> inputAssemblerProgram() {
        List<String> program = new ArrayList<>();
        String command;
        do {
            String code = scanner.nextLine();
            program.add(code);
            command = code.split(" ")[0];
        } while (command.equals("END"));

        return program;
    }
}
