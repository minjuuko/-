package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.background.BusSystem;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.*;

public class Assembler {

    private LcCounter lcCounter = new LcCounter();
    private List<Label> addressLabelTable = new ArrayList<>();
    // private List<Integer> binaryInstruction = new ArrayList<>();

    // private int startLC;
    // private HashMap<Integer, List<Integer>> instructionsMap = new HashMap<>();
    private List<Integer> startLcList = new ArrayList<>();

    // 임시

    public void parseFirstPass(List<String> program) throws UnknownInstructionException {
        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String instruction = args.get(0);

            if (isLabelInstruction(instruction)) {
                addSymbolTable(args);
            }

            if (instruction.equals("ORG")) {
                executeORG(Integer.parseInt(args.get(1)));
                continue;
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
        if (command.contains(",")) {
            return true;
        }

        return false;
    }

    private void addSymbolTable(List<String> args) {
        String label = args.get(0);
        label = label.substring(0, label.length() - 1);
        String data = args.get(2);

        addressLabelTable.add(new Label(label, lcCounter.getCurrentLc(), args.get(1).equals("HEX") ? Integer.parseInt(data, 16) : Integer.parseInt(data)));
    }


    private void executeORG(int location) {
        startLcList.add(location);
        lcCounter.setLc(location);
    }

    public void parseSecondPass(List<String> program) throws UnknownInstructionException {
        lcCounter.resetLc();

        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String instruction = isLabelInstruction(args.get(0)) ? args.get(1) : args.get(0);

            if (isPseudoInstruction(instruction)) {
                executePseudoInstruction(args);
                if (instruction.equals("ORG")) continue;
            } else {
                try {
                    executeNonPseudoInstruction(args);
                } catch (IllegalArgumentException e) {
                    OutputView.printUnknownInstructionError(lcCounter.getCurrentLc(), command);
                    throw new UnknownInstructionException();
                }
            }

            lcCounter.increaseLc();
        }
    }

    private void executePseudoInstruction(List<String> args) {
        String instruction = args.get(0);

        switch (instruction) {
            case "END":
                return;
            case "ORG":
                executeORG(Integer.parseInt(args.get(1)));
                return;

        }

        if (isLabelInstruction(instruction)) {
            String labelName = args.get(0);
            Label label = getLabelByName(labelName);
            // @deprecated
            BusSystem.getInstance().setMemoryData(lcCounter.getCurrentLc(), label.getData());
        }
    }

    private void executeNonPseudoInstruction(List<String> args) throws IllegalArgumentException {
        Instruction instruction = Instruction.valueOf(args.get(0));
        int instructionHexCode = instruction.getHexaCode();
        if (args.size() > 1) {
            String labelName = args.get(1);
            instructionHexCode += getLabelByName(labelName).getLc();
        }
        if (args.get(args.size() - 1).equals("I")) {
            instructionHexCode += 0x8000;
        }

        // @deprecated
        BusSystem.getInstance().setMemoryData(lcCounter.getCurrentLc(), instructionHexCode);
    }

    private Label getLabelByName(String name) {
        return addressLabelTable.stream()
                .filter(l -> l.getName().equals(name))
                .findAny().get();
    }
}
