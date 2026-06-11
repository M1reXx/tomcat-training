package org.mykhailo.twitter.repository;

import org.mykhailo.twitter.entity.Ticket;
import org.mykhailo.twitter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    private static final String UPDATE_TICKET_SQL = """
            update ticket set price = ?, train_id = ? where id = ?
            """;
    private static final String DELETE_TICKET_SQL = """
            delete from ticket where id = ?
            """;
    private static final String GET_ALL_TICKETS = """
            select * from ticket
            """;
    private static final String GET_TICKETS_BY_TRAIN_ID = """
            select * from ticket where train_id = ?
            """;
    private static final String INSERT_TICKET_SQL = """
            insert into ticket (id, price, train_id) values (?, ?, ?)
            """;

    private static final TicketRepository INSTANCE = new TicketRepository();

    private TicketRepository() {}

    public static TicketRepository getTicketRepository() {
        return INSTANCE;
    }

    public Integer createTicket(Long id, Long price, Long train_id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_SQL);
            statement.setLong(1, id);
            statement.setLong(2, price);
            statement.setLong(3, train_id);
            return statement.executeUpdate();
        }
    }

    public List<Ticket> getAll() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_TICKETS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(
                        resultSet.getLong("id"),
                        resultSet.getLong("price"),
                        resultSet.getLong("train_id")
                ));
            }
        }
        return tickets;
    }

    public List<Ticket> getAllTicketsByTrainId(Long train_id) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(GET_TICKETS_BY_TRAIN_ID);
            statement.setLong(1, train_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(
                        resultSet.getLong("id"),
                        resultSet.getLong("price"),
                        resultSet.getLong("train_id")
                ));
            }
        }
        return tickets;
    }

    public boolean update(Ticket ticket) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET_SQL);
            statement.setLong(1, ticket.getPrice());
            statement.setLong(2, ticket.getTrainId());
            statement.setLong(3, ticket.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(Long id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_TICKET_SQL);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}