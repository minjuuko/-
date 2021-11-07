package com.jnuhw.bcfirst.domain;

public class Utility {

    public static String toFormatBinaryString(int data) {
        return String.format("%16s", Integer.toBinaryString(data)).replace(' ', '0');
    }
}
