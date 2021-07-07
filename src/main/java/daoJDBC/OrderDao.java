package main.java.daoJDBC;

import model.Order;
import model.User;

import java.util.Optional;

public interface OrderDao {

    void addOrder(Order value, User user);

    Optional<Order> getUserOrder(User value);
}
