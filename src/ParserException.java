/**
 * The ParcelException class is used to report any paring errors.
 */
public class ParserException extends Exception
{

    /**
     *
     * @param string -
     */
	public ParserException(String string)
	{
		// TODO Auto-generated constructor stub
        System.out.println("[Parser Exception] " + string);
	}

}
