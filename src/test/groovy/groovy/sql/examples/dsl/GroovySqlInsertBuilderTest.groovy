package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import org.junit.Test

class GroovySqlInsertBuilderTest {
    @Test
    public void testBuildInsertWithoutTableAttribute() {
        Sql sql = GroovySqlHandler.createDriverManagerSql()
        def builder = new GroovySqlInsertBuilder(sql)
        def insert = builder.insert('city') {
            row(name: 'Spieskappel', state: 'Hessia', founded_year: 1197)
            row(name: 'Frielendorf', state: 'Hessia', founded_year: 1000)
        }

        assert insert.result.size() == 2
        println "Created records with IDs $insert.result"
    }

    @Test
    public void testBuildInsertWithTableAttribute() {
        Sql sql = GroovySqlHandler.createDriverManagerSql()
        def builder = new GroovySqlInsertBuilder(sql)
        def insert = builder.insert(table: 'city') {
            row(name: 'Spieskappel', state: 'Hessia', founded_year: 1197)
            row(name: 'Frielendorf', state: 'Hessia', founded_year: 1000)
        }

        assert insert.result.size() == 2
        println "Created records with IDs $insert.result"
    }
}
