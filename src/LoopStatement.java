/**
 * The LoopStatement class
 */
public class LoopStatement implements Statement {

    private Assignment assignment;
    private BooleanExpression booleanExpression;
    private CodeBlock codeBlock;

    /**
     * @param assignment Assignment
     * @param booleanExpression BooleanExpression
     * @param codeBlock CodeBlock
     */
    public LoopStatement(Assignment assignment, BooleanExpression booleanExpression, CodeBlock codeBlock) {
        if(assignment == null)
            throw new IllegalArgumentException("[Loop Statement] Assignment is null.");
        if (booleanExpression == null)
            throw new IllegalArgumentException("[Loop Statement] Boolean Expression is null.");
        if (codeBlock == null)
            throw new IllegalArgumentException("[Loop Statement] Code Block is null.");
        this.assignment = assignment;
        this.booleanExpression = booleanExpression;
        this.codeBlock = codeBlock;
    }

    /**
     *
     */
    @Override
    public void evaluate() {
        assignment.evaluate();
        while(booleanExpression.evaluate() != 0){
            codeBlock.evaluate();
        }
    }
}
