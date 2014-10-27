/**
 * The Token class creates a data structure.
 */
public class Token {
    private int rowNumber;
    private int columnNumber;
    private String lexeme;
    private TokenType tokType;

    /**
     * @param rowNumber    - must be positive
     * @param columnNumber - must be positive
     * @param lexeme       - cannot be null nor empty
     * @param tokType      - cannot be null
     * @throws IllegalArgumentException if any precondition is not satisfied
     */
    public Token(TokenType tokType, String lexeme, int rowNumber,
                 int columnNumber) {
        if (rowNumber <= 0)
            throw new IllegalArgumentException("[Token] invalid row number argument");
        if (columnNumber <= 0)
            throw new IllegalArgumentException("[Token] invalid column number argument");
        if (lexeme == null || lexeme.length() == 0)
            throw new IllegalArgumentException("[Token] invalid lexeme argument");
        if (tokType == null)
            throw new IllegalArgumentException("[Token] invalid TokenType argument");
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.lexeme = lexeme;
        this.tokType = tokType;
    }

    /**
     * @return int
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * @return int
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * @return int
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * @return int
     */
    public TokenType getTokType() {
        return tokType;
    }
}
