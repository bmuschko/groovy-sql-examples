package groovy.sql.examples.dsl

import groovy.sql.Sql

class GroovySqlInsertBuilder extends AbstractGroovySqlBuilder {
    static final INSERT_NODE = 'insert'
    static final ROW_NODE = 'row'
    static final TABLE_ATTRIBUTE = 'table'
    private Insert insert

    public GroovySqlInsertBuilder(Sql sql) {
        super(sql)
        this.insert = new Insert()
    }

    @Override
    protected void setParent(Object parent, Object child) {
        if(child instanceof Row) {
            parent.rows << child
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
            case INSERT_NODE: insert.table = (attributes && attributes.containsKey(TABLE_ATTRIBUTE)) ? attributes[TABLE_ATTRIBUTE] : value
                              return insert
            case ROW_NODE: Row row = new Row()
                           row.columnNames = attributes.keySet()
                           row.values = attributes.values().toList()
                           return row
        }
    }

    @Override
    protected void nodeCompleted(Object parent, Object node) {
        if(parent == null) {
            def insertedIds = []

            insert.rows.each { row ->
                def ids = sql.executeInsert(createPreparedStatement(insert.table, row.columnNames), row.values)
                insertedIds.addAll ids
            }

            insert.result = insertedIds
        }
    }

    private String createPreparedStatement(String table, Set<String> columnNames) {
        def questionMarks = getQuestionMarks(columnNames)
        "INSERT INTO ${table} (${columnNames.join(', ')}) VALUES (${questionMarks.join(', ')})"
    }

    private getQuestionMarks(Set<String> columnNames) {
        def questionMarks = []

        columnNames.size().times {
            questionMarks << '?'
        }

        questionMarks
    }

    private class Insert {
        String table
        List<Row> rows = []
        List<List<Object>> result
    }

    private class Row {
        Set<String> columnNames
        List<Object> values
    }
}
