import groovy.sql.examples.GroovySqlHandler

def sql = GroovySqlHandler.createDriverManagerSql()

sql.withTransaction {
        sql.execute "INSERT INTO city (name, state, founded_year) VALUES ('Minneapolis', 'Minnesota', 1867)"
        sql.execute "INSERT INTO city (name, state, founded_year) VALUES ('Orlando', 'Florida', 1875)"
        sql.execute "INSERT INTO city (name, state, founded_year) VALUES ('Gulfport', 'Mississippi', 1887)"
}

def cities = sql.dataSet('city')

sql.withTransaction {
        cities.add name: 'Minneapolis', state: 'Minnesota', founded_year: 1867
        cities.add name: 'Orlando', state: 'Florida', founded_year: 1875
        cities.add name: 'Gulfport', state: 'Mississippi', founded_year: 1887
}