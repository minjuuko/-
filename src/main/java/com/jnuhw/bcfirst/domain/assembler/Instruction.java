package com.jnuhw.bcfirst.domain.assembler;

/*
 * Instruction에 관한 정보를 정의하고
 * 이와 관련된 기능들을 정의하는 메소드를 가진 자료구조
 */

public enum Instruction {

    ETC(0, false),

    AND(0x0000, true),
    ADD(0x1000, true),
    LDA(0x2000, true),
    STA(0x3000, true),
    BUN(0x4000, true),
    BSA(0x5000, true),
    ISZ(0x6000, true),

    CLA(0x7800, false),
    CLE(0x7400, false),
    CMA(0x7200, false),
    CME(0x7100, false),
    CIR(0x7080, false),
    CIL(0x7040, false),
    INC(0x7020, false),
    SPA(0x7010, false),
    SNA(0x7008, false),
    SZA(0x7004, false),
    SZE(0x7002, false),
    HLT(0x7001, false),

    INP(0xF800, false),
    OUT(0xF400, false),
    SKI(0xF200, false),
    SKO(0xF100, false),
    ION(0xF080, false),
    IOF(0xF040, false);

    public static final int INDIRECT_CODE = 0x8000;

    private final int hexaCode;
    private final boolean isMri;

    Instruction(int hexaCode, boolean isMri) {
        this.hexaCode = hexaCode;
        this.isMri = isMri;
    }

    public int getHexaCode() {
        return hexaCode;
    }

    public boolean isMri() {
        return isMri;
    }

    public static boolean isMriHexCode(int hexaCode) {
        if (AND.hexaCode <= hexaCode && hexaCode < HLT.hexaCode) {
            return true;
        }

        if (AND.hexaCode + INDIRECT_CODE <= hexaCode && hexaCode < HLT.hexaCode + INDIRECT_CODE) {
            return true;
        }

        return false;
    }

    public static boolean isIndirectHexaCode(int hexaCode) {
        if (AND.hexaCode + INDIRECT_CODE <= hexaCode && hexaCode < HLT.hexaCode + INDIRECT_CODE) {
            return true;
        }

        return false;
    }

    /**
     * 데이터 주소가 섞여있는 MRI Instruction hexa code를 Instruction 판별용 hexa code로 변환함.
     * @param memoryHexaCode
     * @return MRI Instruction Hexa Code
     */
    public static int getInstructionHexaCodeFromMemoryHexaCode(int memoryHexaCode) {
        return memoryHexaCode - getDataHexaCodeFromMemoryHexaCode(memoryHexaCode);
    }

    /**
     * 데이터 주소가 섞여있는 MRI Instruction hexa code를 Instruction 판별용 hexa code로 변환함.
     * @param memoryHexaCode
     * @return MRI Instruction Hexa Code
     */
    public static int getDataHexaCodeFromMemoryHexaCode(int memoryHexaCode) {
        return memoryHexaCode % 0x1000;
    }
}
