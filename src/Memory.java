/**
 * The Memory class
 */
public class Memory {

    //	private static int[] mem = {0,0,0};
    final private static int[] mem = new int[26];

    /**
     * @param var - must be a valid identifier
     * @return value of ch
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public static int fetch(Id var) {
        return mem[getIndex(var)];
    }

    /**
     * @param var    - must be a valid identifier
     * @param value -
     * @throws IllegalArgumentException if ch is not a valid identifier
     */
    public static void store(Id var, int value) {
        mem[getIndex(var)] = value;
    }

    /**
     * @param var -
     * @return ch - 'A'
     */
    private static int getIndex(Id var) {
        char ch = var.getChar();
        return ch - 'a';
    }

    /**
     *
     */
    public static void displayMemory() {
        for (int i = 0; i < mem.length; i++)
            System.out.println("[Memory] " + (char) ('a' + i) + ": " + mem[i]);
    }
}
