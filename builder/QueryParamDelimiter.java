package ua.nure.db.builder;

/**
 * @author Vladyslav_Yemelianov
 */
public enum QueryParamDelimiter {
    AND(" AND "), OR (" OR ");
    private final String value;

    QueryParamDelimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
