/**
 * The Program class is used to generate the code block.
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
