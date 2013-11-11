package eu.johncasson.meerkatchallenge.game.actor.interfaces;

import eu.johncasson.meerkatchallenge.game.interfaces.visual.Animator;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Implemented by classes that can be animated
 * @author John Casson
 *
 */
public interface Animatable {
	/**
	 * Starts an animation
	 * @param a
	 */
	public void startAnimation(Animator a);
	/**
	 * Ends an animation
	 * @param a
	 */
	public void stopAnimation(Animator a);
	/**
	 * Gets the bitmap to be animated
	 * @param a
	 */
	public Bitmap getBitmap();
	/**
	 * Gets the matrix used to draw this entity
	 * @param a
	 */
	public Matrix getMatrix();
}
