import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()

def city = sql.firstRow("SELECT * FROM city")

println city.id + " " + city[1] + " " + city.state + " " + city.founded_year