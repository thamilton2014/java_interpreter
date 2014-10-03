/**
 * The LexicalException class
 */
public class LexicalException extends Exception {
    /**
     * @param string - display error
     */
    public LexicalException(String string) {
        System.out.println("[Lexical Exception] " + string);
    }
}
