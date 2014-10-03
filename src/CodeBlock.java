import java.util.List;

/**
 * The CodeBlock class
 */
public class CodeBlock {

    private List<Statement> statementList;

    /**
     *
     * @param statementList List<Statement></Statement>
     */
    public CodeBlock(List<Statement> statementList) {
        if(statementList == null)
            throw new IllegalArgumentException("[CodeBlock] statementList is null");
        this.statementList = statementList;
    }

    /**
     *
     */
    public void evaluate() {
        for(Statement statement : statementList)
        statement.evaluate();
    }
}
