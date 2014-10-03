import java.io.FileNotFoundException;

/**
 * The Interpreter class
 */
public class Interpreter {

    /**
     * @param args command line arguments must not be null.
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new IllegalArgumentException("[CLI] Usage: 'java -jar <file location>'");
            Parser p = new Parser(args[0]);
            p.execute();
            Memory.displayMemory();
        } catch (ParserException e) {
            System.out.println("[Parser Exception] " + e.toString());
        } catch (FileNotFoundException e) {
            System.out.println("[File not found] " + e.toString());
        } catch (LexicalException e) {
            System.out.println("[Lexical Exception] " + e.toString());
        }
    }
}
