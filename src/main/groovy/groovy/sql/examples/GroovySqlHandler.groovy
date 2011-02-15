package groovy.sql.examples

import groovy.sql.Sql
import javax.naming.Context
import javax.naming.InitialContext
import javax.sql.DataSource
import org.h2.jdbcx.JdbcDataSource

class GroovySqlHandler {
        static final DEFAULT_URL = 'jdbc:h2:~/cityalmanac'
        static final DEFAULT_USERNAME = 'sa'
        static final DEFAULT_PASSWORD = ''
        static final DEFAULT_DRIVER_CLASS_NAME = 'org.h2.Driver'

        static createDriverManagerSql(String url = DEFAULT_URL, String username = DEFAULT_USERNAME,
                                                    String password = DEFAULT_PASSWORD, String driverClassName = DEFAULT_DRIVER_CLASS_NAME) {
                Sql.newInstance(url, username, password, driverClassName)
        }

        static createDataSourceSql(url = DEFAULT_URL, username = DEFAULT_USERNAME, password = DEFAULT_PASSWORD) {
                DataSource dataSource = new JdbcDataSource()
                dataSource.URL = url
                dataSource.user = username
                dataSource.password = password
                new Sql(dataSource)
        }

        static createJndiDataSourceSql(String jndiName) {
                Context context = new InitialContext()
                DataSource dataSource = context.lookup(jndiName)
                new Sql(dataSource)
        }
}
