package ua.nure.db.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SelectSqlQueryBuilder extends AbstractSqlQueryBuilder {
    private final static String DEFAULT_SELECT = "SELECT ";

    private final static String IN = " IN ";
    private final static String LIKE = " LIKE ";
    private final static String ORDER_BY = " ORDER BY ";
    private final static String GROUP_BY = " GROUP BY ";
    private final static String LIMIT = " LIMIT ";

    public SelectSqlQueryBuilder() {
        query.append(DEFAULT_SELECT);
    }

    public SelectSqlQueryBuilder col(String col) {
        query.append(col).append(',');
        return this;
    }

    public SelectSqlQueryBuilder col(String col, String abr) {
        query.append(col).append(" as ").append(abr).append(',');
        return this;
    }

    public SelectSqlQueryBuilder in(Object col, List<Object> params) {
        if (params == null || params.isEmpty()) {
            return this;
        }
        where(col);
        this.params.addAll(params);
        query.append(IN).append('(');
        for (int i = 0; i < params.size(); i++) {
            query.append(EQUAL);
            if (i != params.size() - 1) {
                query.append(',');
            }
        }
        query.append(')');
        return this;
    }

    public SelectSqlQueryBuilder like(Object col, String param) {
        return like(col, param, QueryParamDelimiter.AND);
    }

    public SelectSqlQueryBuilder like(Object col, String param, QueryParamDelimiter delimiter) {
        if (StringUtils.isBlank(param)) {
            return this;
        }
        where(col, delimiter);
        params.add("%" + param + "%");
        query.append(LIKE).append(EQUAL);
        return this;
    }

    public SelectSqlQueryBuilder gt(Object col, Object digit) {
        if (digit == null) {
            return this;
        }
        where(col);
        params.add(digit);
        query.append(">=").append(EQUAL);
        return this;
    }

    public SelectSqlQueryBuilder lt(Object col, Object digit) {
        if (digit == null) {
            return this;
        }
        where(col);
        params.add(digit);
        query.append("<=").append(EQUAL);
        return this;
    }

    public SelectSqlQueryBuilder groupBy(String col) {
        query.append(GROUP_BY).append(col);
        return this;
    }

    public SelectSqlQueryBuilder orderBy(String expr) {
        if (expr == null || expr.isEmpty()) {
            return this;
        }
        query.append(ORDER_BY).append(expr);
        return this;
    }

    public SelectSqlQueryBuilder limit(int from, int size) {
        query.append(LIMIT).append(from).append(',').append(size);
        return this;
    }

    public SelectSqlQueryBuilder limit(int amount) {
        query.append(LIMIT).append(amount);
        return this;
    }

}
