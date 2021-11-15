package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.domain.cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.cpu.FlipFlopType;
import com.jnuhw.bcfirst.domain.cpu.RegisterType;
import com.jnuhw.bcfirst.view.InputView;

import java.util.ArrayList;
import java.util.List;

public class Interrupt {
    private static Interrupt instance;
    public static Interrupt getInstance() {
        if (instance == null) {
            instance = new Interrupt();
        }

        return instance;
    }


    private final CPUEngine cpuEngine = CPUEngine.getInstance();

    private List<Integer> externalInputTimings = new ArrayList<>();
    private List<Integer> externalOutputTimings = new ArrayList<>();

    public void addExternalInputTimings(int t) {
        externalInputTimings.add(t);
    }

    public void addExternalOutputTimings(int t) {
        externalOutputTimings.add(t);
    }


    public void executeInterruptCycle() {
        // M[0] <- PC
        int pcData = cpuEngine.getRegisterData(RegisterType.PC);
         cpuEngine.setMemoryData(0, pcData);

         // PC <- 1
        cpuEngine.setRegisterData(RegisterType.PC, 1);

        // IEN <- 0, R <- 0
        cpuEngine.setFlipFlopData(FlipFlopType.IEN, 0);
        cpuEngine.setFlipFlopData(FlipFlopType.R, 0);
    }

    public void checkInterruptFlags() {
        if (cpuEngine.getFlipFlopData(FlipFlopType.IEN) == 0) {
            return;
        }

        if (cpuEngine.getFlipFlopData(FlipFlopType.FGI) == 1 || cpuEngine.getFlipFlopData(FlipFlopType.FGO) == 1) {
            cpuEngine.setFlipFlopData(FlipFlopType.R, 1);
        }
    }

    public void checkExternalInput() {
        // External Input Interrupt 위치인지 확인
        int currentPc = cpuEngine.getRegisterData(RegisterType.PC);
        if (!externalInputTimings.contains(currentPc)) {
            return;
        }

        String input = InputView.getExternalInput();
        cpuEngine.setRegisterData(RegisterType.INPR, Integer.parseInt(input));

        cpuEngine.setFlipFlopData(FlipFlopType.FGI, 1);
    }

    public void checkExternalOutput() {
        // External Output Interrupt 위치인지 확인
        int currentPc = cpuEngine.getRegisterData(RegisterType.PC);
        if (!externalInputTimings.contains(currentPc)) {
            return;
        }

        cpuEngine.setFlipFlopData(FlipFlopType.FGO, 1);
    }
}
