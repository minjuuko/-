package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.controller.AssemblerController;
import com.jnuhw.bcfirst.view.InputView;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.*;

public class Application {

    // Main Method.
    public static void main(String[] args) {

        // 6-5 Code
//        String[] addingTwoVariableProgram = new String[]{
//                "ORG 1",
//                "LDA A",
//                "ADD B",
//                "STA C",
//                "HLT",
//                "A, DEC 83",
//                "B, DEC -63",
//                "C, DEC 0",
//                "END"
//        };

        AssemblerController assemblerController = new AssemblerController();
        List<String> program = InputView.inputAssemblerProgram();
        assemblerController.run(program);
    }
}
