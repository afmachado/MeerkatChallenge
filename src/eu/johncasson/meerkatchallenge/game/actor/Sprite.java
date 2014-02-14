package eu.johncasson.meerkatchallenge.game.actor;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Responsible for storage of and animating of an image.
 * 
 * @author John Casson
 * 
 */
class Sprite {
	private Bitmap bm;
	/**
	 * CopyOnWriteArrayList used to avoid concurrent access + read / write
	 */
	private List<Animator> animators = new CopyOnWriteArrayList<Animator>();
	Matrix matrix;

	public Sprite() {
		matrix = new Matrix();
	}

	/**
	 * Sets this sprite's image
	 * 
	 * @param bitmap This sprite's image
	 */
	void setBitmap(Bitmap bitmap, int size) {
		this.bm = Bitmap.createScaledBitmap(bitmap, size, size, false);
	}

	/**
	 * Draws this sprite onto the passed canvas at the passed location
	 */
	void draw(Canvas canvas, float x, float y) {
		// Reset any transformations
		matrix.reset();
		// place the sprite
		matrix.postTranslate(x, y);
		// Add each animator in turn
		for (Animator a : animators) {
			a.animate();
			bm = a.getBitmap();
			matrix = a.getMatrix();
		}
		canvas.drawBitmap(getBitmap(), matrix, null);
	}

	/**
	 * Returns this sprite's bitmap
	 */
	Bitmap getBitmap() {
		return bm;
	}

	/**
	 * Starts an animation on this sprite
	 */
	void startAnimation(Animator a) {
		animators.add(a);
	}

	/**
	 * Stops an animation on this sprite
	 */
	void stopAnimation(Animator a) {
		animators.remove(a);
	}

	/**
	 * Returns the matrix used to draw this sprite
	 */
	Matrix getMatrix() {
		return matrix;
	}
}