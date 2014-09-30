/**
 * The If_Statement class is used to determine the boolean expression and then execute the appropriate code block.
 */
public class If_Statement implements Statement {

    private final BooleanExpression booleanExpression;
    private final CodeBlock codeBlock1;
    private final CodeBlock codeBlock2;

    /**
     * @param booleanExpression BooleanExpression
     * @param codeBlock1        CodeBlock
     * @param codeBlock2        CodeBlock
     */
    public If_Statement(BooleanExpression booleanExpression, CodeBlock codeBlock1, CodeBlock codeBlock2) {
        if (booleanExpression == null)
            throw new IllegalArgumentException("[If Statement] Boolean Expression is null.");
        if (codeBlock1 == null || codeBlock2 == null)
            throw new IllegalArgumentException("[If Statement] Code block is null.");
        this.booleanExpression = booleanExpression;
        this.codeBlock1 = codeBlock1;
        this.codeBlock2 = codeBlock2;
    }

    /**
     *
     */
    @Override
    public void evaluate() {
        if (booleanExpression.evaluate() == 0)
            codeBlock1.evaluate();
        else
            codeBlock2.evaluate();
    }
}
