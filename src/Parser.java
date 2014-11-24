import java.io.FileNotFoundException;

/**
 * The Parsing class should detect any syntactical or semantic errors.
 */
public class Parser {

    private LexicalAnalyzer lex;


    /**
     * @param fileName -
     * @throws FileNotFoundException
     * @throws LexicalException
     */
    public Parser(String fileName) throws FileNotFoundException, LexicalException {
        if (fileName == null || fileName.length() == 0)
            throw new IllegalArgumentException("[Parser] invalid file name argument");
        init(fileName);
    }

    private void init(String fileName) throws FileNotFoundException, LexicalException {
        lex = new LexicalAnalyzer(fileName);
    }

    /**
     * <feature> -> feature <id> is do <compound> end
     *
     * @return program (Change to feature)
     * @throws LexicalException
     * @throws ParserException
     */
    public Feature parse() throws LexicalException, ParserException {
        match(getNextToken(), TokenType.FEATURE_TOK);
        getId();
        match(getNextToken(), TokenType.IS_TOK);
        match(getNextToken(), TokenType.DO_TOK);
        Compound compound = getCompound();
        match(getLookAheadToken(), TokenType.END_TOK);
        if (getNextToken().getTokType() != TokenType.END_TOK)
            throw new ParserException("garbage at end of program : ");
        return new Feature(compound);
    }

    /**
     * <compound> -> <statement> | <compound>  <statement>
     *
     * @return Statements list (change to compound)
     * @throws LexicalException
     * @throws ParserException
     */
    private Compound getCompound() throws LexicalException, ParserException {
        Compound compound = new Compound();
        Statement statement = getStatement();
        compound.add(statement);

        while(isValidStart(getLookAheadToken())){
            statement = getStatement();
            compound.add(statement);
            getLookAheadToken();
        }
        return compound;
    }

    private boolean isValidStart(Token token){
        return token.getTokType() == TokenType.ID_TOK || token.getTokType() == TokenType.IF_TOK
                || token.getTokType() == TokenType.PRINT_TOK || token.getTokType() == TokenType.FROM_TOK;
    }

    /**
     * <statement> -> <assignment_statement> | <print_statement> | <if_statement> | <loop_statement>
     *
     * @return statement
     * @throws LexicalException
     * @throws ParserException
     */
    private Statement getStatement() throws LexicalException, ParserException {
        Token token;
        Statement statement;

        token = getLookAheadToken();
        switch (token.getTokType()) {
            case ID_TOK:
                statement = getAssignmentStatement();
                break;
            case PRINT_TOK:
                statement = getPrintStatement();
                break;
            case IF_TOK:
                statement = getIfStatement();
                break;
            case FROM_TOK:
                statement = getLoopStatement();
                break;
            default:
                throw new LexicalException("statement initializing lexeme expected, " + token.getLexeme());
        }
        return statement;
    }

    /**
     * <assignment_statement> -> <id> assignment_operator <expression>
     *
     * @return Assignment
     * @throws LexicalException
     * @throws ParserException
     */
    private Assignment getAssignmentStatement() throws LexicalException, ParserException {
        Id var = getId();
        Expression expression;

        match(getNextToken(), TokenType.ASSIGN_TOK);
        expression = getExpression();
        return new Assignment(var, expression);
    }

    /**
     * <print_statement> -> print  ( <expression>  )
     *
     * @return PrintStatement
     * @throws ParserException
     * @throws LexicalException
     */
    private PrintStatement getPrintStatement() throws ParserException, LexicalException {
        Expression expression;

        match(getNextToken(), TokenType.PRINT_TOK);
        match(getNextToken(), TokenType.LPARAN_TOK);
        expression = getExpression();
        match(getNextToken(), TokenType.RPARAN_TOK);
        return new PrintStatement(expression);
    }

    /**
     * <if_statement> -> if <boolean_expression> then <compound> else <compound> end
     *
     * @return If_Statement
     * @throws LexicalException
     * @throws ParserException
     */
    private If_Statement getIfStatement() throws LexicalException, ParserException {
        BooleanExpression booleanExpression;
        Compound codeBlock1;
        Compound codeBlock2;

        match(getNextToken(), TokenType.IF_TOK);
        booleanExpression = getBooleanExpression();
        match(getNextToken(), TokenType.THEN_TOK);
        codeBlock1 = getCompound();
        match(getNextToken(), TokenType.ELSE_TOK);
        codeBlock2 = getCompound();
        match(getNextToken(), TokenType.END_TOK);
        return new If_Statement(booleanExpression, codeBlock1, codeBlock2);
    }

    /**
     * <loop_statement> -> from <assignment_statement> until <boolean_expression> loop <compound> end
     *
     * @return LoopStatement
     * @throws LexicalException
     * @throws ParserException
     */
    private LoopStatement getLoopStatement() throws LexicalException, ParserException {
        Assignment assignment;
        BooleanExpression booleanExpression;
        Compound codeBlock;

        match(getNextToken(), TokenType.FROM_TOK);
        assignment = getAssignmentStatement();
        match(getNextToken(), TokenType.UNTIL_TOK);
        booleanExpression = getBooleanExpression();
        match(getNextToken(), TokenType.LOOP_TOK);
        codeBlock = getCompound();
        match(getNextToken(), TokenType.END_TOK);
        return new LoopStatement(assignment, booleanExpression, codeBlock);
    }

    /**
     * <expression> -> <arithmetic_operator> <expression> <expression> | <id> | literal_integer
     *
     * @return expr
     * @throws ParserException
     * @throws LexicalException
     */
    private Expression getExpression() throws ParserException, LexicalException {
        Expression expr;

        switch(getNextTokenType()){
            case MUL_TOK:
            case DIV_TOK:
            case SUB_TOK:
            case ADD_TOK:
                expr = getBinaryExpression();
                break;
            case ID_TOK:
                expr = getId();
                break;
            default:
                expr = getConstant();
        }
        return expr;
    }

    /**
     * @return BinaryExpression(op, expr1, expr2);
     * @throws ParserException
     * @throws LexicalException
     */
    private Expression getBinaryExpression() throws ParserException, LexicalException {
        ArithmeticOperator op;

        if (getNextTokenType() == TokenType.ADD_TOK) {
            match(getNextToken(), TokenType.ADD_TOK);
            op = ArithmeticOperator.ADD_OP;
        } else if (getNextTokenType() == TokenType.MUL_TOK) {
            match(getNextToken(), TokenType.MUL_TOK);
            op = ArithmeticOperator.MUL_OP;
        } else if (getNextTokenType() == TokenType.SUB_TOK) {
            match(getNextToken(), TokenType.SUB_TOK);
            op = ArithmeticOperator.SUB_OP;
        } else if (getNextTokenType() == TokenType.DIV_TOK) {
            match(getNextToken(), TokenType.DIV_TOK);
            op = ArithmeticOperator.DIV_OP;
        } else
            throw new ParserException(" operator expected at row " +
                    getNextToken().getRowNumber() + " and column " + getNextToken().getColumnNumber());
        Expression expr1 = getExpression();
        Expression expr2 = getExpression();
        return new BinaryExpression(op, expr1, expr2);
    }

    /**
     * <boolean_expression> → <relational_operator> <expression> <expression>
     *
     * @return BooleanExpression
     * @throws LexicalException
     * @throws ParserException
     */
    private BooleanExpression getBooleanExpression() throws LexicalException, ParserException {
        RelationalOperator op;

        if (getNextTokenType() == TokenType.LE_TOK) {
            match(getNextToken(), TokenType.LE_TOK);
            op = RelationalOperator.LE_OP;
        } else if (getNextTokenType() == TokenType.LT_TOK) {
            match(getNextToken(), TokenType.LT_TOK);
            op = RelationalOperator.LT_OP;
        } else if (getNextTokenType() == TokenType.GE_TOK) {
            match(getNextToken(), TokenType.GE_TOK);
            op = RelationalOperator.GE_OP;
        } else if (getNextTokenType() == TokenType.GT_TOK) {
            match(getNextToken(), TokenType.GT_TOK);
            op = RelationalOperator.GT_OP;
        } else if (getNextTokenType() == TokenType.EQ_TOK) {
            match(getNextToken(), TokenType.EQ_TOK);
            op = RelationalOperator.EQ_OP;
        } else if (getNextTokenType() == TokenType.NE_TOK) {
            match(getNextToken(), TokenType.NE_TOK);
            op = RelationalOperator.NE_TOK;
        } else
            throw new ParserException(" operator expected at row " +
                    getNextToken().getRowNumber() + " and column " + getNextToken().getColumnNumber());
        return new BooleanExpression(op, getExpression(), getExpression());
    }

    /**
     * @return Id
     * @throws LexicalException
     * @throws ParserException
     */
    private Id getId() throws LexicalException, ParserException {
        match(getLookAheadToken(), TokenType.ID_TOK);
        return new Id(getNextToken().getLexeme().charAt(0));
    }

    /**
     * @return Constant
     * @throws ParserException
     * @throws LexicalException
     */
    private Expression getConstant() throws ParserException, LexicalException {
        match(getLookAheadToken(), TokenType.CONST_TOK);
        return new Constant(Integer.parseInt(getNextToken().getLexeme()));
    }

    /**
     * @param tok     - The token to print.
     * @param tokType - The token type to print.
     * @throws ParserException
     */
    private void match(Token tok, TokenType tokType) throws ParserException {
        if (tok.getTokType() != tokType)
            throw new ParserException(tokType.name() + " expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber() + " ,actual: " + tok.getLexeme());
    }

    private TokenType getTokenType() throws ParserException {
        TokenType tokenType;
        try {
            tokenType = lex.getNextToken().getTokType();
        } catch (LexicalException e) {
            throw new ParserException(e.toString());
        }
        return tokenType;
    }

    private TokenType getNextTokenType() throws ParserException {
        TokenType tokenType;
        try {
            tokenType = lex.getLookaheadToken().getTokType();
        } catch (LexicalException e) {
            throw new ParserException(e.toString());
        }
        return tokenType;
    }

    private Token getNextToken() throws ParserException {
        Token token;
        try {
            token = lex.getNextToken();
        } catch (LexicalException e) {
            throw new ParserException(e.getMessage());
        }
        return token;
    }

    private Token getLookAheadToken() throws ParserException {
        Token token;
        try {
            token = lex.getLookaheadToken();
        } catch (LexicalException e) {
            throw new ParserException(e.getMessage());
        }
        return token;
    }
}
