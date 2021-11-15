package com.jnuhw.bcfirst.controller;

import com.jnuhw.bcfirst.domain.assembler.Executor;
import com.jnuhw.bcfirst.exception.UnknownInstructionException;
import com.jnuhw.bcfirst.domain.assembler.Parser;
import com.jnuhw.bcfirst.view.InputView;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.List;

public class AssemblerController {

    Parser parser = new Parser();
    Executor executor = new Executor();

    public void run(List<String> program) {
        try {
            int startLc = InputView.inputStartLc();

            parser.parseFirstPass(program);
            parser.parseSecondPass(program);

            OutputView.saveMemoryData();
            executor.execute(startLc);
            OutputView.printResultView();
        } catch (UnknownInstructionException exception) {
            exception.printStackTrace();
            OutputView.printErrorAnnouncement();
        }
    }
}