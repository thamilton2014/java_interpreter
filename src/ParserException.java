/**
 * The ParcelException class
 */
public class ParserException extends Exception
{

    /**
     *
     * @param string -
     */
	public ParserException(String string)
	{
        System.out.println("[Parser Exception] " + string);
	}

}
