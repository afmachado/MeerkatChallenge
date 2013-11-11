package eu.johncasson.meerkatchallenge.game.actor;

import eu.johncasson.meerkatchallenge.game.actor.interfaces.Animatable;
import eu.johncasson.meerkatchallenge.game.interfaces.visual.Animator;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Pops up an animatable object
 * 
 * @author John Casson
 */
public class PopUpper implements Animator {
	/** 
	 * The original bitmap (never changes)
	 */
	private Bitmap originalBm;
	/** 
	 * The animated bitmap (changes with each frame)
	 */
	private Bitmap animBm;
	/**
	 * The matrix used to draw the bitmap
	 */
	private Matrix matrix = new Matrix();
	/**
	 * The animation's start time
	 */
	private long startTime;
	/**
	 * The entity to animate
	 */
	private Animatable animatable;
	/**
	 * The time in which to complete a pop up animation
	 */
	private int popUpTime;
	/**
	 * Indicates that the animation is ready to go
	 */
	private boolean ready = false;
	/**
	 * Indicates the animation is finished
	 */
	private boolean finished = false;

	public PopUpper(Animatable animatable, int popUpSpeed) {
		this.animatable = animatable;
		this.originalBm = animatable.getBitmap();
		this.popUpTime = popUpSpeed;
		startTime = System.currentTimeMillis();
		ready = true;
	}

	/**
	 * Animates the passed animatable
	 */
	public synchronized void animate() {
		// don't start animating till the constructor has completed
		if(!ready) {
			return;
		}
		matrix = animatable.getMatrix();
		long now = System.currentTimeMillis();
		float difference = now - startTime; // time in ms between starting pop
											// up and now
		// Popping up takes popUpSpeed milliseconds. Get the percent of time between
		// the start time and popUpSpeed seconds later
		float popUpPercent = difference / popUpTime ;
		popUpPercent += 0.01; // Ensure there's always something to be drawn

		// If the animation is finished
		if (popUpPercent >= 1) {
			animBm = originalBm;
			finished = true;
			return;
		}
		
		// Calculates which "slice" of the original bitmap to show
		int slice = (int) (originalBm.getHeight() - (originalBm.getHeight() * popUpPercent));
		animBm = Bitmap.createBitmap(originalBm, 0, 0, originalBm.getWidth(),
				originalBm.getHeight() - slice);

		// Move the image "up" so it looks like it's appearing from the bottom
		matrix.postTranslate(0, slice);
	}

	/**
	 * Returns the bitmap as it's drawn
	 */
	@Override
	public Bitmap getBitmap() {
		// Ensure the original bitmap is always sent back when this animation ends
		if(finished) {
			animatable.stopAnimation(this);
		}
		return animBm;
	}

	/**
	 * Returns the matrix to transform the bitmap during drawing
	 */
	@Override
	public Matrix getMatrix() {
		return matrix;
	}
}
