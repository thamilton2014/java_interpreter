import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The LexicalAnalyzer class
 */
public class LexicalAnalyzer {

    final private List<Token> tokens;

    /**
     * @param fileName File
     * @throws FileNotFoundException
     * @throws LexicalException
     */
    public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexicalException {
        tokens = new ArrayList<Token>();
        Scanner sourceCode = new Scanner(new File(fileName));
        int lineNumber = 0;
        while (sourceCode.hasNext()) {
            String line = sourceCode.nextLine();
            processLine(line, lineNumber);
            lineNumber++;
        }
        tokens.add(new Token(lineNumber, 1, "EOS", TokenType.EOS_TOK));
        sourceCode.close();
    }

    /**
     * @param line       String
     * @param lineNumber int
     * @throws LexicalException
     */
    private void processLine(String line, int lineNumber) throws LexicalException {
        int index = 0;
        index = skipWhiteSpace(line, index);
        while (index < line.length()) {
            String lexeme = getLexeme(line, index);
            TokenType tokType = getTokenType(lexeme, lineNumber, index);
            tokens.add(new Token(lineNumber + 1, index + 1, lexeme, tokType));
            index += lexeme.length();
            index = skipWhiteSpace(line, index);
        }
    }

    /**
     * @param lexeme       String
     * @param lineNumber   int
     * @param columnNumber int
     * @return TokenType
     * @throws LexicalException
     */
    private TokenType getTokenType(String lexeme, int lineNumber, int columnNumber) throws LexicalException {
        TokenType tokType;
        if (lexeme.contains(FeatureConstants.FEATURE))
            tokType = TokenType.FEATURE_TOK;
        else if (lexeme.matches("[a-zA-Z]")) {
            if (lexeme.length() == 1)
                if (isValidIdentifier(lexeme.charAt(0)))
                    tokType = TokenType.ID_TOK;
                else
                    throw new LexicalException("invalid lexeme at row number " +
                            (lineNumber + 1) + " and column " + (columnNumber + 1));
            else
                throw new LexicalException("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        } else if (Character.isDigit(lexeme.charAt(0))) {
            if (allDigits(lexeme))
                tokType = TokenType.CONST_TOK;
            else
                throw new LexicalException("invalid lexeme at row number " +
                        (lineNumber + 1) + " and column " + (columnNumber + 1));
        } else if (lexeme.contains(FeatureConstants.IS))
            tokType = TokenType.IS_TOK;
        else if (lexeme.contains(FeatureConstants.DO))
            tokType = TokenType.DO_TOK;
        else if (lexeme.equals(FeatureConstants.PLUS))
            tokType = TokenType.ADD_TOK;
        else if (lexeme.equals(FeatureConstants.MULTIPLY))
            tokType = TokenType.MUL_TOK;
        else if (lexeme.equals(FeatureConstants.MINUS))
            tokType = TokenType.SUB_TOK;
        else if (lexeme.equals(FeatureConstants.DIVIDE))
            tokType = TokenType.DIV_TOK;
        else if (lexeme.equals(FeatureConstants.ASSIGN))
            tokType = TokenType.ASSIGN_TOK;
        else if (lexeme.contains(FeatureConstants.PRINT))
            tokType = TokenType.PRINT_TOK;
        else if (lexeme.contains("("))
            tokType = TokenType.LPARAN_TOK;
        else if (lexeme.contains(")"))
            tokType = TokenType.RPARAN_TOK;
        else if (lexeme.contains(FeatureConstants.END))
            tokType = TokenType.END_TOK;
        else if (lexeme.contains(FeatureConstants.EQ))
            tokType = TokenType.EQ_TOK;
        else if (lexeme.contains(FeatureConstants.NE))
            tokType = TokenType.NE_TOK;
        else if (lexeme.equals(FeatureConstants.GT))
            tokType = TokenType.GT_TOK;
        else if (lexeme.equals(FeatureConstants.GE))
            tokType = TokenType.GE_TOK;
        else if (lexeme.equals(FeatureConstants.LT))
            tokType = TokenType.LT_TOK;
        else if (lexeme.equals(FeatureConstants.LE))
            tokType = TokenType.LE_TOK;
        else if (lexeme.equals(FeatureConstants.IF))
            tokType = TokenType.IF_TOK;
        else if (lexeme.equals(FeatureConstants.ELSE))
            tokType = TokenType.ELSE_TOK;
        else if (lexeme.equals(FeatureConstants.THEN))
            tokType = TokenType.THEN_TOK;
        else if (lexeme.equals(FeatureConstants.FROM))
            tokType = TokenType.FROM_TOK;
        else if (lexeme.equals(FeatureConstants.UNTIL))
            tokType = TokenType.UNTIL_TOK;
        else if (lexeme.equals(FeatureConstants.LOOP))
            tokType = TokenType.LOOP_TOK;
        else
            throw new LexicalException("invalid lexeme at row number " +
                    (lineNumber + 1) + " and column " + (columnNumber + 1) + " [Lexeme] " + lexeme);
        return tokType;
    }

    /**
     * @param s String
     * @return boolean
     */
    private boolean allDigits(String s) {
        int i = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i)))
            i++;
        return i == s.length();
    }

    /**
     * @param line  String
     * @param index int
     * @return String
     */
    private String getLexeme(String line, int index) {
        int i = index;
        while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
            i++;
        return line.substring(index, i);
    }

    /**
     * @param line  String
     * @param index int
     * @return int index
     */
    private int skipWhiteSpace(String line, int index) {
        while (index < line.length() && Character.isWhitespace(line.charAt(index)))
            index++;
        return index;
    }

    /**
     * @return Token
     * @throws LexicalException
     */
    public Token getNextToken() throws LexicalException {
        if (tokens.isEmpty())
            throw new LexicalException("no more tokens");
        return tokens.remove(0);
    }

    /**
     * @return Token
     * @throws LexicalException
     */
    public Token getLookaheadToken() throws LexicalException {
        if (tokens.isEmpty())
            throw new LexicalException("no more tokens");
        return tokens.get(0);
    }

    /**
     * @param ch char
     * @return boolean
     */
    public static boolean isValidIdentifier(char ch) {
        char id = Character.toUpperCase(ch);
        for (Character in : FeatureConstants.ID.toCharArray()) {
            if (id == in)
                return true;
        }
        return false;
    }
}
