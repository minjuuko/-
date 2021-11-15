package com.jnuhw.bcfirst.exception;

public class UnknownInstructionException extends RuntimeException {
    public UnknownInstructionException(String message){
        super(message);
    }
}
