package com.jnuhw.bcfirst.domain.Assembler;

import com.jnuhw.bcfirst.domain.Cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.Cpu.FlipFlopType;
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
            int memoryData = engine.getMemoryData(engine.getRegisterData(RegisterType.PC)); 

          
            int InstructionHexCode = memoryData;
            int operand = 0;
            boolean isMri = Instruction.isMriHexCode(InstructionHexCode);
            boolean isInDirect = Instruction.isIndirectHexaCode(InstructionHexCode);
            if (isMri) {
                InstructionHexCode = Instruction.getInstructionHexaCodeFromMemoryHexaCode(memoryData);
             
                operand = Instruction.getDataHexaCodeFromMemoryHexaCode(memoryData);
            }

            int _instructionHexCode = InstructionHexCode;
            Instruction instruction = Arrays.stream(Instruction.values()).filter(i -> i.getHexaCode() == _instructionHexCode).findAny().get();
            instruction.setIsInDirect(isInDirect);
            switch (instruction) {
               
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
                
                default:
                    break;
            }


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
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	String binaryString = Integer.toBinaryString(data);
    	char c[]= binaryString.toCharArray();
    	String Zero = "0";
    	for(int i = 0; i < 15-binaryString.length();i++) {
    		Zero = Zero + "0";
    	}
    	binaryString = Zero + binaryString; 
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	for(int k = 0 ; k < 16; k++) {
    		c[k]= binaryString.toCharArray()[k];
    	}
    	for(int i = 0; i < 16 ; i ++) {
    		if(i==0) {
    			CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, binaryString.charAt(i));
    		}
    		else if(i==15) {
    			char e = (char)CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);
    			a[i] = e;
    		}
    		else {
    			a[i-1]=c[i];
    		}
    		
    	}
        String binary = String.valueOf(a);
        int data1 = Integer.parseInt(binary,2);
        CPUEngine.getInstance().setRegisterData(RegisterType.AC, data1);

    }

    private void executeCIL() {
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	String binaryString = Integer.toBinaryString(data);
    
    	char c[]= binaryString.toCharArray();
    	
    	String Zero = "0";
    	for(int i = 0; i < 15-binaryString.length();i++) {
    		Zero = Zero + "0";
    	}
    	binaryString = Zero + binaryString; 
    	
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    
    	for(int i = 0; i < 16 ; i ++) {
    		if(i==0) {
    			char e = (char)CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);
    			a[i] = e;
    			
    		}
    		else if(i==15) {
    			CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, binaryString.charAt(i));
    		}
    		else {
    			a[i+1]=c[i];
    		}
    		
    	}
        String binary = String.valueOf(a);
        int data1 = Integer.parseInt(binary,2);
        CPUEngine.getInstance().setRegisterData(RegisterType.AC, data1);


    }

    private void executeINC() {
    	
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	data = data+1;
    	CPUEngine.getInstance().setRegisterData(RegisterType.AC, data);

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