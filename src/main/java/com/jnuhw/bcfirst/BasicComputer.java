package com.jnuhw.bcfirst;

import com.jnuhw.bcfirst.background.BusSystem;
import com.jnuhw.bcfirst.background.SymbolTable;

import java.util.*;

public class BasicComputer {

    private List<String> PROGRAM = new ArrayList<>();
    private BusSystem busSystem;

    // Main Method.
    public static void main(String[] args) {

        String[] programA = new String[]{
                "ORG 0",
                "LDA A",
                "ADD B",
                "STA C",
                "HLT",
                "A, DEC 83",
                "B, DEC -63",
                "C, DEC 0",
               // "XNA",
                "END"
        }; // 6-5 Code

        BasicComputer bc = new BasicComputer();
        bc.insertCode(programA); // 6-5 코드를 basic Computer에 입력.

        try {
            bc.compile(); // 컴파일
            bc.execute(); // 실행
        } catch (UnknownInstructionException exception) {
            System.out.println("프로그램이 실행 중 오류로 인해 종료되었습니다.");
        }

        // 정상적으로 프로그램이 실행 됬는지, Memory 주소 006 확인
            bc.printMemory(0x0000);
            bc.printMemory(0x0001);
            bc.printMemory(0x0002);
            bc.printMemory(0x0003);
            bc.printMemory(0x0004);
            bc.printMemory(0x0005);
        bc.printMemory(0x0006);

    }

    /*

                BASIC COMPUTER CLASS


     */


    public BasicComputer() {
        busSystem = new BusSystem(); // BUS System 초기값으로 설정
    }


    /*
        void printMemory
        args:
            key : M[key]
        description : M[Key]에 저장된 데이터를 출력한다.
     */
    public void printMemory(int key) {
        System.out.println("M[" + key + "] 의 데이터는 " + busSystem.getMemoryData(key));
    }


    /*
        void insertCode
        args:
            codes : ORG ~ END로 구설된 String list
        description : basic Computer에 코드를 입력시켜주는 Metohd
     */
    public void insertCode(String[] codes) {
        PROGRAM.addAll(Arrays.asList(codes));
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
                busSystem.insertMemory(address, 0, true); // 0은 아무 의미 없으며, popedData를 입력함
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


    /*
        void compile
        args:
        description: FIRST PASS와 SECOND PASS를 실행 한 후, byte code 형태로 변환된 코드를 Memory에 입력함.
     */
    public void compile() throws UnknownInstructionException{
        /*
                    First PASS
                    1. , (comma)가 있는 지 여부 확인
                    2. 있다면 Label 이므로, address symbol table에 저장 -> 1번
                    3. 없다면 ORG인지 확인
                    4. ORG라면 LC를 ORG다음의 숫자로 SET -> 1번
                    5. ORG가 아니라면 END인지 확인
                    6. 맞다면 Second PASS
                    7. 아니라면 LC++ -> 1번
         */

        List<SymbolTable> symbolTables = new ArrayList<>();
        int lc = 0;

        for(String code : PROGRAM) {
            if(code.contains(", ")) { // Symbol, LC, Value를 Table에 저장
                String[] datas = code.split(", ");
                String symbol = datas[0];
                String type = datas[1].split(" ")[0];
                int value = 0;
                if(type.equals("DEC")) {
                    value = Integer.parseInt(datas[1].split(" ")[1]);
                }

                symbolTables.add(new SymbolTable(symbol, lc, value));
            } else if (code.startsWith("ORG")) { // ORG -> LC를 ORG 뒤의 넘버로 SET
                int setLC = Integer.parseInt(code.split(" ")[1]);
                lc = setLC;
                continue;
            } else if (code.startsWith("END")) { // END -> Second PASS로 이동
                break;
            }
            lc++;
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
        lc = 0;
        List<Integer> compiled = new ArrayList<>();

        for(String code : PROGRAM) { // SECOND PASS 실행
            String cmd = code.split(" ")[0].toUpperCase(Locale.ROOT);
            int hexCode = 0x0000;

            if(code.contains("ORG") || code.contains("END") || code.contains("DEC") || code.contains("HEX")) { // Pseudo-Instruction 인가?

                if (cmd.equals("ORG")) { // ORG 뒤의 숫자로 LC 를 SET
                    int setLC = Integer.parseInt(code.split(" ")[1]);
                    lc = setLC;
                    continue;
                } else if (cmd.equals("END")) { // END 일시 Second Pass 종료
                    break;
                } else { // DEC, HEX 일시 데이터 입력
                    for (SymbolTable symbolTable : symbolTables) {
                        if (symbolTable.getLocCount() == lc) {
                            hexCode = symbolTable.getData();
                            break;
                        }
                    }
                }

            } else { // Pseudo-Instruction이 아니고,

                int mriCode = isMRI(cmd); // MRI Code인지 확인.

                if (mriCode > 0) { // 앞의 문자가 MRI 일 때, 명령어코드 + 심볼값 저장.
                    String[] data = code.split(" ");
                    String symbol = data.length >= 2 ? data[1] : ""; // 코드의 스페이스 뒷 부분을 Symbol로 Set

                    if(!symbol.equals("")) {
                        for (SymbolTable symbolTable : symbolTables) { // SymbolTable에서 일치하는 Symbol을 찾고, Table의 LC값 + 명령어 를 주소에 저장.
                            if (symbolTable.getSymbol().equals(symbol)) {
                                mriCode += symbolTable.getLocCount();
                            }
                        }
                    }

                    hexCode = mriCode;
                } else { // MRI코드가 아닐 때,
                    int nonMRICode = isNonMRI(cmd); // NON-MRI Code 인지 확인.

                    if (nonMRICode > 0) {
                        String[] data = code.split(" ");
                        String symbol = data.length >= 2 ? data[1] : ""; // 코드의 스페이스 뒷 부분을 Symbol로 Set

                        if(!symbol.equals("")) {
                            for (SymbolTable symbolTable : symbolTables) { // SymbolTable에서 일치하는 Symbol을 찾고, Table의 LC값 + 명령어 를 주소에 저장.
                                if (symbolTable.getSymbol().equals(symbol)) {
                                    nonMRICode += symbolTable.getLocCount();
                                }
                            }
                        }

                        hexCode = nonMRICode;
                    } else {
                        System.out.println("Line " + lc + " 에서 알 수 없는 Instruction을 발견 강제 종료 합니다. \n문제가 된 Instruction: { " + cmd + " } || 전체 문장 : " + code + " \n");
                        throw new UnknownInstructionException();
                    }
                }
            }
            compiled.add(hexCode);

            lc++;
        }

        // System.out.println("Program Compiled Result :");
        for(Integer code : compiled) {
            // String hexString = Integer.toHexString(code);
            // System.out.println(hexString.length() > 4 ? hexString.substring(4) : hexString);
            busSystem.insertMemory(code);
        }
    }


    /*
        int isMRI
        args:
            cmd : ORG, END등의 명령어 부분을 받아옴.
        description : Memory-Referenced Instruction 인지 확인함.
     */
    private int isMRI(String cmd) {
        switch(cmd.toUpperCase(Locale.ROOT)) {
            case "AND": // AND memory word to AC
                return 0x0000;
            case "ADD": // ADD memory word to AC
                return 0x1000;
            case "LDA": // LOAD memory word to AC
                return 0x2000;
            case "STA": // Store content of AC in memory
                return 0x3000;
            case "BUN": // Branch unconditionally
                return 0x4000;
            case "BSA": // Branch and save return address
                return 0x5000;
            case "ISZ": // Increment and skip if zero
                return 0x6000;
        }
        return -1;
    }


    /*
        int isNonMRI
        args:
            cmd : ORG, END등의 명령어 부분을 받아옴.
        description : Non Memory-Referenced Instruction 인지 확인함.
     */
    private int isNonMRI(String cmd) {
        switch(cmd.toUpperCase(Locale.ROOT)){
            case "CLA": // Clear AC
                return 0x7800;
            case "CLE": // Clear E
                return 0x7400;
            case "CMA": // Complement AC
                return 0x7200;
            case "CME": // Complement E
                return 0x7100;
            case "CIR": // Circulate Right AC and E
                return 0x7080;
            case "CIL": // Circulate Left AC and E
                return 0x7040;
            case "INC": // Increment AC
                return 0x7020;
            case "SPA": // Skip next instruction if AC positive
                return 0x7010;
            case "SNA": // Skip next instruction if AC negative
                return 0x7008;
            case "SZA": // Skip next instruction if AC zero
                return 0x7004;
            case "SZE": // Skip next instruction if E zero
                return 0x7002;
            case "HLT": // Halt Computer
                return 0x7001;
            case "INP": // Input Character to AC
                return 0xF800;
            case "OUT": // Output Character from AC
                return 0xF400;
            case "SKI": // Skip on input flag
                return 0xF200;
            case "SKO": // Skip on output flag
                return 0xF100;
            case "ION": // Interrupt On
                return 0xF080;
            case "IOF": // Interrupt Off
                return 0xF040;
        }
        return -1;
    }


}
