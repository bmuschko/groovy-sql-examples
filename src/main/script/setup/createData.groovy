import groovy.sql.Sql
import groovy.sql.examples.GroovySqlHandler

Sql sql = GroovySqlHandler.createDriverManagerSql()

sql.execute """
    INSERT INTO city (name, state, founded_year) VALUES ('Boston', 'Massachusetts', 1630);
    INSERT INTO city (name, state, founded_year) VALUES ('Charleston', 'West Virginia', 1788);
    INSERT INTO city (name, state, founded_year) VALUES ('Washington', 'District of Columbia', 1790);
    INSERT INTO city (name, state, founded_year) VALUES ('Chicago', 'Illinois', 1803);
    INSERT INTO city (name, state, founded_year) VALUES ('Montgomery', 'Alabama', 1819);
    INSERT INTO city (name, state, founded_year) VALUES ('Dallas', 'Texas', 1841);
    INSERT INTO city (name, state, founded_year) VALUES ('Las Vegas', 'Nevada', 1905);
"""