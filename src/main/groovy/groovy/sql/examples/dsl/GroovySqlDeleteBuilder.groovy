package groovy.sql.examples.dsl

import groovy.sql.Sql

class GroovySqlDeleteBuilder extends AbstractGroovySqlBuilder {
    static final String DELETE_NODE = 'delete'
    static final String WHERE_NODE = 'where'
    static final String AND_NODE = 'and'
    static final String OR_NODE = 'or'
    static final String TABLE_ATTRIBUTE = 'table'

    public GroovySqlDeleteBuilder(Sql sql) {
        super(sql)
    }

    @Override
    protected void setParent(Object parent, Object child) {
        if(child instanceof Statement) {
            parent.statements << child
        }
    }

    @Override
    protected Object createNode(Object name) {
        createNode(name, null, null)
    }

    @Override
    protected Object createNode(Object name, Object value) {
        createNode(name, null, value)
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        createNode(name, attributes, null)
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        switch(name) {
            case DELETE_NODE: Delete delete = new Delete()
                              delete.table = (attributes && attributes.containsKey(TABLE_ATTRIBUTE)) ? attributes[TABLE_ATTRIBUTE] : value
                              return delete
            case [WHERE_NODE, AND_NODE, OR_NODE]: Statement statement = StatementFactory.instance.getStatement(name)
                                                  statement.expression = value
                                                  return statement
        }
    }

    @Override
    protected void nodeCompleted(Object parent, Object node) {
        if(parent == null) {
            String statement = createStatement(node.table, node.statements)
            sql.execute statement
        }
    }

    private String createStatement(String table, List<Statement> statements) {
        def expression

        if(statements.size() > 0) {
            Statement firstStatement = statements.get(0)

            if(!firstStatement instanceof WhereStatement) {
                throw new IllegalArgumentException("First statement always has to be a WHERE statement!")
            }
            else {
                expression = "WHERE ${firstStatement.expression}"
            }

            if(statements.size() > 1) {
                (1..statements.size() - 1).each { i ->
                    Statement statement = statements.get(i)
                    expression += " ${statement.type} ${statement.expression}"
                }
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

    @Singleton
    private class StatementFactory {
        Statement getStatement(String name) {
            switch(name) {
                case GroovySqlDeleteBuilder.WHERE_NODE: return new WhereStatement()
                case GroovySqlDeleteBuilder.AND_NODE: return new AndStatement()
                case GroovySqlDeleteBuilder.OR_NODE: return new OrStatement()
                default: throw new IllegalArgumentException("Unknown statement type '$name'")
            }
        }
    }
}
