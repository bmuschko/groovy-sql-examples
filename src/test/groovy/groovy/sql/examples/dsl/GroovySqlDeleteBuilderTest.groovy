package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import org.junit.Test

class GroovySqlDeleteBuilderTest {
    static final String TABLE_NAME = 'city'

    @Test
    public void testBuildingWithWhereStatement() {
        Sql sql = GroovySqlHandler.createDriverManagerSql()
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name = 'Grand Rapids'")
        }
    }

    @Test
    public void testBuildingWithWhereAndOrStatements() {
        Sql sql = GroovySqlHandler.createDriverManagerSql()
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name = 'Grand Rapids'")
            or("name = 'Little Rock'")
        }
    }

    @Test
    public void testBuildingWithNestedStatements() {
        Sql sql = GroovySqlHandler.createDriverManagerSql()
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            where("name is not null")
            and {
                or("name = 'Little Rock'")
            }
        }
    }
}
