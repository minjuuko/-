package com.jnuhw.bcfirst.domain;

import com.jnuhw.bcfirst.domain.Cpu.RegisterType;

public class Utility {

    public static String toFormatBinaryString(int data) {
        return String.format("%16s", Integer.toBinaryString(data)).replace(' ', '0');
    }

    public static String toFormatBinaryString(RegisterType registerType, int data) {
        String format = "%" + registerType.getBitSize() + "s";
        return String.format(format, Integer.toBinaryString(data)).replace(' ', '0');
    }

    public static String toFormatBinaryString(int formatSize, int data) {
        String format = "%" + formatSize + "s";
        return String.format(format, Integer.toBinaryString(data)).replace(' ', '0');
    }
}
