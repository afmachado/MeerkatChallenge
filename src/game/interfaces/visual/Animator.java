package game.interfaces.visual;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Implemented by classes that can animate
 * @author John Casson
 *
 */
public interface Animator {
	/**
	 * Starts an animation
	 */
	public void animate();
	/**
	 * Gets the animated bitmap
	 * @return
	 */
	public Bitmap getBitmap();
	/**
	 * Gets the matrix to transform the animated bitmap before drawing
	 * @return
	 */
	public Matrix getMatrix();
}
