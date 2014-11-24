/**
 * The If_Statement class
 */
public class If_Statement implements Statement {

    private final BooleanExpression booleanExpression;
    private final Compound compound_1;
    private final Compound compound_2;

    public If_Statement(BooleanExpression booleanExpression, Compound compound_1, Compound compound_2) {
        if (booleanExpression == null)
            throw new IllegalArgumentException("[If Statement] Boolean Expression is null.");
        if (compound_1 == null || compound_2 == null)
            throw new IllegalArgumentException("[If Statement] Compound is null.");
        this.booleanExpression = booleanExpression;
        this.compound_1 = compound_1;
        this.compound_2 = compound_2;
    }

    /**
     *
     */
    public void execute() {
        if (booleanExpression.evaluate())
            compound_1.execute();
        else
            compound_2.execute();
    }
}
