package core.doc;

public interface IProperties {

	/**
	 * Returns the directory that contains all
	 * cards for the given suit.
	 * 
	 * @param String suit
	 * @return String dir
	 */
	public String suitPath(String suit);
	
	/**
	 * Returns the file path for the image used
	 * generically for face-down cards.
	 * 
	 * @return String path
	 */
	public String getCoverPath();
	
	/**
	 * Returns the file location for the placeholder
	 * image.  This is used to show where cards
	 * will be placed.
	 * 
	 * @return String path
	 */
	public String getEmptyPath();
	
	/**
	 * Returns the path to the game logo.
	 * 
	 * @return String path
	 */
	public String getLogo();
	
}
