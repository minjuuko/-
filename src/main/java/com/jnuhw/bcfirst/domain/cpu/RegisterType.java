package com.jnuhw.bcfirst.domain.cpu;

public enum RegisterType {
    AR(12, false),
    PC(12, false),
    DR(16, true),
    AC(16, true),
    IR(16, false),
    TR(16, true),
    OUTR(8, false),
    INPR(8, false);

    private int bitSize;
    private boolean isSigned;

    RegisterType(int size, boolean isSigned) {
        this.bitSize = size;
        this.isSigned = isSigned;
    }

    public int getBitSize() {
        return bitSize;
    }

    public boolean isSigned() {
        return isSigned;
    }
}
