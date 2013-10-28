package game.interfaces.status;

/**
 * Implemented by classes that can be paused
 * and unpaused.
 * @author John Casson
 *
 */
public interface Pausable {
	/**
	 * When the game is paused
	 */
	public void onPause();
	/**
	 * When the game is unpaused
	 */
	public void onUnPause();
}
