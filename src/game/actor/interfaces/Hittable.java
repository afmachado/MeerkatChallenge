package game.actor.interfaces;

import android.graphics.Rect;

/**
 * Implemented by classes that can be hit
 * @author John Casson
 *
 */
public interface Hittable {
	/**
	 * Does the passed rect overlap this entity?
	 * @param rectangle
	 * @return
	 */
	public boolean isOverlapping(Rect rectangle);
}
