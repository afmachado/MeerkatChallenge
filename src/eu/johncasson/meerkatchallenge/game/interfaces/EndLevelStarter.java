package eu.johncasson.meerkatchallenge.game.interfaces;

import eu.johncasson.meerkatchallenge.game.Score;
import eu.johncasson.meerkatchallenge.levels.Level;

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
