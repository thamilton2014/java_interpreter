/**
 * The BooleanExpression class
 */
public class BooleanExpression implements Expression {

    private Expression expression1, expression2;
    private RelationalOperator op;

    /**
     * @param op          RelationalOperator
     * @param expression1 Expression
     * @param expression2 Expression
     */
    public BooleanExpression(RelationalOperator op, Expression expression1, Expression expression2) {
        if (op == null)
            throw new IllegalArgumentException("[Boolean Expression] operator is null.");
        if (expression1 == null || expression2 == null)
            throw new IllegalArgumentException("[Boolean Expression] expression is null.");
        this.op = op;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    /**
     * @return value
     */
    @Override
    public int evaluate() {
        int value;
        if (op == RelationalOperator.EQ_OP)
            value = (expression1.evaluate() == expression2.evaluate()) ? 0 : 1;
        else if (op == RelationalOperator.LE_OP)
            value = (expression1.evaluate() <= expression2.evaluate()) ? 0 : 1;
        else if (op == RelationalOperator.LT_OP)
            value = (expression1.evaluate() < expression2.evaluate()) ? 0 : 1;
        else if (op == RelationalOperator.GE_OP)
            value = (expression1.evaluate() >= expression2.evaluate()) ? 0 : 1;
        else if (op == RelationalOperator.GT_OP)
            value = (expression1.evaluate() > expression2.evaluate()) ? 0 : 1;
        else
            value = (expression1.evaluate() != expression2.evaluate()) ? 0 : 1;
        return value;
    }
}
