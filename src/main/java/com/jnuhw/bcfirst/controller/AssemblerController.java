package com.jnuhw.bcfirst.controller;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.domain.assembler.Parser;
import com.jnuhw.bcfirst.domain.assembler.Executor;
import com.jnuhw.bcfirst.view.InputView;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.List;

public class AssemblerController {

    Parser parser = new Parser();
    Executor executor = Executor.getInstance();

    public void run(List<String> program) {
        try {
            int startLc = InputView.inputStartLc();

            parser.parseFirstPass(program);
            parser.parseSecondPass(program);

            OutputView.saveMemoryData();
//            int startLc = parser.getStartLc(program.get(0));
            executor.execute(startLc);
            OutputView.printResultView();
        } catch (UnknownInstructionException exception) {
            exception.printStackTrace();
            OutputView.printErrorAnnouncement();
        }
    }
}