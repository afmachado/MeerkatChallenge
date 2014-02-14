package eu.johncasson.meerkatchallenge.game.interfaces;

/**
 * Implemented by classes that want
 * to be notified when the game stops
 * @author John Casson
 *
 */
public interface OnStopListener {
	/**
	 * What to do when the game is stopped
	 */
	public void onStop();
}