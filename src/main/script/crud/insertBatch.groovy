import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

def boston = new Expando(name: 'Boston', state: 'Massachusetts', foundedYear: 1630)
def charleston = new Expando(name: 'Charleston', state: 'West Virginia', foundedYear: 1788)
def washingtonDc = new Expando(name: 'Washington', state: 'District of Columbia', foundedYear: 1790)
def newCities = [boston, charleston, washingtonDc]

Sql sql = GroovySqlHandler.createDriverManagerSql()
int[] updateCount = sql.withBatch { stmt ->
        newCities.each { city ->
                stmt.addBatch "INSERT INTO city (name, state, founded_year) VALUES ('$city.name', '$city.state', $city.foundedYear)"
        }
}

println "Updated $updateCount record(s)."