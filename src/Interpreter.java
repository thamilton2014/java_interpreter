import java.io.FileNotFoundException;

/**
 * The Interpreter class
 */
public class Interpreter {

    /**
     * @param args command line arguments must not be null.
     *
     */
    public static void main(String[] args) {
        try {
            Parser p = new Parser(System.getProperty("user.dir") + "/test_data/test3.e");
            Feature feature = p.parse();
            feature.execute();
            Memory.displayMemory();
        } catch (ParserException e) {
            System.out.println("[Parser Exception] " + e.toString());
        } catch (FileNotFoundException e) {
            System.out.println("[File not found] " + e.toString());
        } catch (LexicalException e) {
            System.out.println("[Lexical Exception] " + e.toString());
        } catch (Exception e){
            System.out.println("[Exception] " + e.toString());
        }
    }
}
