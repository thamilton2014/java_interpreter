/**
 *
 */
public class Id implements Expression
{

	private char ch;
	
	/**
	 * @param ch - must be a valid identifier
	 * @throws IllegalArgumentException if ch is not a valid identifier
	 */
	public Id(char ch)
	{
		if (!LexicalAnalyzer.isValidIdentifier(ch))
			throw new IllegalArgumentException ("character is not a valid identifier");
		this.ch = Character.toUpperCase(ch);
	}

    /**
     *
     * @return ch
     */
	@Override
	public int evaluate()
	{
		return Memory.fetch (ch);
	}

    /**
     *
     * @return ch
     */
	public char getChar ()
	{
		return ch;
	}
}
