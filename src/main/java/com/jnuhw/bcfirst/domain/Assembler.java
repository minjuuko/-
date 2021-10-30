package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.UnknownInstructionException;
import com.jnuhw.bcfirst.background.BusSystem;
import com.jnuhw.bcfirst.view.OutputView;

import java.util.*;

public class Assembler {

    private LcCounter lcCounter = new LcCounter();
    private List<Label> addressLabelTable = new ArrayList<>();
    private List<Integer> binaryInstruction = new ArrayList<>();

    private int startLC;
    private HashMap<Integer, List<Integer>> instructionsMap = new HashMap<>();

    // 임시
    private BusSystem busSystem = new BusSystem();

    /**
     * Symbol 을 선언하는 명령어들을 읽어서, Symbol 객체 생성
     * @param program
     */
    public void parseFirstPass(List<String> program) throws UnknownInstructionException {
        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String symbol = args.get(0);

            if (isLabelCommand(symbol)) {
                addSymbolTable(args);
            }

            if(symbol.equals("ORG"))
                executeORG(Integer.parseInt(args.get(1)));

            lcCounter.increaseLc();
        }
    }

    private boolean isPseudoCommand(String command) {
        switch(command.toUpperCase(Locale.ROOT)) {
            case "ORG":
            case "END":
            case "DEC":
            case "HEX":
                return true;

            default:
                return false;
        }
    }

    private boolean isLabelCommand(String command) {
        if (command.contains(",")) {
            return true;
        }

        return false;
    }

    private void addSymbolTable(List<String> args) {
        String label = args.get(0);
        label = label.substring(0, label.length()-1);
        String data = args.get(2);

        addressLabelTable.add(new Label(label, lcCounter.getCurrentLc(), args.get(1).equals("HEX") ? Integer.parseInt(data, 16) : Integer.parseInt(data)));
    }


    private void executeORG(int location) {
        startLC = location;
        lcCounter.setLc(location);
    }

    public void parseSecondPass(List<String> program) throws UnknownInstructionException {
        lcCounter.resetLc();

        for (String command : program) {
            List<String> args = Arrays.asList(command.split(" "));
            String symbol = isLabelCommand(args.get(0)) ? args.get(1) : args.get(0);

            if (isPseudoCommand(symbol)) {
                executePseudoCommand(args);
            } else {
                try {
                    executeNonPseudoCommand(args);
                } catch (IllegalArgumentException e) {
                    OutputView.printUnknownInstructionError(lcCounter.getCurrentLc(), command);
                    throw new UnknownInstructionException();
                }
            }

            lcCounter.increaseLc();
        }
    }

    private void executePseudoCommand(List<String> args) {
        String symbol = args.get(0);

        switch(symbol) {
            case "END":
                instructionsMap.put(startLC, binaryInstruction);
                binaryInstruction = new ArrayList<>();
                return;
            case "ORG":
                executeORG(Integer.parseInt(args.get(1)));
                return;

        }

        if(isLabelCommand(symbol)) {
            Label label = getLabelByCurrentLc();
            busSystem.setMemoryData(lcCounter.getCurrentLc(), label.getData());
        }
    }

    private Label getLabelByCurrentLc() {
        return addressLabelTable.stream()
                .filter(l -> l.getLc() == lcCounter.getCurrentLc())
                .findAny().get();
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
        if (args.get(args.size()-1).equals("I")) {
            instructionHexCode += 0x8000;
        }

        binaryInstruction.add(instructionHexCode);
    }
}
