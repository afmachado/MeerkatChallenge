package game.interfaces;

import levels.Level;
import game.Score;

/**
 * Implemented by classes that can start the end
 * level procedure 
 */
public interface EndLevelStarter {
	/**
	 * Start the end level procedure
	 * @param score
	 * @param level
	 */
	void startEndLevel(Score score, Level level);
}
