package main.java.factory.service;

import service.CartService;
import service.serviceImpl.CartServiceImpl;

public class CartServiceFactory {

    private static CartService instance;

    private CartServiceFactory() {
    }

    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new CartServiceImpl() {
            };
        }
        return instance;
    }
}
