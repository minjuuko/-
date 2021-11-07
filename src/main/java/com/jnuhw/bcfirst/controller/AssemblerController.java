package com.jnuhw.bcfirst.controller;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.domain.cpu.CPUEngine;
import com.jnuhw.bcfirst.domain.assembler.Parser;
import com.jnuhw.bcfirst.domain.assembler.Executor;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.List;

public class AssemblerController {

    Parser parser = new Parser();
    Executor executor = Executor.getInstance();

    public void run(List<String> program) {
        try {
            parser.parseFirstPass(program);
            parser.parseSecondPass(program);

            OutputView.saveMemoryData();
            int startLc = parser.getStartLc(program.get(0));
            executor.execute(startLc);
            OutputView.printResultView();
        } catch (UnknownInstructionException exception) {
            exception.printStackTrace();
            OutputView.printErrorAnnouncement();
        }


        // (디버깅용) 정상적으로 프로그램이 실행 됬는지, Memory 주소 006 확인
//        printMemoryByAddress(0x0000);
//        printMemoryByAddress(0x0001);
//        printMemoryByAddress(0x0002);
//        printMemoryByAddress(0x0003);
//        printMemoryByAddress(0x0004);
//        printMemoryByAddress(0x0005);
//        printMemoryByAddress(0x0006);
//        printMemoryByAddress(0x0007);
        //
    }

    public void printMemoryByAddress(int address) {
        OutputView.printMemory(address, CPUEngine.getInstance().getMemoryData(address));
    }
}
