/**
 * The PrintStatement class is used to execute console reporting.
 */
public class PrintStatement implements Statement {

    private Expression expression;

    /**
     * @param expression -
     */
    public PrintStatement(Expression expression) {
        if (expression == null)
            throw new IllegalArgumentException("[PrintStatement] value is null");
        this.expression = expression;
    }

    /**
     *
     */
    @Override
    public void evaluate() {
        System.out.println("[Print Statement] " + expression.evaluate());
    }
}
