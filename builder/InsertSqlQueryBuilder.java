package ua.nure.db.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertSqlQueryBuilder {
    private final static String DEFAULT_INSERT = "INSERT INTO ";

    private final static String VALUE = " VALUE ";

    private final static String EQUAL = " ? ";

    private List<Object> params = new ArrayList<>();

    private List<String> cols = new ArrayList<>();

    private StringBuilder query = new StringBuilder(DEFAULT_INSERT);

    private StringBuilder values = new StringBuilder(VALUE);

    public InsertSqlQueryBuilder into(String table) {
        query.append(table);
        return this;
    }

    public InsertSqlQueryBuilder value(String col, Object value) {
        if (value == null) {
            return this;
        }
        params.add(value);
        cols.add(col);
        return this;
    }

    public PreparedStatement build(Connection connection) throws SQLException {
        query.append('(');
        values.append('(');
        for (int i = 0; i < cols.size(); i++) {
            query.append(cols.get(i));
            values.append(EQUAL);
            if (i != cols.size() - 1) {
                query.append(',');
                values.append(',');
            }
        }
        values.append(')');
        query.append(')').append(values);

        PreparedStatement statement = connection.prepareStatement(query.toString());
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        return statement;
    }
}
