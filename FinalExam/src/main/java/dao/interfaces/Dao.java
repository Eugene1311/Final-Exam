package dao.interfaces;


import common.Private;
import common.functions.Exceptional;
import common.functions.ExceptionalFunction;

import java.sql.*;

@FunctionalInterface
public interface Dao {
    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Dao.class);

    Connection getConnection() throws SQLException;

    @SuppressWarnings("unchecked")
    @Private
    default <T, E extends SQLException> Exceptional<T, E> withConnection(ExceptionalFunction<Connection, T, E> jdbcTemplate) {
        try (final Connection connection = getConnection()) {
            return jdbcTemplate.apply(connection);
        } catch (SQLException e) {
            log.error(e);
            return Exceptional.withException((E) e);
        }
    }

    @Private
    default <T> Exceptional<T, SQLException> withStatement(ExceptionalFunction<Statement, T, SQLException> jdbcTemplate) {
        return withConnection(connection -> {
            try (final Statement statement = connection.createStatement()) {
                return jdbcTemplate.get(statement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> withPreparedStatement(String sql,
                                                                   ExceptionalFunction<PreparedStatement, T, SQLException> jdbcTemplate) {
        return withConnection(connection -> {
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                return jdbcTemplate.get(statement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> withCallableStatement(String call,
                                                                   ExceptionalFunction<CallableStatement, T, SQLException> jdbcTemplate) {
        return withConnection(connection -> {
            try (final CallableStatement callableStatement = connection.prepareCall(call)) {
                return jdbcTemplate.get(callableStatement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> executeQuery(String sql,
                                                          ExceptionalFunction<ResultSet, T, SQLException> template) {
        return withStatement(statement -> {
            try (final ResultSet rs = statement.executeQuery(sql)) {
                return template.get(rs);
            }
        });
    }

    @Private
    default Exceptional<Integer, SQLException> executeUpdate(String sql) {
        return withStatement(statement -> statement.executeUpdate(sql));
    }
}