package eu.johncasson.meerkatchallenge.game;

import eu.johncasson.meerkatchallenge.game.interfaces.status.Pausable;

import java.util.ArrayList;

/**
 * Maintains the basic game state (started, paused, pausable components)
 * @author John Casson
 *
 */
public class Game {
	public boolean paused = true;
	public boolean started = false;
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();

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
}
