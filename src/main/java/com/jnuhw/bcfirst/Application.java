package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.controller.AssemblerController;
import com.jnuhw.bcfirst.view.InputView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Application {

    public static void main(String[] args) {
        AssemblerController assemblerController = new AssemblerController();

        if (args.length == 0) {
            System.exit(0);
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            System.exit(0);
        }

        List<String> program = InputView.inputAssemblerProgram();
        assemblerController.run(program);

//        try {
//            List<String> program = new ArrayList<>();
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            while (br.ready()) {
//                program.add(br.readLine());
//            }
//            br.close();
//
//            assemblerController.run(program);
//        } catch (IOException e) {
//            System.exit(0);
//        }
    }
}