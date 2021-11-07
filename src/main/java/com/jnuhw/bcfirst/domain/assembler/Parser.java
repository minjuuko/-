package com.jnuhw.bcfirst.domain.assembler;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.domain.cpu.CPUEngine;

import java.util.*;

public class Parser {

    private LcCounter lcCounter = new LcCounter();
    private List<Label> addressLabelTable = new ArrayList<>();

    public int getStartLc(String firstLine) {
        List<String> args = Arrays.asList(firstLine.split(" "));
        if (args.get(0).toLowerCase(Locale.ROOT).equals("ORG")) {
            return Integer.parseInt(args.get(1));
        }

        return 1;
    }

    public void parseFirstPass(List<String> program) throws UnknownInstructionException {
        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String instruction = args.get(0);
            if(isLabelInstruction(instruction) || instruction.equals("DEC") || instruction.equals("HEX")) {
                addSymbolTable(args);
            } else {

                if (instruction.equals("ORG")) {
                    executeORG(Integer.parseInt(args.get(1)));
                    continue;
                }
            }

            lcCounter.increaseLc();
        }
    }

    private boolean isPseudoInstruction(String instruction) {
        switch (instruction.toUpperCase(Locale.ROOT)) {
            case "ORG":
            case "END":
            case "DEC":
            case "HEX":
                return true;

            default:
                return false;
        }
    }

    private boolean isLabelInstruction(String command) {
        return command.contains(",");
    }

    private void addSymbolTable(List<String> args) {

        String label = null;
        String instruction = args.get(0);
        String data = args.get(1);

        if(isLabelInstruction(args.get(0))) {
            label = args.get(0);
            label = label.substring(0, label.length() - 1);
            instruction = args.get(1);
            data = args.get(2);
        }

        if (instruction.equals("HEX")) {
            addressLabelTable.add(new Label(label, lcCounter.getCurrentLc(), Integer.parseInt(data, 16)));
        } else if (instruction.equals("DEC")){
            addressLabelTable.add(new Label(label, lcCounter.getCurrentLc(), Integer.parseInt(data)));
        } else {
            addressLabelTable.add(new Label(label, lcCounter.getCurrentLc()));
        }

    }


    private void executeORG(int location) {
        lcCounter.setLc(location);
    }

    public void parseSecondPass(List<String> program) throws UnknownInstructionException {
        lcCounter.resetLc();

        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String instruction = args.get(0);
            boolean isLabeled = false;
            if(isLabelInstruction(instruction)){
                instruction = args.get(1);
                isLabeled = true;
            }

            if (isPseudoInstruction(instruction)) {
                executePseudoInstruction(args);
                if (instruction.equals("ORG")) continue;

            } else {
                try {
                    executeNonPseudoInstruction(args, isLabeled);
                } catch (IllegalArgumentException e) {
                    throw new UnknownInstructionException("알 수 없는 Instruction을 발견 : " + command);
                }
            }

            lcCounter.increaseLc();
        }
    }

    private void executePseudoInstruction(List<String> args) {
        String instruction = args.get(0);
        if(isLabelInstruction(instruction)) instruction = args.get(1);

        switch(instruction) {
            case "END":
                return;
            case "ORG":
                executeORG(Integer.parseInt(args.get(1)));
                return;
            case "DEC":
            case "HEX":
                executeLabelInstruction();
        }

    }

    private void executeLabelInstruction() {
        Label label = getLabelByLc(lcCounter.getCurrentLc());
        CPUEngine.getInstance().initializeMemoryData(lcCounter.getCurrentLc(), true, label.getData());
    }

    private void executeNonPseudoInstruction(List<String> args, boolean isLabeled) throws IllegalArgumentException {
        boolean isIndirect = false;
        if (args.size() >= 3 && args.get(args.size()-1).equals("I")) {
            isIndirect = true;
        }

        Instruction instruction = Instruction.valueOf(args.get(isLabeled ? 1 : 0));
        instruction.setIsInDirect(isIndirect);
        int instructionHexCode = instruction.getHexaCode();
        if (instruction.isMri()) {
            instructionHexCode += getLabelByName(args.get(isLabeled ? 2 : 1)).getLc();
        }

        CPUEngine.getInstance().initializeMemoryData(lcCounter.getCurrentLc(), false, instructionHexCode);
    }

    private Label getLabelByName(String name) {
        return addressLabelTable.stream()
                .filter(l -> l.getName() != null && l.getName().equals(name))
                .findAny().get();
    }

    private Label getLabelByLc(int lc) {
        return addressLabelTable.stream()
                .filter(l -> l.getLc() == lc)
                .findAny().get();
    }
}