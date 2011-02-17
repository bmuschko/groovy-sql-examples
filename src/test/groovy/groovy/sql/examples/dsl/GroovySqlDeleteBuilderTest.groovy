package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import org.junit.After
import org.junit.Before
import org.junit.Test

class GroovySqlDeleteBuilderTest {
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
    public void testBuildingWithWhereStatement() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name = 'Grand Rapids'")
        }
    }

    @Test
    public void testBuildingWithWhereAndOrStatements() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name = 'Grand Rapids'")
            or("name = 'Little Rock'")
        }
    }

    @Test
    public void testBuildingWithNestedStatements() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name is not null")
            and {
                or("name = 'Little Rock'")
            }
        }
    }
}
