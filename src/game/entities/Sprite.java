package game.entities;

import game.interfaces.Animatable;
import game.interfaces.Animator;

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
public class Sprite implements Animatable {
	private Bitmap bm;
	// CopyOnWriteArrayList used to avoid concurrent access + read / write
	// issues
	private List<Animator> animators = new CopyOnWriteArrayList<Animator>();
	Matrix matrix;

	public Sprite() {
		matrix = new Matrix();
	}

	/**
	 * Sets this Meerkat's image
	 * 
	 * @param Bitmap
	 *            bm This Meerkat's image
	 */
	public void setBitmap(Bitmap bm, int size) {
		this.bm = Bitmap.createScaledBitmap(bm, size, size, false);
	}

	/**
	 * Draws this sprite onto the passed canvas
	 */
	public void draw(Canvas canvas, float x, float y) {
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

	public Bitmap getBitmap() {
		return bm;
	}

	@Override
	public void registerAnimation(Animator a) {
		animators.add(a);
	}

	@Override
	public void unregisterAnimation(Animator a) {
		animators.remove(a);
	}

	@Override
	public Matrix getMatrix() {
		return matrix;
	}
}