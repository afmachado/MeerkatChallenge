package game.entities;

import game.interfaces.Animatable;
import game.interfaces.Animator;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Pops up an animatable object
 * 
 * @author John Casson
 */
public class PopUpper implements Animator {
	// The original bitmap (never changes)
	private Bitmap originalBm;
	// The animated bitmap (changes with each frame)
	private Bitmap animBm;
	private Matrix matrix = new Matrix();
	private long startTime;
	private Animatable animatable;
	// Pop up speed in milliseconds
	private int popUpSpeed;
	// indicates this animation is ready to fire.
	private boolean ready = false;
	private boolean finished = false;

	public PopUpper(Animatable animatable, int popUpSpeed) {
		this.animatable = animatable;
		this.originalBm = animatable.getBitmap();
		this.popUpSpeed = popUpSpeed;
		startTime = System.currentTimeMillis();
		ready = true;
	}

	public synchronized void animate() {
		// don't start animating till the constructor has completed
		if(!ready) {
			return;
		}
		matrix = animatable.getMatrix();
		// Calculate the "slice" height of animatable to show
		long now = System.currentTimeMillis();
		float difference = now - startTime; // time in ms between starting pop
											// up and now
		// Popping up takes popUpSpeed milliseconds. Get the percent of time between
		// the start time and popUpSpeed seconds later
		float popUpPercent = difference / popUpSpeed ;
		popUpPercent += 0.01; // Ensure there's always some height

		if (popUpPercent >= 1) {
			animBm = originalBm;
			finished = true;
			return;
		}
		
		int slice = (int) (originalBm.getHeight() - (originalBm.getHeight() * popUpPercent));
		animBm = Bitmap.createBitmap(originalBm, 0, 0, originalBm.getWidth(),
				originalBm.getHeight() - slice);

		// Move the image "up" so it looks like it's appearing from the bottom
		matrix.postTranslate(0, slice);
	}

	@Override
	public Bitmap getBitmap() {
		// Ensure the original bitmap is always sent back when this animation ends
		if(finished) {
			animatable.unregisterAnimation(this);
		}
		return animBm;
	}

	@Override
	public Matrix getMatrix() {
		return matrix;
	}
}
