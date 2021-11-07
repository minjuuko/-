package com.jnuhw.bcfirst.domain.Assembler;

import com.jnuhw.bcfirst.domain.Cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.Cpu.RegisterType;

import java.util.Arrays;

public class Executor {

    private static Executor instance;

    public static Executor getInstance() {
        if(instance == null)
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
            boolean isMri = Instruction.isMriHexCode(InstructionHexCode);
            boolean isInDirect = Instruction.isIndirectHexaCode(InstructionHexCode);
            if (isMri) {
                InstructionHexCode = Instruction.getInstructionHexaCodeFromMemoryHexaCode(memoryData);
                // int oprandAddress = Instruction.getDataHexaCodeFromMemoryHexaCode(memoryData);
                // operand = engine.getMemoryData(oprandAddress);
                operand = Instruction.getDataHexaCodeFromMemoryHexaCode(memoryData);
            }

            int _instructionHexCode = InstructionHexCode;
            Instruction instruction = Arrays.stream(Instruction.values()).filter(i -> i.getHexaCode() == _instructionHexCode).findAny().get();
            instruction.setIsInDirect(isInDirect);
            switch (instruction) {
                // MRI Instruction
                case AND:
                    break;
                case ADD:
                    executeADD();
                    break;
                case LDA:
                    executeLDA();
                    break;
                case STA:
                    executeSTA();
                    break;
                case BUN:
                    executeBUN(operand,isInDirect);
                    break;
                case BSA:
                    executeBSA(operand,isInDirect);
                    break;
                case ISZ:
                    executeISZ(operand,isInDirect);
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

    private void executeAND() {
    }

    private void executeADD() {

    }

    private void executeLDA() {
    }

    private void executeSTA() {

    }

    private void executeBUN(int operand, boolean isIndirect) {
        CPUEngine.getInstance().setRegisterData(RegisterType.AR,operand);

        if(isIndirect){     //간접주소
            int inData = CPUEngine.getInstance().getMemoryData(operand);    //M[AR]
            CPUEngine.getInstance().setRegisterData(RegisterType.AR,inData);
            //System.out.println("M[AR] :"+inData);
        }   //AR <-M[AR]

        int arData=CPUEngine.getInstance().getRegisterData(RegisterType.AR);    //AR
        //System.out.println("AR or M[AR]:"+arData);
        CPUEngine.getInstance().setRegisterData(RegisterType.PC,arData);    //PC <- AR
        //int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);
        //System.out.println("PC : "+pcData);

    }

    private void executeBSA(int operand, boolean isIndirect) {
        CPUEngine.getInstance().setRegisterData(RegisterType.AR,operand);

        if(isIndirect){     //간접주소
            int inData = CPUEngine.getInstance().getMemoryData(operand);    //M[AR]
            CPUEngine.getInstance().setRegisterData(RegisterType.AR,inData);
            //System.out.println("M[AR] :"+inData);
        }   //AR <-M[AR]

        int arData=CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
        // System.out.println("PC :"+pcData);
        CPUEngine.getInstance().setMemoryData(arData,pcData);  //M[AR] <- PC
        // System.out.println("M[AR] or M[M[AR]] :"+arData);

        arData +=1; // AR <- AR+1
        CPUEngine.getInstance().setRegisterData(RegisterType.PC,arData);    //PC <- AR

        int pcData2 = CPUEngine.getInstance().getRegisterData(RegisterType.PC);
        //System.out.println(pcData2);
     }

    private void executeISZ(int operand, boolean isIndirect) {
        CPUEngine.getInstance().setRegisterData(RegisterType.AR,operand);

        if(isIndirect){     //간접주소
            int inData = CPUEngine.getInstance().getMemoryData(operand);    //M[AR]
            CPUEngine.getInstance().setRegisterData(RegisterType.AR,inData);
            //System.out.println("M[AR] :"+inData);
        }   //AR <-M[AR]

        int arData=CPUEngine.getInstance().getRegisterData(RegisterType.AR);
        int data = CPUEngine.getInstance().getMemoryData(arData);
        CPUEngine.getInstance().setRegisterData(RegisterType.AR,data); //M[AR]
        int marData = CPUEngine.getInstance().getRegisterData(RegisterType.AR);   //marData = M[AR]
        //System.out.println("M[AR] or M[M[AR]]:"+marData);
        CPUEngine.getInstance().setRegisterData(RegisterType.DR,marData);  //DR<- M[AR]

        int drData = CPUEngine.getInstance().getRegisterData(RegisterType.DR);  //DR
        drData +=1; //DR <- DR+1
        //System.out.println("DR :"+drData);

        CPUEngine.getInstance().setMemoryData(marData,drData);  //M[AR] <- DR
        if(drData==0){  //if(DR = 0)
            int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
            pcData +=1; //PC+1
            CPUEngine.getInstance().setRegisterData(RegisterType.PC,pcData);    //PC<-PC+1
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