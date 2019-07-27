package main.java.daoJDBC.impl;

import dao.daoJDBC.ProductDao;
import model.Product;
import org.apache.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<Product> getAll() {

        List<Product> productsList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM product ")) {
            while (resultSet.next()) {
                productsList.add(new Product(resultSet.getLong("idProduct"),
                                             resultSet.getString("name"),
                                             resultSet.getString("description"),
                                             resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
            return null;
        }
        return productsList;
    }

    @Override
    public void addProduct(Product value) {

        String sql = String.format("INSERT INTO product(name, description, price) VALUES('%s', '%s', '%s')",
                                    value.getName(), value.getDescription(), value.getPrice());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " added to db");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Product value) {

        String sql = "DELETE FROM product WHERE idProduct = '" + value.getProductID() + "'";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " deleted from db");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public void editProduct(Product value) {

        String sql = String.format("UPDATE product SET name = '%s', description = '%s', price = '%s' " +
                                   "WHERE idProduct = %d;",
                                    value.getName(), value.getDescription(),
                                    value.getPrice(), value.getProductID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " updated");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> getProductById(Long id) {

        Optional<Product> product = Optional.empty();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM product WHERE product.idProduct = " + id)) {
            if (resultSet.next()) {
                product = Optional.of(
                        new Product(resultSet.getLong("idProduct"), resultSet.getString("name"),
                                    resultSet.getString("description"),
                                    resultSet.getDouble("price")));
            }
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
            return Optional.empty();
        }
        return product;
    }
}
