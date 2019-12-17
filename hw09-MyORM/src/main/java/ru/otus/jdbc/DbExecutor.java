package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.model.Model;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

    public long insertRecord(Connection connection, String sql, List<String> param) throws SQLException {
        Savepoint savepoint = connection.setSavepoint();
        try(PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for(int idx = 0; idx < param.size(); idx++) {
                pst.setObject(idx+1, param.get(idx));
            }
            pst.executeUpdate();
            try(ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            } catch (SQLException e) {
                connection.rollback(savepoint);
                throw e;
            }
        }
    }

    public Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1,id);
            try(ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }

    }
}
