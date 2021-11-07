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
                    executeAND();
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
                case SZE:
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
    	int acData = CPUEngine.getInstance().getRegisterData(RegisterType.AC); // AC값 acData에 저장
    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'}; // AC를 2진수 형태로 저장할 배열
    	for(int i = 15; i >= 0 ; i--) {
    		if((acData&(1<<i))==0)
    			arr[15-i]= '0';
    		else
    			arr[15-i]='1';
    		}
    	//AC를 2진수로 변환
    	if(arr[0]==0) //if(AC<0)
    	{  
            int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
            pcData +=1; //PC+1
            CPUEngine.getInstance().setRegisterData(RegisterType.PC,pcData);    //PC<-PC+1
            
          //System.out.println("PC : "+pcData);
        }

    }
    private void executeSNA() {
    	int acData = CPUEngine.getInstance().getRegisterData(RegisterType.AC); // AC값 acData에 저장
    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	for(int i = 15; i >= 0 ; i--) {
    		if((acData&(1<<i))==0)
    			arr[15-i]= '0';
    		else
    			arr[15-i]='1';
    		}
    	if(arr[0]==1)
    	{  //if(AC<0)
            int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
            pcData +=1; //PC+1
            CPUEngine.getInstance().setRegisterData(RegisterType.PC,pcData);    //PC<-PC+1
            //System.out.println("PC : "+pcData);
        }
    	
    }

    private void executeSZA() {
    	int acData = CPUEngine.getInstance().getRegisterData(RegisterType.AC); // AC값 acData에 저장
  
        if(acData==0){  //if(AC = 0)
            int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
            pcData +=1; //PC+1
            CPUEngine.getInstance().setRegisterData(RegisterType.PC,pcData);    //PC<-PC+1
            //System.out.println("PC : "+pcData);
        }
    
    }

    private void executeSZE() {
    	int eData = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);
    	if(eData==0){  //if(E=0)
            int pcData = CPUEngine.getInstance().getRegisterData(RegisterType.PC);  //PC
            pcData +=1; //PC+1
            CPUEngine.getInstance().setRegisterData(RegisterType.PC,pcData);    //PC<-PC+1
            //System.out.println("PC : "+pcData);
        }

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
