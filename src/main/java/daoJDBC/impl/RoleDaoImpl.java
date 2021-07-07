package main.java.daoJDBC.impl;

import dao.daoJDBC.RoleDao;
import model.Role;
import org.apache.log4j.Logger;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoleDaoImpl implements RoleDao {

    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);
    private static final String GET_ROLE_BY_NAME = "SELECT * FROM role WHERE name = ?";

    @Override
    public Optional<Role> getRoleByName(String value) {

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_NAME);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<Role> role = Optional.empty();
            while (resultSet.next()) {
                role = Optional.of(new Role(resultSet.getLong("idRole"), resultSet.getString("name")));
            }
            return role;
        } catch (SQLException e) {
            LOGGER.error("Getting role by name", e);
        }
        return Optional.empty();
    }
}
