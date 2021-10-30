package com.jnuhw.bcfirst.domain;

public enum Instruction {

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

    private int hexaCode;
    private boolean isMri;
    private boolean isInDirect;

    Instruction(int hexaCode, boolean isMri) {
        this.hexaCode = hexaCode;
        this.isMri = isMri;
        this.isInDirect = false;
    }

    public void setIsInDirect(boolean isInDirect) {
        this.isInDirect = isInDirect;
    }

    public int getHexaCode() {
        if (isInDirect) {
            return hexaCode + 0x8000;
        }

        return hexaCode;
    }

    public boolean isMri() {
        return isMri;
    }
}
