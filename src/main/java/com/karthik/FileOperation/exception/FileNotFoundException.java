package com.karthik.FileOperation.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Long id) {
        super("File not found  for ID : " + id);
    }

    public FileNotFoundException(String message) {
        super("Message :"+message);
    }
}
