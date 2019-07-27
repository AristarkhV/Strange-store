package main.java.factory.service;

import service.OrderService;
import service.serviceImpl.OrderServiceImpl;

public class OrderServiceFactory {

    private static OrderService instance;

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    private OrderServiceFactory() {
    }
}
