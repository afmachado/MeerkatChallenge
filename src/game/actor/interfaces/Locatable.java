package game.actor.interfaces;

import android.graphics.Rect;

/**
 * Implemented by classes that can be located
 * @author John Casson
 *
 */
public interface Locatable {
	/**
	 * Sets the location of the class
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y);
	/**
	 * Returns the size of the locatable
	 * @return
	 */
	public Rect getBounds();
}
