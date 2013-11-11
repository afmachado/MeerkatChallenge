package eu.johncasson.meerkatchallenge.game;

import eu.johncasson.meerkatchallenge.game.interfaces.EndLevelStarter;
import eu.johncasson.meerkatchallenge.game.interfaces.status.OnStopListener;
import eu.johncasson.meerkatchallenge.levels.Level;

/**
 * Starts the process for ending a level
 * @author John Casson
 *
 */
public class ShowLevelEnd implements OnStopListener {
	private Score score;
	private Level level;
	EndLevelStarter endLevelStarter;
	
	public ShowLevelEnd(EndLevelStarter endLevelStarter, Score score, Level level) {
		this.endLevelStarter = endLevelStarter;
		this.score = score;
		this.level = level;
	}
	
	/**
	 * Start the end level process when we receive
	 * an onStop message.
	 */
	@Override
	public void onStop() {
		endLevelStarter.startEndLevel(score, level);
	}
	
}
