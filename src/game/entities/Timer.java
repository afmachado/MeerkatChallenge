package game.entities;

import game.interfaces.GameComponent;
import game.interfaces.Pausable;
import game.interfaces.StopCondition;
import android.graphics.Paint;

public class Timer implements StopCondition, GameComponent, Pausable, GivesUpdates {
	boolean finished = false;

	long startTime;
	long timeLimit;
	long pauseTime;
	Paint textPaint;
	
	/**
	 * Stops the game after a specified time
	 * @param gameTime The time to stop after
	 */
	public Timer(int gameTime) {
		this.timeLimit = gameTime;
		startTime = System.currentTimeMillis();
	}
	
	@Override
	public boolean stopCondition() {
		return finished;
	}
	
	// Calculate the current game time and check whether the play time
	// has exceeded the game time
	@Override
	public void play() throws Exception {
		long timePlayed = System.currentTimeMillis() - startTime;
		if(timePlayed > timeLimit) {
			finished = true;
		}
	}
	
	public long getTimeLeft() {
		long timeTaken = System.currentTimeMillis() - startTime;
		// We add 500 to the visible output 
		// so the timer starts from e.g. 10 instead of 9
		long timeLeft = timeLimit + 500 - timeTaken;
		timeLeft = timeLeft / 1000;
		return timeLeft;
	}
	
	/** 
	 * When the game is paused, store the time of the pause 
	 */
	@Override
	public void onPause() {
		pauseTime = System.currentTimeMillis();
	}

	/**
	 *  When we're unpaused, update the start time to reflect the
	 *  time spent paused
	 */
	@Override
	public void onUnPause() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		startTime = startTime + pausedTime;
	}

	@Override
	public String getStatus() {
		return Long.toString(getTimeLeft());
	}
}
