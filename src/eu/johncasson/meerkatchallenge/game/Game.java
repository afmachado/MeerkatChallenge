package eu.johncasson.meerkatchallenge.game;

import eu.johncasson.meerkatchallenge.game.loops.GameLoop;
import eu.johncasson.meerkatchallenge.levels.Level;

/**
 * Maintains the basic game state (started, paused, pausable components)
 * @author John Casson
 *
 */
public class Game {
	public boolean paused = true;
	public boolean started = false;
	private final Score score;
	private final Level level;
	private final GameLoop gameLoop;
	
	public Game(Score score, Level level, GameLoop gameLoop) {
		this.score = score;
		this.level = level;
		this.gameLoop = gameLoop;
	}

	/**
	 * Starts the game
	 */
	public void start() {
		paused = false;
		started = true;
	}
	
	/**
	 * Pauses the game
	 */
	public void pause() {
		gameLoop.onPause();
	}
	
	/**
	 * Unpauses the game
	 */
	public void unPause() {
		gameLoop.onUnPause();
	}
	
	/**
	 * Is the game paused
	 * @return boolean
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Has the game been started?
	 * @return Boolean
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * Gets the game's score as an integer
	 * @return integer representing the game's score
	 */
	public int getScore() {
		return score.get();
	}

	/**
	 * Gets the current level
	 * @return
	 */
	public Level getLevel() {
		return level;
	}
}
