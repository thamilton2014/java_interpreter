/**
 * The PrintStatement class
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
    public void execute() {
        System.out.println("[Print Statement] " + expression.evaluate());
    }
}
