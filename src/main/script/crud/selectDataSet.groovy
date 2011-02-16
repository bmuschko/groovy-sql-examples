import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()

def cities = sql.dataSet('city')

cities.each { city ->
    println city.id + " " + city[1] + " " + city.state + " " + city.founded_year
}
