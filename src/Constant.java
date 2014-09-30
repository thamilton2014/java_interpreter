/**
 * The Constants class is used to obtain the constant token.
 */
public class Constant implements Expression {

    final private int value;

    /**
     * @param value -
     */
    public Constant(int value) {
        this.value = value;
    }

    /**
     * @return value
     */
    @Override
    public int evaluate() {
        return value;
    }
}
