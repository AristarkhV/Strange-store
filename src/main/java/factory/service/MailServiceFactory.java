package main.java.factory.service;

import service.MailService;
import service.serviceImpl.MailServiceImpl;

public class MailServiceFactory {

    private static MailService instance;

    private MailServiceFactory() {
    }

    public static synchronized MailService getInstance() {
        if (instance == null) {
            instance = new MailServiceImpl();
        }
        return instance;
    }
}
