package com.bdd.traintimetables;

public class UnknownLineException extends RuntimeException{
    public UnknownLineException(String message) { super(message);}
}