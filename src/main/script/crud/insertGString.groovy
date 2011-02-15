import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

def boston = new Expando(name: 'Boston', state: 'Massachusetts', foundedYear: 1630)

Sql sql = GroovySqlHandler.createDriverManagerSql()
def ids = sql.executeInsert("INSERT INTO city (name, state, founded_year) VALUES ($boston.name, $boston.state, $boston.foundedYear)")

println "Created cities with IDs $ids"