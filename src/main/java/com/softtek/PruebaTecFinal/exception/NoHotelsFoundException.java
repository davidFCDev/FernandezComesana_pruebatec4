package com.softtek.PruebaTecFinal.exception;

public class NoHotelsFoundException extends RuntimeException {
    public NoHotelsFoundException(String message) {
        super(message);
    }
}
