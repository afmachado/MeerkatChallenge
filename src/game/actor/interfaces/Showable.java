package game.actor.interfaces;


/**
 * Implemented by classes that can be shown
 * @author hqs71687
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
