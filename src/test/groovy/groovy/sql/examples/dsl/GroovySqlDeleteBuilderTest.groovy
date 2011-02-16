package groovy.sql.examples.dsl

import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler
import org.junit.Test

/**
 * Created by IntelliJ IDEA.
 * User: ben
 * Date: 2/15/11
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
class GroovySqlDeleteBuilderTest {
	@Test
	public void testBuilding() {
		Sql sql = GroovySqlHandler.createDriverManagerSql()
		def builder = new GroovySqlDeleteBuilder(sql)
		builder.delete('city') {
			where("name = 'Spieskappel'")
		}
	}
}
