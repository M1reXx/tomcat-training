package org.mykhailo.twitter.repository;

import org.mykhailo.twitter.entity.Train;
import org.mykhailo.twitter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    private static final String INSERT_TRAIN_SQL = """
            insert into train (seat_count) values (?)
            """;

    private static final String GET_TRAIN_BY_ID = """
            select * from train where id = ?
            """;

    private static final String UPDATE_TRAIN_SQL = """
            update train set seat_count = ? where id = ?
            """;

    private static final String DELETE_TRAIN_SQL = """
            delete from train where id = ?
            """;

    private static final String GET_ALL_TRAINS = """
            select * from train
            """;

    private static final TrainRepository INSTANCE = new TrainRepository();
    private final TicketRepository ticketRepository = TicketRepository.getTicketRepository();


    private TrainRepository() {
    }

    public Integer createTrain(Long seatCount) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_TRAIN_SQL);
            statement.setLong(1, seatCount);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Train getTrainById(Long id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(GET_TRAIN_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Train(
                        resultSet.getLong("id"),
                        resultSet.getLong("seat_count")
                );
            } else {
                return null;
            }
        }
    }

    public static TrainRepository getTrainRepository() {
        return INSTANCE;
    }

    public List<Train> getAll() throws SQLException {
        List<Train> trains = new ArrayList<>();

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_TRAINS);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                trains.add(new Train(
                        id,
                        resultSet.getLong("seat_count")
                ));
            }
        }
        return trains;
    }

    public boolean update(Train train) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TRAIN_SQL);
            statement.setLong(1, train.getSeatCount());
            statement.setLong(2, train.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(Long id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_TRAIN_SQL);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}
