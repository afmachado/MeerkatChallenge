package eu.johncasson.meerkatchallenge.game.interfaces;

/**
 * Objects of this type can stop a game.
 * @author John Casson
 *
 */
public interface Stopper {
	/**
	 * Returns true when the game should stop.
	 * @return
	 */
	public boolean needToStop();
}
