package ua.nure.db.builder;

/**
 * @author Vladyslav_Yemelianov
 */
public class UpdateSqlQueryBuilder extends AbstractSqlQueryBuilder {
    private static final String DEFAULT_UPDATE = "UPDATE ";
    private static final String SET = " SET ";

    public UpdateSqlQueryBuilder update(Object table) {
        query.append(DEFAULT_UPDATE).append(table);
        return this;
    }

    public UpdateSqlQueryBuilder set(Object col, Object value) {
        if (value == null) {
            return this;
        }
        appendSet();
        query.append(col).append('=').append(EQUAL);
        params.add(value);
        return this;
    }

    private void appendSet() {
        if (query.toString().contains(SET)) {
            query.append(" , ");
            return;
        }
        query.append(SET);
    }
}
