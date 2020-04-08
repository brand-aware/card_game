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
		if(args.length < 1){
			System.out.println("java driver <rootDir>");
			System.exit(1);
		}
		Properties properties = new Properties(args[0]);
		Board board = new Board();
		board.init(properties);
		Thread thread = new Thread(new Mover(board.getRulesEngine()));
		thread.start();
	}
}