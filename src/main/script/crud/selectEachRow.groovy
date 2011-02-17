import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()
println ' Cities '.center(30, '-')

sql.eachRow("SELECT * FROM city") { city ->
    println "${city[1]}, $city.state"
    println "founded in year $city.founded_year"
    println '-' * 30
}