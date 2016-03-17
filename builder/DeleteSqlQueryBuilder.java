package ua.nure.db.builder;

/**
 * @author Vladyslav_Yemelianov
 */
public class DeleteSqlQueryBuilder extends AbstractSqlQueryBuilder {
    private static final String DEFAULT_DELETE = "DELETE ";

    public DeleteSqlQueryBuilder delete(Object table) {
        query.append(DEFAULT_DELETE);
        from(table);
        return this;
    }
}
