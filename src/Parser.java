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
        lex = new LexicalAnalyzer(fileName);
    }

    /**
     * @throws LexicalException
     * @throws ParserException
     */
//    public void execute() throws LexicalException, ParserException {

//        Feature program;

//        while (lex.getLookaheadToken().getTokType() != TokenType.EOS_TOK) {
//            program = getProgram();
//            program.evaluate();
//        }
//    }

    /**
     * <feature> -> feature <id> is do <compound> end
     *
     * @return program (Change to feature)
     * @throws LexicalException
     * @throws ParserException
     */
    public Feature parse() throws LexicalException, ParserException {
        Token token = getNextToken();
        match(token, TokenType.FEATURE_TOK);
        getId();
        token = lex.getNextToken();
        match(token, TokenType.IS_TOK);
        token = lex.getNextToken();
        match(token, TokenType.DO_TOK);
        Compound compound = getCompound();
        token = lex.getNextToken();
        match(token, TokenType.END_TOK);
        if (token.getTokType() != TokenType.END_TOK)
            throw new ParserException("garbage at end of program");
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
        Token token = getLookAheadToken();
        while(isValidStart(token)){
            statement = getStatement();
            compound.add(statement);
            token = getLookAheadToken();
        }
        return compound;
// return new Compound(getStatementList());
    }

    private boolean isValidStart(Token token){
        return token.getTokType() == TokenType.ID_TOK || token.getTokType() == TokenType.IF_TOK
                || token.getTokType() == TokenType.PRINT_TOK || token.getTokType() == TokenType.FROM_TOK;
    }

    /**
     * @return statementList
     * @throws LexicalException
     * @throws ParserException
     */
//    private List<Statement> getStatementList() throws LexicalException, ParserException {
//        List<Statement> statementList;
//
//        statementList = new ArrayList<Statement>();
//        while (lex.getLookaheadToken().getTokType() == TokenType.PRINT_TOK
//                || lex.getLookaheadToken().getTokType() == TokenType.ID_TOK
//                || lex.getLookaheadToken().getTokType() == TokenType.IF_TOK
//                || lex.getLookaheadToken().getTokType() == TokenType.FROM_TOK)
//            statementList.add(getStatement());
//        return statementList;
//    }

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
        Token token;
        Id var = getId();
        Expression expression;

        token = lex.getNextToken();
        match(token, TokenType.ASSIGN_TOK);
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
        Token token;
        Expression expression;

        token = lex.getNextToken();
        match(token, TokenType.PRINT_TOK);
        token = lex.getNextToken();
        match(token, TokenType.LPARAN_TOK);
        expression = getExpression();
        token = lex.getNextToken();
        match(token, TokenType.RPARAN_TOK);
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
        Token token;
        BooleanExpression booleanExpression;
        Compound codeBlock1;
        Compound codeBlock2;

        token = lex.getNextToken();
        match(token, TokenType.IF_TOK);
        booleanExpression = getBooleanExpression();
        token = lex.getNextToken();
        match(token, TokenType.THEN_TOK);
        codeBlock1 = getCompound();
        token = lex.getNextToken();
        match(token, TokenType.ELSE_TOK);
        codeBlock2 = getCompound();
        token = lex.getNextToken();
        match(token, TokenType.END_TOK);
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
        Token token;
        Assignment assignment;
        BooleanExpression booleanExpression;
        Compound codeBlock;

        token = lex.getNextToken();
        match(token, TokenType.FROM_TOK);
        assignment = getAssignmentStatement();
        token = lex.getNextToken();
        match(token, TokenType.UNTIL_TOK);
        booleanExpression = getBooleanExpression();
        token = lex.getNextToken();
        match(token, TokenType.LOOP_TOK);
        codeBlock = getCompound();
        token = lex.getNextToken();
        match(token, TokenType.END_TOK);
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
        Token tok;
        Expression expr;

        tok = lex.getLookaheadToken();
        if (tok.getTokType() == TokenType.ADD_TOK
                || tok.getTokType() == TokenType.MUL_TOK
                || tok.getTokType() == TokenType.DIV_TOK
                || tok.getTokType() == TokenType.SUB_TOK)
            expr = getBinaryExpression();
        else if (tok.getTokType() == TokenType.ID_TOK)
            expr = getId();
        else
            expr = getConstant();
        return expr;
    }

    /**
     * @return BinaryExpression(op, expr1, expr2);
     * @throws ParserException
     * @throws LexicalException
     */
    private Expression getBinaryExpression() throws ParserException, LexicalException {
        Token tok;
        ArithmeticOperator op;

        tok = lex.getNextToken();
        if (tok.getTokType() == TokenType.ADD_TOK) {
            match(tok, TokenType.ADD_TOK);
            op = ArithmeticOperator.ADD_OP;
        } else if (tok.getTokType() == TokenType.MUL_TOK) {
            match(tok, TokenType.MUL_TOK);
            op = ArithmeticOperator.MUL_OP;
        } else if (tok.getTokType() == TokenType.SUB_TOK) {
            match(tok, TokenType.SUB_TOK);
            op = ArithmeticOperator.SUB_OP;
        } else if (tok.getTokType() == TokenType.DIV_TOK) {
            match(tok, TokenType.DIV_TOK);
            op = ArithmeticOperator.DIV_OP;
        } else
            throw new ParserException(" operator expected at row " +
                    tok.getRowNumber() + " and column " + tok.getColumnNumber());
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
        Token token;
        RelationalOperator op;

        token = lex.getNextToken();
        if (token.getTokType() == TokenType.LE_TOK) {
            match(token, TokenType.LE_TOK);
            op = RelationalOperator.LE_OP;
        } else if (token.getTokType() == TokenType.LT_TOK) {
            match(token, TokenType.LT_TOK);
            op = RelationalOperator.LT_OP;
        } else if (token.getTokType() == TokenType.GE_TOK) {
            match(token, TokenType.GE_TOK);
            op = RelationalOperator.GE_OP;
        } else if (token.getTokType() == TokenType.GT_TOK) {
            match(token, TokenType.GT_TOK);
            op = RelationalOperator.GT_OP;
        } else if (token.getTokType() == TokenType.EQ_TOK) {
            match(token, TokenType.EQ_TOK);
            op = RelationalOperator.EQ_OP;
        } else if (token.getTokType() == TokenType.NE_TOK) {
            match(token, TokenType.NE_TOK);
            op = RelationalOperator.NE_TOK;
        } else
            throw new ParserException(" operator expected at row " +
                    token.getRowNumber() + " and column " + token.getColumnNumber());
        Expression expression1 = getExpression();
        Expression expression2 = getExpression();
        return new BooleanExpression(op, expression1, expression2);
    }

    /**
     * @return Id
     * @throws LexicalException
     * @throws ParserException
     */
    private Id getId() throws LexicalException, ParserException {
        Token token;

        token = lex.getNextToken();
        match(token, TokenType.ID_TOK);
        return new Id(token.getLexeme().charAt(0));
    }

    /**
     * @return Constant
     * @throws ParserException
     * @throws LexicalException
     */
    private Expression getConstant() throws ParserException, LexicalException {
        Token tok;
        int value;

        tok = lex.getNextToken();
        match(tok, TokenType.CONST_TOK);
        value = Integer.parseInt(tok.getLexeme());
        return new Constant(value);
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
