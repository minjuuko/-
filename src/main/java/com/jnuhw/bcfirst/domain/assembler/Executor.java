package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.domain.Utility;
import com.jnuhw.bcfirst.domain.cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.cpu.FlipFlopType;
import com.jnuhw.bcfirst.domain.cpu.RegisterType;

import java.util.Arrays;

public class Executor {

    private static Executor instance;

    public static Executor getInstance() {
        if (instance == null)
            instance = new Executor();

        return instance;
    }

    private CPUEngine cpuEngine = CPUEngine.getInstance();

    public void execute(int startLc) {
        cpuEngine.setRegisterData(RegisterType.PC, startLc);

        while (true) {
            int instructionDataInMemory = cpuEngine.getMemoryData(cpuEngine.getRegisterData(RegisterType.PC)); // M[PC]의 데이터 ( Instruction )
            cpuEngine.increaseRegister(RegisterType.PC);
            cpuEngine.setRegisterData(RegisterType.IR, instructionDataInMemory);

            // Instruction Information
            int InstructionHexCode = instructionDataInMemory;
            int operandAddress = 0;
            boolean isMri = Instruction.isMriHexCode(InstructionHexCode);
            boolean isInDirect = Instruction.isIndirectHexaCode(InstructionHexCode);
            if (isMri) {
                InstructionHexCode = Instruction.getInstructionHexaCodeFromMemoryHexaCode(instructionDataInMemory);
                operandAddress = Instruction.getDataHexaCodeFromMemoryHexaCode(instructionDataInMemory);
            }

            int _instructionHexCode = isInDirect ? InstructionHexCode - Instruction.INDIRECT_CODE : InstructionHexCode;
            Instruction instruction = Arrays.stream(Instruction.values())
                    .filter(i -> i.getHexaCode() == _instructionHexCode)
                    .findAny().get();
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
                case SZE:
                    executeSZE();
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
        cpuEngine.setRegisterData(RegisterType.AR, operandAddress);
        if (isIndirect) {
            int indirectAddress = cpuEngine.getMemoryData(operandAddress);
            cpuEngine.setRegisterData(RegisterType.AR, indirectAddress);
        }
    }

    private void executeAND(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = cpuEngine.getRegisterData(RegisterType.AR);
        int data = cpuEngine.getMemoryData(address);
        cpuEngine.setRegisterData(RegisterType.DR, data);

        cpuEngine.useALU(Instruction.AND);
    }

    private void executeADD(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = cpuEngine.getRegisterData(RegisterType.AR);
        int data = cpuEngine.getMemoryData(address);
        cpuEngine.setRegisterData(RegisterType.DR, data);

        cpuEngine.useALU(Instruction.ADD);
    }

    private void executeLDA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = cpuEngine.getRegisterData(RegisterType.AR);
        int data = cpuEngine.getMemoryData(address);
        cpuEngine.setRegisterData(RegisterType.DR, data);

        // AC <- DR
        cpuEngine.setRegisterData(RegisterType.AC, data);
    }

    private void executeSTA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        int data = cpuEngine.getRegisterData(RegisterType.AC);
        int address = cpuEngine.getRegisterData(RegisterType.AR);
        cpuEngine.setMemoryData(address, data);
    }


    private void executeBUN(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // PC <- AR
        int arData = cpuEngine.getRegisterData(RegisterType.AR);
        cpuEngine.setRegisterData(RegisterType.PC, arData);
    }

    private void executeBSA(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // M[AR] <- PC
        int arAddress = cpuEngine.getRegisterData(RegisterType.AR);
        int pcData = cpuEngine.getRegisterData(RegisterType.PC);
        cpuEngine.setMemoryData(arAddress, pcData);

        // AR <- AR + 1
        cpuEngine.increaseRegister(RegisterType.AR);

        // PC <- AR
        int acData = cpuEngine.getRegisterData(RegisterType.AR);
        cpuEngine.setRegisterData(RegisterType.PC, acData);
    }

    private void executeISZ(int operandAddress, boolean isIndirect) {

        // AR <- Operand
        setARWithOperand(operandAddress, isIndirect);

        // DR <- M[AR]
        int address = cpuEngine.getRegisterData(RegisterType.AR);
        int data = cpuEngine.getMemoryData(address);
        cpuEngine.setRegisterData(RegisterType.DR, data);

        // DR <- DR + 1
        cpuEngine.increaseRegister(RegisterType.DR);

        // M[AR] <- DR
        int drData = cpuEngine.getRegisterData(RegisterType.DR);
        cpuEngine.setMemoryData(address, drData);
        if (drData == 0) {  //if(DR = 0)
            cpuEngine.increaseRegister(RegisterType.PC);
        }
    }


    private void executeCLA() {
        cpuEngine.setRegisterData(RegisterType.AC, 0);
    }

    private void executeCLE() {
        cpuEngine.setFlipFlopData(FlipFlopType.E, 0);
    }

    private void executeCMA() {

        int data = cpuEngine.getRegisterData(RegisterType.AC);
        int newAcData = ~(data);
        cpuEngine.setRegisterData(RegisterType.AC, newAcData);

    }
    private void executeCME() {
        int data = cpuEngine.getFlipFlopData(FlipFlopType.E);
        if(data == 0)
            data = 1;
        else
            data = 0;

        cpuEngine.setFlipFlopData(FlipFlopType.E, data);
    }

    private void executeCIR() {
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, cpuEngine.getRegisterData(RegisterType.AC));
        int topBit = Character.getNumericValue(acBinaryData.charAt(acBinaryData.length() - 1));
        int eBit = cpuEngine.getFlipFlopData(FlipFlopType.E);

        cpuEngine.setFlipFlopData(FlipFlopType.E, topBit);
        String newAcBinaryData = eBit + acBinaryData.substring(0, acBinaryData.length() - 1);
        int newAcData = Integer.parseInt(newAcBinaryData, 2);
        cpuEngine.setRegisterData(RegisterType.AC, newAcData);
    }

    private void executeCIL() {
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, cpuEngine.getRegisterData(RegisterType.AC));
        int topBit = Character.getNumericValue(acBinaryData.charAt(0));
        int eBit = cpuEngine.getFlipFlopData(FlipFlopType.E);

        cpuEngine.setFlipFlopData(FlipFlopType.E, topBit);
        String newAcBinaryData = acBinaryData.substring(1) + eBit;
        int newAcData = Integer.parseInt(newAcBinaryData, 2);
        cpuEngine.setRegisterData(RegisterType.AC, newAcData);
    }

    private void executeINC() {
        cpuEngine.increaseRegister(RegisterType.AC);
    }




    private void executeSPA() {
        int acData = cpuEngine.getRegisterData(RegisterType.AC); // AC값 acData에 저장
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, acData);

        //if(AC<0)
        if (acBinaryData.charAt(0) == 0) {

            //PC<-PC+1
            cpuEngine.increaseRegister(RegisterType.PC);
        }
    }

    private void executeSNA() {
        int acData = cpuEngine.getRegisterData(RegisterType.AC); // AC값 acData에 저장
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, acData);

        //if(AC<0)
        if (acBinaryData.charAt(0) == 1) {

            //PC<-PC+1
            cpuEngine.increaseRegister(RegisterType.PC);
        }

    }

    private void executeSZA() {
        int acData = cpuEngine.getRegisterData(RegisterType.AC); // AC값 acData에 저장

        //if(AC = 0)
        if (acData == 0) {

            //PC<-PC+1
            cpuEngine.increaseRegister(RegisterType.PC);
        }

    }

    private void executeSZE() {
        int eData = cpuEngine.getFlipFlopData(FlipFlopType.E);
        if (eData == 0) {

            //PC<-PC+1
            cpuEngine.increaseRegister(RegisterType.PC);
        }
    }




    private void executeINP() {

        // AC(0-7) <- INPR
        int inprData = cpuEngine.getRegisterData(RegisterType.INPR);
        String inprBinaryData = Utility.toFormatBinaryString(RegisterType.INPR, inprData);

        int acData = cpuEngine.getRegisterData(RegisterType.AC);
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, acData);
        String newAcBinaryData = acBinaryData.substring(0, acBinaryData.length() - 8) + inprBinaryData;
        int newAcData = Integer.parseInt(newAcBinaryData, 2);
        cpuEngine.setRegisterData(RegisterType.AC, newAcData);

        // FGI <- 0
        cpuEngine.setFlipFlopData(FlipFlopType.FGI, 0);

    }

    private void executeOUT() {

        // OUTR <- AC(0-7)
        int acData = cpuEngine.getRegisterData(RegisterType.AC);
        String acBinaryData = Utility.toFormatBinaryString(RegisterType.AC, acData);
        String dividedAcBinaryData = acBinaryData.substring(acBinaryData.length() - 8);
        int dividedAcData = Integer.parseInt(dividedAcBinaryData, 2);

        cpuEngine.setRegisterData(RegisterType.OUTR, dividedAcData);

        // FGO <- 0
        cpuEngine.setFlipFlopData(FlipFlopType.FGO, 0);
    }

    private void executeSKI() {
        if (cpuEngine.getFlipFlopData(FlipFlopType.FGI) == 1) {
            cpuEngine.increaseRegister(RegisterType.PC);
        }
    }

    private void executeSKO() {
        if (cpuEngine.getFlipFlopData(FlipFlopType.FGO) == 1) {
            cpuEngine.increaseRegister(RegisterType.PC);
        }
    }

    private void executeION() {
        cpuEngine.setFlipFlopData(FlipFlopType.IEN, 1);
    }

    private void executeIOF() {
        cpuEngine.setFlipFlopData(FlipFlopType.IEN, 0);
    }
}