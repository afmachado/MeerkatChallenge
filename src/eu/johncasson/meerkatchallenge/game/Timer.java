package eu.johncasson.meerkatchallenge.game;

import eu.johncasson.meerkatchallenge.game.interfaces.Stopper;
import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.status.Pausable;
import eu.johncasson.meerkatchallenge.game.interfaces.status.Updater;
import android.graphics.Paint;

/**
 * Times the game
 * @author John Casson
 *
 */
public class Timer implements Stopper, GameComponent, Pausable, Updater {
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
	
	/**
	 * If the game is finished, return true
	 * to let other components know the game
	 * needs to stop. 
	 */
	@Override
	public boolean needToStop() {
		return finished;
	}
	
	/**
	 *  Calculate the current game time and check whether the play time
	 *  has exceeded the game time
	 */
	@Override
	public void play() {
		long timePlayed = System.currentTimeMillis() - startTime;
		if(timePlayed > timeLimit) {
			finished = true;
		}
	}
	
	/**
	 * Calculate the time left before the game ends
	 * @return
	 */
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
	 * so we can recalculate the game time on unpause. 
	 */
	@Override
	public void onPause() {
		pauseTime = System.currentTimeMillis();
	}

	/**
	 *  When we're unpaused, update the start time to reflect the
	 *  time spent paused.
	 */
	@Override
	public void onUnPause() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		startTime = startTime + pausedTime;
	}

	/**
	 * Return a string representation of the game time
	 */
	@Override
	public String getUpdate() {
		return Long.toString(getTimeLeft());
	}
}
