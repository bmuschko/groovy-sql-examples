import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()
sql.execute('DELETE FROM city WHERE name = ?', ['Boston'])
