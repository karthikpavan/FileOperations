package com.karthik.FileOperation.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Post not found  for ID : " + id);
    }

    public PostNotFoundException(String message) {
        super("Message :"+message);
    }
}
