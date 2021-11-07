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
    				//AC�� ���� �޾ƿ� data�� ����
    	
    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
	    //�޾ƿ� AC�� ���� 2���� ���·� ������ ũ�Ⱑ 16�� char�迭 ����
	
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
	    //���� 2���� ���·� ����� �迭�� �������� shift�� ���� ������ �� �ٸ� ũ�Ⱑ 16�� char �迭 ����
	
    	for(int i = 15; i >= 0 ; i--) {
    		if((data&(1<<i))==0)
    			arr[15-i]= '0';
    		else
    			arr[15-i]='1';
    		}
	    			//�Ѱܹ��� 10������ AC�� ���� 2������ ���·� ��ȯ���ִ� for��
					//�� ��ȯ�� 2������ arr�迭�� ����ȴ� 
    	
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
			//AC�� ���� �޾ƿ� data�� ����

    	char arr[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	    //�޾ƿ� AC�� ���� 2���� ���·� ������ ũ�Ⱑ 16�� char�迭 ����
    	
    	char a[]= {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};
    	    //���� 2���� ���·� ����� �迭�� �������� shift�� ���� ������ �� �ٸ� ũ�Ⱑ 16�� char �迭 ����
    	
		for(int i = 15; i >= 0 ; i--) {
			if((data&(1<<i))==0)
				arr[15-i]= '0';
			else
				arr[15-i]='1';
		}
    	    //�Ѱܹ��� 10������ AC�� ���� 2������ ���·� ��ȯ���ִ� for��
			//�� ��ȯ�� 2������ arr�迭�� ����ȴ�
		
    		int f1 = CPUEngine.getInstance().getFlipFlopData(FlipFlopType.E);
    					//E �ø��÷ӿ� �ִ� ���� f1 ������ �����Ѵ�.

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
    									//E���� �޾ƿ� ���� int���̰� ������ �迭�� char���̹Ƿ� E���� �޾ƿ� ���� ���� 
									//���� �ƽ�Ű�ڵ�� ���� �� char���� '0'�� '1'�� ���� a[15]�� �־��ش�.
    					}
	
    			else {
    				a[i]=arr[i+1]; //shl(AC)�� ����
    				}

    			}
    		String binary = String.valueOf(a); //shift�� 2���� ������ �迭 a�� string������ ��ȯ���ְ�
    		int data1 = Integer.parseInt(binary,2); //��ȯ�� string������ 2������ �ٽ� 10������ ��ȯ���ش�
    		CPUEngine.getInstance().setRegisterData(RegisterType.AC, data1);//�׸��� �� ��ȯ�� 10������ �ٽ� AC�� ������ set ��Ų��.
    		
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