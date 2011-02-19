package groovy.sql.examples.dsl

import org.junit.Test

class GroovySqlDeleteBuilderTest extends GroovySqlBuilderFixture {
    @Test
    public void testBuildingWithEqualsCriteria() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            eq(name: 'name', value: 'Grand Rapids')
        }
    }

    @Test
    public void testBuildingWithNotEqualsCriteria() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            ne(name: 'name', value: 'Grand Rapids')
        }
    }

    @Test
    public void testBuildingWithLikeCriteria() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            like(name: 'name', value: 'Grand%')
        }
    }

    @Test
    public void testBuildingWithIsNullCriteria() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            isNull(name: 'name')
        }
    }

    @Test
    public void testBuildingWithIsNotNullCriteria() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            isNotNull(name: 'name')
        }
    }

    @Test
    public void testBuildingWithAndStatement() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            and {
                eq(name: 'name', value: 'Grand Rapids')
                isNotNull(name: 'name')
            }
        }
    }

    @Test
    public void testBuildingWithOrStatement() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            or {
                eq(name: 'name', value: 'Grand Rapids')
                eq(name: 'name', value: 'Little Rock')
            }
        }
    }

    @Test
    public void testBuildingWithNestedLogicStatements() {
        def builder = new GroovySqlDeleteBuilder(sql)
        builder.delete(TABLE_NAME) {
            and {
                isNotNull(name: 'name')

                or {
                    eq(name: 'name', value: 'Grand Rapids')
                    eq(name: 'name', value: 'Little Rock')
                }
            }
        }
    }
}

