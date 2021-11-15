package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.controller.AssemblerController;
import com.jnuhw.bcfirst.view.InputView;

import java.util.*;

public class Application {

    public static void main(String[] args) {
        AssemblerController assemblerController = new AssemblerController();
        List<String> program = InputView.inputAssemblerProgram();
        assemblerController.run(program);
    }
}