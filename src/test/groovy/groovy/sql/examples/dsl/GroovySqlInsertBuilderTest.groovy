package groovy.sql.examples.dsl

import org.junit.Test

class GroovySqlInsertBuilderTest extends GroovySqlBuilderFixture {
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
