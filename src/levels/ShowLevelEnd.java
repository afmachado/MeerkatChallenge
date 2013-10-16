package levels;

import game.entities.Score;
import game.interfaces.EndLevelStarter;
import game.interfaces.StopAction;

/**
 * Shows the level end screen
 * @author John Casson
 *
 */
public class ShowLevelEnd implements StopAction {
	private Score score;
	private Level level;
	EndLevelStarter endLevelStarter;
	
	public ShowLevelEnd(EndLevelStarter endLevelStarter, Score score, Level level) {
		this.endLevelStarter = endLevelStarter;
		this.score = score;
		this.level = level;
	}
	
	@Override
	public void onStop() {
		endLevelStarter.startEndLevel(score, level);
	}
	
}
