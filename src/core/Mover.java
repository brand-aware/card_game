/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

public class Mover implements Runnable{
	
	private RulesEngine rulesEngine;
	
	public Mover(RulesEngine re){
		rulesEngine = re;
	}

	@Override
	public void run() {
		while(true){
			if(!rulesEngine.isGameFinished()){
				rulesEngine.doMove();
			}
			
			try {
				Thread.sleep(260);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
