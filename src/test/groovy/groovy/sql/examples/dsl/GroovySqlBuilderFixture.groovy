package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass

class GroovySqlBuilderFixture {
    static final String TABLE_NAME = 'city'
    Sql sql

    @BeforeClass
    public static void setUpClass() {
        def logger = Logger.getLogger('groovy.sql')
        logger.level = Level.FINE
        logger.addHandler(new ConsoleHandler(level: Level.FINE))
    }

    @Before
    public void setUp() {
        sql = GroovySqlHandler.createDriverManagerSql()
    }

    @After
    public void tearDown() {
        sql = null
    }
}
