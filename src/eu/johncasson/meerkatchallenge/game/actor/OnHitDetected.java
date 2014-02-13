package eu.johncasson.meerkatchallenge.game.actor;

/**
 * Implemented by classes that want to be notified
 * when an object is hit
 * @author John Casson
 *
 */
public interface OnHitDetected {
	/**
	 * Called when a hit is detected
	 */
	public void onHit();
}
