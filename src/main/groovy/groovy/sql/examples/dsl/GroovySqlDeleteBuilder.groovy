package groovy.sql.examples.dsl

import groovy.sql.Sql

class GroovySqlDeleteBuilder extends AbstractGroovySqlBuilder {
    private Delete delete

    public GroovySqlDeleteBuilder(Sql sql) {
        super(sql)
        this.delete = new Delete()
    }

    @Override
    protected void setParent(Object parent, Object child) {
        if(child instanceof Statement) {
            parent.statements << child
        }
    }

    @Override
    protected Object createNode(Object name) {
        return null
    }

    @Override
    protected Object createNode(Object name, Object value) {
        switch(name) {
                case 'delete':
                        delete.table = value
                        return delete
                case 'where':
                    Statement statement = new WhereStatement()
                    statement.expression = value
                    return statement
                case 'and':
                    Statement statement = new AndStatement()
                    statement.expression = value
                    return statement
                case 'or':
                    Statement statement = new OrStatement()
                    statement.expression = value
                    return statement
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        switch(name) {
            case 'delete':
                        delete.table = attributes['table']
                        return delete
        }
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        return null
    }

    @Override
    protected void nodeCompleted(Object parent, Object node) {
        if(parent == null) {
            sql.execute createStatement(delete.table, delete.statements)
        }
    }

    private String createStatement(String table, List<Statement> statements) {
        def expression

        statements.each { statement ->
            if(statement instanceof WhereStatement) {
                expression = "WHERE ${statement.expression}"
            }
        }

        "DELETE FROM ${table} ${expression}"
    }

    private class Delete {
        String table
        List<Statement> statements = []
    }

    private class Statement {
        Type type
        String expression
        List<Statement> statements = []

        Statement(Type type) {
            this.type = type
        }

        public enum Type {
            WHERE, AND, OR
        }
    }

    private class WhereStatement extends Statement {
        WhereStatement() {
            super(Type.WHERE)
        }
    }

    private class AndStatement extends Statement {
        AndStatement() {
            super(Type.AND)
        }
    }

    private class OrStatement extends Statement {
        OrStatement() {
            super(Type.OR)
        }
    }
}
