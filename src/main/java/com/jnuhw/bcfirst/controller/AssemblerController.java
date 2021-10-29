package com.jnuhw.bcfirst.controller;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.background.BusSystem;
import com.jnuhw.bcfirst.domain.Assembler;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.List;

public class AssemblerController {

    Assembler assembler = new Assembler();
    BusSystem busSystem = new BusSystem();

    /*
        void compile
        args:
        description: FIRST PASS와 SECOND PASS를 실행 한 후, byte command 형태로 변환된 코드를 Memory에 입력함.
     */
    public void run(List<String> program) {
        try {
            assembler.parseFirstPass(program);
            assembler.parseSecondPass(program);
        } catch (UnknownInstructionException exception) {
            OutputView.printErrorAnnouncement();
        }


        // (디버깅용) 정상적으로 프로그램이 실행 됬는지, Memory 주소 006 확인
        printMemoryByAddress(0x0000);
        printMemoryByAddress(0x0001);
        printMemoryByAddress(0x0002);
        printMemoryByAddress(0x0003);
        printMemoryByAddress(0x0004);
        printMemoryByAddress(0x0005);
        printMemoryByAddress(0x0006);
        //
    }

        /*
        void execute
        args:
        description : compile() 에서 Memory에 입력된 Instruction들을 실행한다.
     */
    public void execute() {
        boolean processing = true; // processing이 false가 될 때까지 무한으로 반복한다.

        // System.out.println("Code Execute");
        while(processing) {
            int pc = busSystem.getOutData(BusSystem.RegisterType.PC); // PC 레지스터의 데이터
            int data = busSystem.getMemoryData(pc); // M[PC]의 데이터 ( Instruction )

            int address, memData; // Address는 MRI에서 가리키는 Address 데이터, memData는 bus에 flow된 데이터이다.

            // MRI Commands
            if(0x0000 <= data && data <= 0x0FFF) { // AND Command
                address = data;
                busSystem.popData(address);
                busSystem.setData(BusSystem.RegisterType.DR);
                busSystem.useAdder(BusSystem.CalculationType.AND, true, true, false);
            } else if (0x1000 <= data && data <= 0x1FFF) { // ADD Command
                address = data - 0x1000;
                busSystem.popData(address);
                busSystem.setData(BusSystem.RegisterType.DR);
                busSystem.useAdder(BusSystem.CalculationType.ADD, true, true, false);
            } else if (0x2000 <= data && data <= 0x2FFF) { // LDA Command
                address = data - 0x2000;
                busSystem.popData(address);
                busSystem.setData(BusSystem.RegisterType.AC);
            } else if (0x3000 <= data && data <= 0x3FFF) { // STA Command
                address = data - 0x3000;
                busSystem.popData(BusSystem.RegisterType.AC);
//                busSystem.insertMemory(address, 0, true); // 0은 아무 의미 없으며, popedData를 입력함
            } else if (0x4000 <= data && data <= 0x4FFF) { // BUN Command
                // Not use
            } else if (0x5000 <= data && data <= 0x5FFF) { // BSA Command
                // Not use
            } else if (0x6000 <= data && data <= 0x6FFF) { // ISZ Command
                // Not use
            }

            switch (data) { // Non-MRI Commands
                case 0x7001:
                    // System.out.println("Code End");
                    processing = false;
                    break;

            }
            busSystem.increaseRegister(BusSystem.RegisterType.PC);
        }
    }

    public void printMemoryByAddress(int address) {
        OutputView.printMemory(address, busSystem.getMemoryData(address));
    }
}
