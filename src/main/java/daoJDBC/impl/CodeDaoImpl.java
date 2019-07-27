package main.java.daoJDBC.impl;

import dao.daoJDBC.CodeDao;
import model.Code;
import org.apache.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class CodeDaoImpl implements CodeDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addCode(Code value) {

        String sql = String.format("INSERT INTO code(value, email, idOrder) " +
                                   "VALUES('%s', '%s', '%s')",
                                    value.getCode(), value.getEmail(),
                                    value.getOrderID());
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            LOGGER.info(value + " added to db");
        } catch (SQLException e) {
            LOGGER.error("SQl exception " + e.getMessage());
        }
    }

    @Override
    public Optional<Code> getCode(String email) {

        Optional<Code> code = Optional.empty();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM code INNER JOIN user_order WHERE code.email = '" + email + "' " +
                          "ORDER BY idCode DESC LIMIT 1")) {
            if (resultSet.next()) {
                code = Optional.of(
                        new Code(resultSet.getLong("idCode"), resultSet.getString("value"),
                                 resultSet.getLong("idOrder"), resultSet.getString("email")));
            }
        } catch (SQLException e) {
            LOGGER.error("SQl exception " + e.getMessage());
            return Optional.empty();
        }
        return code;
    }
}
