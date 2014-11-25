import java.io.FileNotFoundException;
import java.util.logging.LogManager;

/**
 * The Interpreter class
 */
public class Interpreter {

    private static LogManager logger = LogManager.getLogManager();
    /**
     * @param args command line arguments must not be null.
     *             test feature
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
