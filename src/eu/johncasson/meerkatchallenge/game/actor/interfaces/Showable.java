package eu.johncasson.meerkatchallenge.game.actor.interfaces;


/**
 * Implemented by classes that can be shown
 * @author John Casson
 *
 */
public interface Showable {
	/**
	 * Is this object visible
	 * @return
	 */
	public boolean isVisible();
	/**
	 * Show the object
	 * @throws Exception
	 */
	public void show();
	/**
	 * Hide the object
	 * @throws Exception
	 */
	public void hide();
}
