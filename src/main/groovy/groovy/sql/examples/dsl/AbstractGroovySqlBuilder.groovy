package groovy.sql.examples.dsl

import groovy.sql.Sql

abstract class AbstractGroovySqlBuilder extends BuilderSupport {
    Sql sql

    public AbstractGroovySqlBuilder(Sql sql) {
        this.sql = sql
    }
}
