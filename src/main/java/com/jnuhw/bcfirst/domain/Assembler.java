package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.background.Label;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assembler {

    private LcCounter lcCounter = new LcCounter();
    private List<Label> addressLabelTable = new ArrayList<>();
    private List<Integer> binaryInstruction = new ArrayList<>();

    /**
     * Symbol 을 선언하는 명령어들을 읽어서, Symbol 객체 생성
     * @param program
     */
    public void parseFirstPass(List<String> program) throws UnknownInstructionException {
        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String symbol = args.get(0);
            if (!isPseudoCommand(symbol) && !isLabelCommand(symbol)) {
                continue;
            }

            if (isLabelCommand(symbol)) {
                executeDEC(args);
            }

            String arg = args.get(1);
            if (symbol.equals("ORG")) {
                executeORG(Integer.parseInt(arg));

            } else if (symbol.equals("END")) {
                break;
            }

            lcCounter.increaseLc();
        }
    }

    private boolean isPseudoCommand(String command) {
        if (command.equals("ORG") || command.equals("END") || command.equals("DEC") || command.equals("HEX")) {
            return true;
        }

        return false;
    }

    private boolean isLabelCommand(String command) {
        if (command.contains(",")) {
            return true;
        }

        return false;
    }

    private void executeDEC(List<String> args) {
        String label = args.get(0);
        int data = Integer.parseInt(args.get(2));

        addressLabelTable.add(new Label(label, lcCounter.getLc(), data));
    }

    private void executeORG(int location) {
        lcCounter.setLc(location);
    }

    public void parseSecondPass(List<String> program) throws UnknownInstructionException {
        lcCounter.resetLc();

        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String symbol = args.get(0);

            if (isPseudoCommand(symbol)) {
                executePseudoCommand(args);
                return;
            } else {
                try {
                    executeNonPseudoCommand(args);
                } catch (IllegalArgumentException e) {
                    OutputView.printUnknownInstructionError(lcCounter.getLc(), command);
                    throw new UnknownInstructionException();
                }
            }

            lcCounter.increaseLc();
        }
    }

    private void executePseudoCommand(List<String> args) {
        String symbol = args.get(0);
        if (symbol.equals("END")) {
            return;
        }

        int arg = Integer.parseInt(args.get(1));
        if (symbol.equals("ORG")) {
            executeORG(arg);
        } else {

            // DEC, HEX 일시 데이터 입력
            Label label = addressLabelTable.stream()
                    .filter(l -> l.getLc() == lcCounter.getLc())
                    .findAny().get();

            binaryInstruction.add(label.getData());
        }
//        else if (symbol.equals("DEC"))
//        } else if (symbol.equals("HEC")) {
//        }
// 함수 분리하기
// 2진수 변환 코드 삽입
    }

    private void executeNonPseudoCommand(List<String> args) throws IllegalArgumentException {
        String symbol = args.get(0);
        Instruction instruction = Instruction.valueOf(symbol);
        int instructionHexCode = instruction.getHexaCode();
        if (args.size() > 1) {
            String label = args.get(1);
            instructionHexCode += addressLabelTable.stream()
                    .filter(l -> l.getName().equals(label))
                    .findAny().get()
                    .getLc();
        }

        binaryInstruction.add(instructionHexCode);
    }
}
