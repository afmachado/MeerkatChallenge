package game.entities;

import game.interfaces.Pausable;

import java.util.ArrayList;

public class Game {
	public boolean paused = true;
	public boolean started = false;
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();

	public void start() {
		paused = false;
		started = true;
	}
	
	public void pause() {
		for(Pausable pausable : pausables) {
			pausable.onPause();
		}
		paused = true;
	}
	
	public void unPause() {
		for(Pausable pausable : pausables) {
			pausable.onUnPause();
		}
		paused = false;
	}
	
	public boolean isPaused() {
		return paused;
	}

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
