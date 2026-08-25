/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
import ui.Board;
import core.Mover;
import core.Properties;

public class driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		Board board = new Board();
		board.init(properties);
		Thread thread = new Thread(new Mover(board.getRulesEngine()));
		thread.start();
	}
}