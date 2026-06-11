package org.mykhailo.twitter.repository;

import org.mykhailo.twitter.entity.User;
import org.mykhailo.twitter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static final UserRepository INSTANCE = new UserRepository();

    private static final String LOGIN = """
                SELECT * FROM users WHERE id = ?
            """;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        return INSTANCE;
    }

    public User login(Long id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(LOGIN);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getLong("tickets_id")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}