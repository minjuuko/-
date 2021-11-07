package com.jnuhw.bcfirst.domain.Assembler;

import com.jnuhw.bcfirst.domain.Cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.Cpu.RegisterType;

import java.util.Arrays;

public class Executor {

    private static Executor instance;

    public static Executor getInstance() {
        if (instance == null)
            instance = new Executor();

        return instance;
    }

    public void execute(int startLc) {
        CPUEngine engine = CPUEngine.getInstance();
        engine.setRegisterData(RegisterType.PC, startLc);

        while (true) {
            int memoryData = engine.getMemoryData(engine.getRegisterData(RegisterType.PC)); // M[PC]의 데이터 ( Instruction )

            // Instruction Information
            int InstructionHexCode = memoryData;
            int operand = 0;
            int oprandAddress = 0;
            boolean isMri = Instruction.isMriHexCode(InstructionHexCode);
            boolean isInDirect = Instruction.isIndirectHexaCode(InstructionHexCode);
            if (isMri) {
                InstructionHexCode = Instruction.getInstructionHexaCodeFromMemoryHexaCode(memoryData);
                oprandAddress = Instruction.getDataHexaCodeFromMemoryHexaCode(memoryData);
                operand = engine.getMemoryData(oprandAddress);
            }

            int _instructionHexCode = InstructionHexCode;
            Instruction instruction = Arrays.stream(Instruction.values()).filter(i -> i.getHexaCode() == _instructionHexCode).findAny().get();
            instruction.setIsInDirect(isInDirect);
            switch (instruction) {
                // MRI Instruction
                case AND:
                    executeAND(operand);
                    break;
                case ADD:
                    executeADD(operand);
                    break;
                case LDA:
                    executeLDA(operand);
                    break;
                case STA:
                    executeSTA(oprandAddress);
                    break;
                case BUN:
                    executeBUN();
                    break;
                case BSA:
                    executeBSA();
                    break;
                case ISZ:
                    executeISZ();
                    break;

                // Register Instruction
                case CLA:
                    executeCLA();
                    break;
                case CLE:
                    executeCLE();
                    break;
                case CMA:
                    executeCMA();
                    break;
                case CME:
                    executeCME();
                    break;
                case CIR:
                    executeCIR();
                    break;
                case CIL:
                    executeCIL();
                    break;
                case INC:
                    executeINC();
                    break;
                case SPA:
                    executeSPA();
                    break;
                case SNA:
                    executeSNA();
                    break;
                case SZA:
                    executeSZA();
                    break;
                case HLT:
                    return;

                // IO Instruction
                case INP:
                    executeINP();
                    break;
                case OUT:
                    executeOUT();
                    break;
                case SKI:
                    executeSKI();
                    break;
                case SKO:
                    executeSKO();
                    break;
                case ION:
                    executeION();
                    break;
                case IOF:
                    executeIOF();
                    break;
                // Data
                default:
                    break;
            }

            // END 명령어 판별 필요?

            engine.increaseRegister(RegisterType.PC);
        }
    }

    private void executeAND(int operand) {
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, operand);

        CPUEngine.getInstance().useALU(Instruction.AND);
    }

    private void executeADD(int operand) {
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, operand);

        CPUEngine.getInstance().useALU(Instruction.ADD);
    }

    private void executeLDA(int operand) {
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, operand);
        CPUEngine.getInstance().setRegisterData(RegisterType.AC, operand);
    }

    private void executeSTA(int operandAddress) {
        int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
        CPUEngine.getInstance().setMemoryData(operandAddress, data);
    }

    private void executeBUN() {

    }

    private void executeBSA() {

    }

    private void executeISZ() {

    }

    private void executeCLA() {

    }

    private void executeCLE() {

    }

    private void executeCMA() {

    }

    private void executeCME() {

    }

    private void executeCIR() {

    }

    private void executeCIL() {

    }

    private void executeINC() {

    }

    private void executeSPA() {

    }

    private void executeSNA() {

    }

    private void executeSZA() {

    }

    private void executeINP() {

    }

    private void executeOUT() {

    }

    private void executeSKI() {

    }

    private void executeSKO() {

    }

    private void executeION() {

    }

    private void executeIOF() {

    }
}