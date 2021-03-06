/**
 *
 */
public class Id implements Expression {

    private char ch;

    /**
     * @param ch - must be a valid identifier
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public Id(char ch) {
        this.ch = ch;
    }

    /**
     * @return ch
     */
    public int evaluate() {
        return Memory.fetch(this);
    }

    /**
     * @return ch
     */
    public char getChar() {
        return ch;
    }
}
