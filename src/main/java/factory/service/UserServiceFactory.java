package main.java.factory.service;

import service.UserService;
import service.serviceImpl.UserServiceImpl;

public class UserServiceFactory {

    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceFactory() {
    }
}
