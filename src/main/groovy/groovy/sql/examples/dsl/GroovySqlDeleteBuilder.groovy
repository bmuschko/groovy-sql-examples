package groovy.sql.examples.dsl

import groovy.sql.Sql

class GroovySqlDeleteBuilder extends AbstractGroovySqlFactoryBuilder {
    public GroovySqlDeleteBuilder(Sql sql) {
        super(sql)
        registerFactories()
    }

    def registerFactories() {
        registerFactory('delete', new DeleteFactory())
        registerFactory('eq', new EqualsCriteriaFactory())
        registerFactory('ne', new NotEqualsCriteriaFactory())
        registerFactory('like', new LikeCriteriaFactory())
        registerFactory('isNull', new IsNullCriteriaFactory())
        registerFactory('isNotNull', new IsNotNullCriteriaFactory())
        registerFactory('and', new AndLogicOperationFactory())
        registerFactory('or', new OrLogicOperationFactory())
    }

    private abstract class CriteriaAbstractFactory extends AbstractFactory {
        @Override
        void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
            if(child instanceof Criteria) {
                parent.criterias << child
            }
        }
    }

    private abstract class KeyValuePairCriteriaAbstractFactory extends CriteriaAbstractFactory {
        final String NAME_ATTRIBUTE = 'name'
        final String VALUE_ATTRIBUTE = 'value'

        @Override
        public boolean isLeaf() {
            true
        }
    }

    private abstract class LogicOperatorCriteriaAbstractFactory extends CriteriaAbstractFactory {
        @Override
        public boolean isLeaf() {
            false
        }
    }

    private class DeleteFactory extends AbstractFactory {
        final String TABLE_ATTRIBUTE = 'table'

        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            Delete delete = new Delete()
            delete.table = (attributes && attributes.containsKey(TABLE_ATTRIBUTE)) ? attributes[TABLE_ATTRIBUTE] : value
            delete
        }

        @Override
        void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
            String statement = createStatement(node.table, node.criterias)
            builder.sql.execute statement
        }

        private String createStatement(String table, criterias) {
            def expression = new StringBuilder()

            if(criterias.size() > 0) {
                criterias.eachWithIndex { criteria, index ->
                    if(index == 0) {
                        expression <<= "WHERE "
                    }
                    else {
                        expression <<= " AND "
                    }

                    if(criteria instanceof Criteria) {
                        expression <<= criteria.renderExpression()
                    }
                }
            }

            "DELETE FROM ${table} ${expression}"
        }

        @Override
        public boolean isLeaf() {
            false
        }
    }

    private class EqualsCriteriaFactory extends KeyValuePairCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new EqualsKeyValuePair(attributes[NAME_ATTRIBUTE], attributes[VALUE_ATTRIBUTE])
        }
    }

    private class NotEqualsCriteriaFactory extends KeyValuePairCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new NotEqualsKeyValuePair(attributes[NAME_ATTRIBUTE], attributes[VALUE_ATTRIBUTE])
        }
    }

    private class LikeCriteriaFactory extends KeyValuePairCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new LikeKeyValuePair(attributes[NAME_ATTRIBUTE], attributes[VALUE_ATTRIBUTE])
        }
    }

    private class IsNullCriteriaFactory extends KeyValuePairCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new EqualsKeyValuePair(attributes[NAME_ATTRIBUTE], null)
        }
    }

    private class IsNotNullCriteriaFactory extends KeyValuePairCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new NotEqualsKeyValuePair(attributes[NAME_ATTRIBUTE], null)
        }
    }

    private class AndLogicOperationFactory extends LogicOperatorCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new AndLogicOperator()
        }
    }

    private class OrLogicOperationFactory extends LogicOperatorCriteriaAbstractFactory {
        @Override
        Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
            return new OrLogicOperator()
        }
    }

    private class Delete {
        String table
        def criterias = []
    }

    private interface Criteria {
        def renderExpression()
    }

    private abstract class KeyValuePair implements Criteria {
        String name
        Object value

        KeyValuePair(name, value) {
            this.name = name
            this.value = value
        }

        String getCriteriaValue(value) {
            value instanceof String ? "'${value}'" : value
        }
    }

    private class EqualsKeyValuePair extends KeyValuePair {
        EqualsKeyValuePair(name, value) {
            super(name, value)
        }

        @Override
        def renderExpression() {
            if(value) {
                return "${name} = ${getCriteriaValue(value)}"
            }
            else {
                return "${name} is null"
            }
        }
    }

    private class NotEqualsKeyValuePair extends KeyValuePair {
        NotEqualsKeyValuePair(name, value) {
            super(name, value)
        }

        @Override
        def renderExpression() {
            if(value) {
                return "${name} != ${getCriteriaValue(value)}"
            }
            else {
                return "${name} is not null"
            }
        }
    }

    private class LikeKeyValuePair extends KeyValuePair {
        LikeKeyValuePair(name, value) {
            super(name, value)
        }

        @Override
        def renderExpression() {
            return "${name} like ${getCriteriaValue(value)}"
        }
    }

    private abstract class LogicOperator implements Criteria {
        def criterias = []
    }

    private class AndLogicOperator extends LogicOperator {
        @Override
        def renderExpression() {
            def expression = new StringBuilder()
            expression <<= "("

            criterias.eachWithIndex { nestedCriteria, index ->
                expression <<= nestedCriteria.renderExpression()

                if(index < criterias.size() - 1) {
                    expression <<= " AND "
                }
            }

            expression <<= ")"
            expression
        }
    }

    private class OrLogicOperator extends LogicOperator {
        @Override
        def renderExpression() {
            def expression = new StringBuilder()
            expression <<= "("

            criterias.eachWithIndex { nestedCriteria, index ->
                expression <<= nestedCriteria.renderExpression()

                if(index < criterias.size() - 1) {
                    expression <<= " OR "
                }
            }

            expression <<= ")"
            expression
        }
    }
}