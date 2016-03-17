package ua.nure.db.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladyslav_Yemelianov
 */
public abstract class AbstractSqlQueryBuilder {
    protected final static String FROM = " FROM ";
    protected final static String EQUAL = " ? ";
    protected final static String WHERE = " WHERE ";
    protected final static String AND = " AND ";

    protected StringBuilder query = new StringBuilder();
    protected List<Object> params = new ArrayList<>();

    public AbstractSqlQueryBuilder where(Object col) {
        appendWhere(query);
        query.append(col);
        return this;
    }

    public AbstractSqlQueryBuilder where(Object col, QueryParamDelimiter delimiter) {
        appendWhere(query, delimiter);
        query.append(col);
        return this;
    }

    public AbstractSqlQueryBuilder from(Object table) {
        deleteComa();
        query.append(FROM).append(table);
        return this;
    }

    public AbstractSqlQueryBuilder equal(Object col, Object param) {
        if (param == null) {
            return this;
        }
        where(col);
        params.add(param);
        query.append('=').append(EQUAL);
        return this;
    }

    public PreparedStatement build(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query.toString());
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }
        return statement;
    }

    protected void deleteComa() {
        if (query.charAt(query.length() - 1) == ',') {
            query.delete(query.length() - 1, query.length());
        }
    }

    private void appendWhere(StringBuilder query, QueryParamDelimiter delimiter) {
        if (query.toString().contains(WHERE)) {
            query.append(delimiter.getValue());
            return;
        }
        query.append(WHERE);
    }

    private void appendWhere(StringBuilder query) {
        if (query.toString().contains(WHERE)) {
            query.append(AND);
            return;
        }
        query.append(WHERE);
    }
}
