package main.java.daoJDBC.impl;

import dao.daoJDBC.CartDao;
import model.Cart;
import model.Product;
import model.User;
import org.apache.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class CartDaoImpl implements CartDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addCart(Cart value) {

        String sql = String.format("INSERT INTO cart(idUser) VALUES('%s')",
                                    value.getUser().getUserID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            LOGGER.info(value + " added to db");
        } catch (SQLException e) {
            LOGGER.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public Optional<Cart> getCart(User value) {

        Optional<Cart> cart = Optional.empty();
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT idUser, cart.idCart FROM cart " +
                     "WHERE cart.idUser = '" + value.getUserID() + "'";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                cart = Optional.of(new Cart(resultSet.getLong("idCart"), products, value));
            }
            sql = "SELECT product.idProduct, name, price, description FROM cart " +
                    "INNER JOIN product_cart ON product_cart.idCart = cart.idCart " +
                    "INNER JOIN product ON product.idProduct = product_cart.idProduct " +
                    "WHERE cart.idUser = '" + value.getUserID() + "'";
            try (Connection nextConnection = DBConnection.getConnection();
                 Statement nextStatement = nextConnection.createStatement();
                 ResultSet nextResultSet = nextStatement.executeQuery(sql)) {
                while (nextResultSet.next()) {
                    Product product = new Product(nextResultSet.getLong("idProduct"),
                                                  nextResultSet.getString("name"),
                                                  nextResultSet.getString("description"),
                                                  nextResultSet.getDouble("price"));
                    products.add(product);
                }
                if(cart.isPresent()) {
                    cart.get().setProducts(products);
                }
            } catch (SQLException e) {
                LOGGER.error("SQl exception " + e.getMessage());
                return Optional.empty();
            }
        }catch (SQLException e) {
            LOGGER.error("SQl exception " + e.getMessage());
            return Optional.empty();
        }
        return cart;
    }

    @Override
    public void addProductToCart(Cart cart, Product product) {

        String sql = String.format("INSERT INTO product_cart (idCart, idProduct) VALUES('%s', '%s')",
                                     cart.getCartID(), product.getProductID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            LOGGER.info(product + " added to product_count table");
        } catch (SQLException e) {
            LOGGER.error("SQl exception " + e.getMessage());
        }
    }
}
