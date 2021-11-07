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
    	//CPUEngine.getInstance().setRegisterData(RegisterType.AC, 34);
    	
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC); 
    				//AC의 값을 받아와 data에 저장
    	
    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
	    //받아온 AC의 값을 2진수 형태로 저장할 크기가 16인 char배열 선언
	
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
	    //위의 2진수 형태로 저장된 배열을 왼쪽으로 shift한 값을 저장할 또 다른 크기가 16인 char 배열 선언
	
    	for(int i = 15; i >= 0 ; i--) {
    		if((data&(1<<i))==0)
    			arr[15-i]= '0';
    		else
    			arr[15-i]='1';
    		}
	    			//넘겨받은 10진수의 AC의 값을 2진수인 형태로 변환해주는 for문
					//이 변환된 2진수는 arr배열에 저장된다 
    	
    	int f1 = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);

    	for(int i = 0; i < 16 ; i ++) {
    		if(i==0) {
    			if(f1==0)
    			{    f1 =48;
    				a[i]= (char)f1;
    			}
    			else {
    				f1 =49;
    				a[i]= (char)f1;
    			}
    	
    			}
    			
    	
    		else if(i==15) {
    			
    			a[i]=arr[i-1];
    			CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, Character.getNumericValue(arr[i]));
    			
    		}
    		else {
    			a[i]=arr[i-1];
    		}
    		
    	}
        String binary = String.valueOf(a);
        int data1 = Integer.parseInt(binary,2);
        CPUEngine.getInstance().setRegisterData(RegisterType.AC, data1);
        
       //int ac = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	//System.out.println(ac);

    }

    private void executeCIL() {
    	//CPUEngine.getInstance().setRegisterData(RegisterType.AC, 34);
    	
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC); 
			//AC의 값을 받아와 data에 저장

    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	    //받아온 AC의 값을 2진수 형태로 저장할 크기가 16인 char배열 선언
    	
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	    //위의 2진수 형태로 저장된 배열을 왼쪽으로 shift한 값을 저장할 또 다른 크기가 16인 char 배열 선언
    	
		for(int i = 15; i >= 0 ; i--) {
			if((data&(1<<i))==0)
				arr[15-i]= '0';
			else
				arr[15-i]='1';
		}
    	    //넘겨받은 10진수의 AC의 값을 2진수인 형태로 변환해주는 for문
			//이 변환된 2진수는 arr배열에 저장된다
		
    		int f1 = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);
    					//E 플립플롭에 있는 값을 f1 변수에 저장한다.

    		for(int i = 0; i < 16 ; i ++) {	
    			if(i==0) { //
    				a[i]=arr[i+1];
    				CPUEngine.getInstance().setFlipFlopData(FlipFlopType.E, Character.getNumericValue(arr[i]));
    			}
    			else if(i==15) {

    				if(f1==0)
    				{    f1 =48; 
    					a[i]= (char)f1;
    				}
    				else {
    					f1 =49;
    					a[i]= (char)f1;
    					}
    									//E에서 받아온 값은 int형이고 저장할 배열은 char형이므로 E에서 받아온 값에 따라 
									//값을 아스키코드로 변경 후 char형인 '0'과 '1'을 각각 a[15]에 넣어준다.
    					}
	
    			else {
    				a[i]=arr[i+1]; //shl(AC)의 과정
    				}

    			}
    		String binary = String.valueOf(a); //shift된 2진수 형태의 배열 a를 string형으로 변환해주고
    		int data1 = Integer.parseInt(binary,2); //변환된 string형태의 2진수를 다시 10진수로 변환해준다
    		CPUEngine.getInstance().setRegisterData(RegisterType.AC, data1);//그리고 이 변환된 10진수를 다시 AC의 값으로 set 시킨다.
    		
    		//int ac = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
        	//System.out.println(ac);

    			}

    private void executeINC() {
    	//CPUEngine.getInstance().setRegisterData(RegisterType.AC, 34);
    	
    	int data = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	data = data+1;
    	CPUEngine.getInstance().setRegisterData(RegisterType.AC, data);
    	
    	//int a = CPUEngine.getInstance().getRegisterData(RegisterType.AC);
    	//System.out.println(a);

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