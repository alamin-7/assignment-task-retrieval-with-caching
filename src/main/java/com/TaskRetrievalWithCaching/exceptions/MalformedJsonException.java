package com.TaskRetrievalWithCaching.exceptions;

public class MalformedJsonException extends RuntimeException{

    public MalformedJsonException(String message){
        super(message);
    }
}
