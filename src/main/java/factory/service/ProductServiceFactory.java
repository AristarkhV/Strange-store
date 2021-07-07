package main.java.factory.service;

import service.ProductService;
import service.serviceImpl.ProductServiceImpl;

public class ProductServiceFactory {

    private static ProductService instance;

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }

    private ProductServiceFactory() {
    }
}
