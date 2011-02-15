import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()
int updateCount = sql.executeUpdate('UPDATE city SET founded_year = ? WHERE name = ?', [1630, 'Boston'])

println "Updated $updateCount record(s)."