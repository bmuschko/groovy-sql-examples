import groovy.sql.examples.GroovySqlHandler
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger

def logger = Logger.getLogger('groovy.sql')
logger.level = Level.FINE
logger.addHandler(new ConsoleHandler(level: Level.FINE))

def sql = GroovySqlHandler.createDriverManagerSql()

sql.eachRow("select * from city") { city ->
        println city.id + " " + city[1] + " " + city.state + " " + city.founded_year
}