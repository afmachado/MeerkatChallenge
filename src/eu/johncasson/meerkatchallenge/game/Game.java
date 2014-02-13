package eu.johncasson.meerkatchallenge.game;

import java.util.ArrayList;

import eu.johncasson.meerkatchallenge.game.interfaces.status.Pausable;
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
	
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();
	
	public Game(Score score, Level level) {
		this.score = score;
		this.level = level;
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
		for(Pausable pausable : pausables) {
			pausable.onPause();
		}
		paused = true;
	}
	
	/**
	 * Unpauses the game
	 */
	public void unPause() {
		for(Pausable pausable : pausables) {
			pausable.onUnPause();
		}
		paused = false;
	}
	
	/**
	 * Is the game paused
	 * @return boolean
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Registers a pausable component with the game
	 * @param pausable
	 */
	public void addPausable(Pausable pausable) {
		pausables.add(pausable);
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
