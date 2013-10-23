package game.interfaces.status;

/**
 * Implemented by classes that can be paused
 * and unpaused.
 * @author John Casson
 *
 */
public interface Pausable {
	public void onPause();
	public void onUnPause();
}
