/**
 * The Program class
 */
public class Feature {

    private Compound compound;

    /**
     * @param compound -
     */
    public Feature(Compound compound) {
        if (compound == null)
            throw new IllegalArgumentException("[Program] codeBlock is null.");
        this.compound = compound;
    }

    /**
     *
     */
    public void execute() {
        compound.execute();
    }
}
