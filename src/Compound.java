import java.util.ArrayList;
import java.util.List;

/**
 * The CodeBlock class
 */
public class Compound {

    private List<Statement> statementList;

    /**
     *
     */
    public Compound(){
        statementList = new ArrayList<Statement>();
    }
//    public Compound(List<Statement> statementList) {
//        if(statementList == null)
//            throw new IllegalArgumentException("[CodeBlock] statementList is null");
//        this.statementList = statementList;
//    }

    public void add(Statement statement){
        if (statement == null)
            throw new IllegalArgumentException("[Compound] null Statement argument");
        statementList.add(statement);
    }

    /**
     *
     */
    public void execute() {
        for(Statement statement : statementList)
        statement.execute();
    }
}
