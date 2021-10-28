package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.background.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Assembler {

    private int lc = 0;
    private List<Label> addressLabelTable = new ArrayList<>();
    private List<Integer> binaryInstruction = new ArrayList<>();

    private void increaseLc() {
        lc++;
    }

    private void resetLc() {
        lc = 0;
    }


    /**
     * Symbol 을 선언하는 명령어들을 읽어서, Symbol 객체 생성
     *
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

            increaseLc();
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

        addressLabelTable.add(new Label(label, lc, data));
    }

    private void executeORG(int location) {
        lc = location;
    }


    /*
                Second PASS
                1. Pseudo-Instruction (ORG, END, DEC, HEX) 인지 확인.
                1-1. ORG 라면 LC를 ORG 뒤의 넘버로 SET
                1-2. END 라면 완료
                1-3. DEC나 HEX라면, LC의 장소에 데이터를 저장.

                2. MRI ( Memory Refernce Instruction ) 인지 확인. -> AND, ADD, LDA, STA, BUN, BSA, ISZ
                2-1 맞다면, 명령어를 분석해 2~4 bit 부분을 명령어로 정하고
                2-2 Symbol Table과 대응해서 5~16 bit 부분의 비트를 정하고
                2-3 Indirect/Direct 여부를확인해 1 bit 부분의 비트를 정해서 병합 후 해당 LC자리에 입력.

                3. NON-MRI 코드인지 확인.
                3-1 맞다면 매칭되는 바이너리 코드를 입력

                4. 아니라면, 해독할 수 없는 명령어 이므로 오류 출력.
         */
    public void parseSecondPass(List<String> program) throws UnknownInstructionException {
        resetLc();

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
                    System.out.println("Line " + lc + " 에서 알 수 없는 Instruction을 발견 강제 종료 합니다. \n문제가 된 Instruction: { " + command + " } || 전체 문장 : " + command + " \n");
                    throw new UnknownInstructionException();
                }
            }

            increaseLc();
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
            for (Label label : addressLabelTable) {
                if (label.getLc() == lc) {
                    binaryInstruction.add(label.getData());
                    break;
                }
            }
        }

//        if (symbol.equals("DEC"))
//        } else if (symbol.equals("HEC")) {
//        }
    }

    private void executeNonPseudoCommand(List<String> args) throws IllegalArgumentException {
        String symbol = args.get(0);
        Instruction instruction = Instruction.valueOf(symbol);
        int instructionHexCode = instruction.getHexaCode();
        if (args.size() > 1) {
            String labelName = args.get(1);
            // no getData ??
            instructionHexCode += addressLabelTable.stream().filter(label -> label.getName().equals(labelName)).collect(Collectors.toList()).get(0).getLc();
        }

        binaryInstruction.add(instructionHexCode);

    }
}
