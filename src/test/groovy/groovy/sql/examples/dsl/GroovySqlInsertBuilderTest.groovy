package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import org.junit.After
import org.junit.Before
import org.junit.Test

class GroovySqlInsertBuilderTest {
    static final String TABLE_NAME = 'city'
    private Sql sql

    @Before
    public void setUp() {
        sql = GroovySqlHandler.createDriverManagerSql()
    }

    @After
    public void tearDown() {
        sql = null
    }

    @Test
    public void testBuildInsertWithoutTableAttribute() {
        def builder = new GroovySqlInsertBuilder(sql)
        def insert = builder.insert(TABLE_NAME) {
            row(name: 'Grand Rapids', state: 'Michigan', founded_year: 1825)
            row(name: 'Little Rock', state: 'Arkansas', founded_year: 1821)
        }

        assert insert.result.size() == 2
        println "Created records with IDs $insert.result"
    }

    @Test
    public void testBuildInsertWithTableAttribute() {
        def builder = new GroovySqlInsertBuilder(sql)
        def insert = builder.insert(table: TABLE_NAME) {
            row(name: 'Grand Rapids', state: 'Michigan', founded_year: 1825)
            row(name: 'Little Rock', state: 'Arkansas', founded_year: 1821)
        }

        assert insert.result.size() == 2
        println "Created records with IDs $insert.result"
    }
}
