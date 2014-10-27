/**
 * The LoopStatement class
 */
public class LoopStatement implements Statement {

    private Assignment assignment;
    private BooleanExpression booleanExpression;
    private Compound compound;

    /**
     * @param assignment Assignment
     * @param booleanExpression BooleanExpression
     * @param compound compound
     */
    public LoopStatement(Assignment assignment, BooleanExpression booleanExpression, Compound compound) {
        if(assignment == null)
            throw new IllegalArgumentException("[Loop Statement] Assignment is null.");
        if (booleanExpression == null)
            throw new IllegalArgumentException("[Loop Statement] Boolean Expression is null.");
        if (compound == null)
            throw new IllegalArgumentException("[Loop Statement] Code Block is null.");
        this.assignment = assignment;
        this.booleanExpression = booleanExpression;
        this.compound = compound;
    }

    /**
     *
     */
    @Override
    public void execute() {
        for(assignment.execute();!booleanExpression.evaluate();)
            compound.execute();
//        assignment.evaluate();
//        while(booleanExpression.evaluate() != 0){
//            codeBlock.evaluate();
//        }
    }
}
