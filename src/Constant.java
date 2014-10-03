/**
 * The Constants class
 */
public class Constant implements Expression {

    final private int value;

    /**
     * @param value - instantiate
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
