package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.controller.AssemblerController;

import java.util.*;

public class Application {

    // Main Method.
    public static void main(String[] args) {

        String[] addingTwoVariableProgram = new String[]{
                "ORG 1",
                "LDA A",
                "ADD B",
                "STA C",
                "HLT",
                "A, DEC 83",
                "B, DEC -63",
                "C, DEC 0",
                "END"
        }; // 6-5 Code

        AssemblerController assemblerController = new AssemblerController();
        assemblerController.run(Arrays.asList(addingTwoVariableProgram));
    }
}
