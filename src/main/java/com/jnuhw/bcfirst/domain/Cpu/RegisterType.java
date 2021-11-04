package com.jnuhw.bcfirst.domain.Cpu;

public enum RegisterType {
    AR(12),
    PC(12),
    DR(16),
    AC(16),
    IR(16),
    TR(16),
    OUTR(8),
    INPR(8);

    int bitSize;

    RegisterType(int size) {
        bitSize = size;
    }
}
