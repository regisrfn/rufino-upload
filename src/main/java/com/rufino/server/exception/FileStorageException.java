package com.rufino.server.exception;

public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public FileStorageException(String message) {
        super(message);
    }
}