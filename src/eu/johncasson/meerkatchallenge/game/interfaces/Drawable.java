package eu.johncasson.meerkatchallenge.game.interfaces;

import android.graphics.Canvas;

/**
 * Implemented by classes that can be drawn
 * @author John Casson
 *
 */
public interface Drawable {
	/**
	 * Draws to a canvas
	 * @param canvas
	 */
	public void draw(Canvas canvas);
}
