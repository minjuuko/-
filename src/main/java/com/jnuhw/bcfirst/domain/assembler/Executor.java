package com.jnuhw.bcfirst.domain.Assembler;

import com.jnuhw.bcfirst.domain.Cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.Cpu.FlipFlopType;
import com.jnuhw.bcfirst.domain.Cpu.RegisterType;
import com.jnuhw.bcfirst.domain.Utility;

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
            int instructionDataInMemory = engine.getMemoryData(engine.getRegisterData(RegisterType.PC)); // M[PC]의 데이터 ( Instruction )
            engine.increaseRegister(RegisterType.PC);

            // Instruction Information
            int InstructionHexCode = instructionDataInMemory;
            int operand = 0;
            int operandAddress = 0;
            boolean isMri = Instruction.isMriHexCode(InstructionHexCode);
            boolean isInDirect = Instruction.isIndirectHexaCode(InstructionHexCode);
            if (isMri) {
                InstructionHexCode = Instruction.getInstructionHexaCodeFromMemoryHexaCode(instructionDataInMemory);
                operandAddress = Instruction.getDataHexaCodeFromMemoryHexaCode(instructionDataInMemory);
                operand = engine.getMemoryData(operandAddress);
            }

            int _instructionHexCode = InstructionHexCode;
            Instruction instruction = Arrays.stream(Instruction.values()).filter(i -> i.getHexaCode() == _instructionHexCode).findAny().get();
            instruction.setIsInDirect(isInDirect);
            switch (instruction) {
                // MRI Instruction
                case AND:
                    executeAND(operandAddress, isInDirect);
                    break;
                case ADD:
                    executeADD(operandAddress, isInDirect);
                    break;
                case LDA:
                    executeLDA(operandAddress, isInDirect);
                    break;
                case STA:
                    executeSTA(operandAddress, isInDirect);
                    break;
                case BUN:
                    executeBUN(operandAddress, isInDirect);
                    break;
                case BSA:
                    executeBSA(operandAddress, isInDirect);
                    break;
                case ISZ:
                    executeISZ(operandAddress, isInDirect);
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
        }
    }

    private void setARWithOperand(int operandAddress, boolean isIndirect) {
        CPUEngine.getInstance().setRegisterData(RegisterType.AR, operandAddress);
        if (isIndirect) {
            int indirectAddress = CPUEngine.getInstance().getMemoryData(operandAddress);
            CPUEngine.getInstance().setRegisterData(RegisterType.AR, indirectAddress);
        }
    }

    private void executeAND(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int data = CPUEngine.getInstance().getMemoryData(address);
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, data);

        CPUEngine.getInstance().useALU(Instruction.AND);
    }

    private void executeADD(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int data = CPUEngine.getInstance().getMemoryData(address);
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, data);

        CPUEngine.getInstance().useALU(Instruction.ADD);
    }

    private void executeLDA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int data = CPUEngine.getInstance().getMemoryData(address);
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, data);

        // AC <- DR
        CPUEngine.getInstance().setRegisterData(RegisterType.AC, data);
    }

    private void executeSTA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
        int address = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        CPUEngine.getInstance().setMemoryData(address, data);
    }




    private void executeBUN(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // PC <- AR
        int arData = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        CPUEngine.getInstance().setRegisterData(RegisterType.PC, arData);
    }

    private void executeBSA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // M[AR] <- PC
        int arAddress = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);
        CPUEngine.getInstance().setMemoryData(arAddress, pcData);

        // AR <- AR + 1
        CPUEngine.getInstance().increaseRegister(RegisterType.AC);

        // PC <- AR
        int acData = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
        CPUEngine.getInstance().setRegisterData(RegisterType.PC, acData);
    }

    private void executeISZ(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int data = CPUEngine.getInstance().getMemoryData(address);
        CPUEngine.getInstance().setRegisterData(RegisterType.DR, data);

        // DR <- DR + 1
        CPUEngine.getInstance().increaseRegister(RegisterType.DR);

        // M[AR] <- DR
        int drData = CPUEngine.getInstance().getRegisterData(RegisterType.DR);
        CPUEngine.getInstance().setMemoryData(address, drData);
        if (drData == 0) {  //if(DR = 0)
            CPUEngine.getInstance().increaseRegister(RegisterType.AR);
        }
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
        String acBinary = Utility.toFormatBinaryString(CPUEngine.getInstance().getRegisterData(RegisterType.AC));
        int topBit = Character.getNumericValue(acBinary.charAt(acBinary.length() - 1));
        int eBit = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);

        CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, topBit);
        String newAcBinary = eBit + acBinary.substring(0, acBinary.length() - 1);

    }

    private void executeCIL() {
        String acBinary = Utility.toFormatBinaryString(CPUEngine.getInstance().getRegisterData(RegisterType.AC));
        int topBit = Character.getNumericValue(acBinary.charAt(0));
        int eBit = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);

        CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, topBit);
        String newAcBinary = acBinary.substring(1) + eBit;
    }

    private void executeINC() {
        CPUEngine.getInstance().increaseRegister(RegisterType.AC);
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