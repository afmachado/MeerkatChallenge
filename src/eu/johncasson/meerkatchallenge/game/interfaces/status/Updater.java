package eu.johncasson.meerkatchallenge.game.interfaces.status;

/**
 * Implemented by classes that can provide
 * updates about their status
 *
 */
public interface Updater {
	/**
	 * Gets the current status of the class
	 * @return
	 */
	public String getUpdate();
}
