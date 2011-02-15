import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()
def ids = sql.executeInsert('INSERT INTO city (name, state, founded_year) VALUES (?, ?, ?)', ['Boston', 'Massachusetts', 1630])

println "Created cities with IDs $ids"