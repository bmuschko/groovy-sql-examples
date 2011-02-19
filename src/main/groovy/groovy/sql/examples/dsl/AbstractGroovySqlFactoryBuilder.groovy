package groovy.sql.examples.dsl

import groovy.sql.Sql

abstract class AbstractGroovySqlFactoryBuilder extends FactoryBuilderSupport {
    Sql sql

    public AbstractGroovySqlFactoryBuilder(Sql sql) {
        this.sql = sql
    }
}