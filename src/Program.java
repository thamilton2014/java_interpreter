/**
 * The Program class
 */
public class Program {

    private CodeBlock codeBlock;

    /**
     * @param codeBlock -
     */
    public Program(CodeBlock codeBlock) {
        if (codeBlock == null)
            throw new IllegalArgumentException("[Program] codeBlock is null.");
        this.codeBlock = codeBlock;
    }

    /**
     *
     */
    public void evaluate() {
        codeBlock.evaluate();
    }
}
