package main.java.daoJDBC.impl;

import dao.daoJDBC.UserDao;
import model.Role;
import model.User;
import org.apache.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> getAll() {

        List<User> userList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM user INNER JOIN role ON role.idRole = user.idRole ")) {
            while (resultSet.next()) {
                userList.add(new User(resultSet.getLong("idUser"),
                                      resultSet.getString("email"),
                                      resultSet.getString("password"),
                                      new Role(resultSet.getLong("idRole"),
                                               resultSet.getString("name"))));
            }
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
            return null;
        }
        return userList;
    }

    @Override
    public void addUser(User value) {

        String sql = String.format("INSERT INTO user(email, password, idRole) VALUES('%s', '%s', '%s')",
                                    value.getEmail(), value.getPassword(), value.getRole().getRoleID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " added to db");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(User value) {

        String sql = "DELETE FROM user WHERE idUser = '" + value.getUserID() + "'";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " deleted from db");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public void editUser(User value) {

        String sql = String.format("UPDATE user SET " +
                                "email = '%s', password = '%s', idRole = '%s' WHERE idUser = %d",
                                 value.getEmail(), value.getPassword(), value.getRole().getRoleID(), value.getUserID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info(value + " updated");
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        Optional<User> user = Optional.empty();
        String sql = "SELECT * FROM user INNER JOIN role ON role.idRole = user.idRole "
                   + "WHERE user.email = '" + email + "'";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if(resultSet.next()) {
                user = Optional.of(
                    new User(resultSet.getLong("idUser"),
                             resultSet.getString("email"),
                             resultSet.getString("password"),
                             new Role(resultSet.getLong("idRole"),
                                      resultSet.getString("name"))));
            }
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
            return Optional.empty();
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {

        Optional<User> user = Optional.empty();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM user INNER JOIN role ON role.idRole = user.idRole " +
                          "WHERE user.idUser = " + id)) {
            if(resultSet.next()) {
                user = Optional.of(
                        new User(resultSet.getLong("idUser"),
                                 resultSet.getString("email"),
                                 resultSet.getString("password"),
                                 new Role(resultSet.getLong("idRole"),
                                          resultSet.getString("name"))));
            }
        } catch (SQLException e) {
            logger.error("SQl exception " + e.getMessage());
            return Optional.empty();
        }
        return user;
    }
}
