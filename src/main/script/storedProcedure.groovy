import groovy.sql.examples.GroovySqlHandler
import groovy.sql.Sql

def sql = GroovySqlHandler.createDriverManagerSql()

sql.call('{ ? = call CitiesBetween(?, ?) }', [Sql.ARRAY, 1800, 1900]) { city ->
        println city.id + " " + city[1] + " " + city.state + " " + city.founded_year
}