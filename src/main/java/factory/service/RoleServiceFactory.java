package main.java.factory.service;

import service.RoleService;
import service.serviceImpl.RoleServiceImpl;

public class RoleServiceFactory {

    private static RoleService instance;

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    private RoleServiceFactory() {
    }
}
