package main.java.factory.service;

import service.CodeService;
import service.serviceImpl.CodeServiceImpl;

public class CodeServiceFatory {

    private static CodeService instance;

    private CodeServiceFatory() {
    }

    public static synchronized CodeService getInstance() {
        if (instance == null) {
            instance = new CodeServiceImpl() {
            };
        }
        return instance;
    }
}
