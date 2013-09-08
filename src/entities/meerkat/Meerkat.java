package entities.meerkat;

import interfaces.Animatable;
import interfaces.Animator;
import interfaces.Drawable;
import interfaces.GameComponent;
import interfaces.Placer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import main.GameBoard;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import entities.Actor;

/**
 * Responsible for the meerkat's picture, receiving and processing user input
 * and showing and hiding the Meerkat.
 * 
 * @author John Casson
 * 
 */
public class Meerkat extends Actor implements Drawable, GameComponent, Animatable {
	// The speed to pop up at
	final int POPUP_SPEED = 150;

	private Bitmap bm;
	private Bitmap originalBm;
	private PopUpBehavior behavior;
	
	// CopyOnWriteArrayList used to avoid concurrent access + read / write issues
	private List<Animator> animators = new CopyOnWriteArrayList<Animator>();
	Matrix matrix;

	public Meerkat(GameBoard gameboard, Placer placer) {
		super(gameboard, placer);
		this.behavior = new PopUpBehavior(this);
		matrix = new Matrix();
		behavior.showDelayed();
	}
	
	public PopUpBehavior getPopUpBehavior() {
		return this.behavior;
	}

	/**
	 * Sets this Meerkat's image
	 * 
	 * @param Bitmap
	 *            bm This Meerkat's image
	 */
	public void setBitmap(Bitmap bm, int size) {
		// Not necessary: Convert the meerkat size to density independent pixels
		// size = mainActivity.dpToPx(size);
		this.bm = Bitmap.createScaledBitmap(bm, size, size, false);
		this.originalBm = this.bm;
		this.bounds = new Rect(0, 0, size, size);
	}

	/**
	 * Draws this meerkat onto the passed canvas
	 */
	public void draw(Canvas canvas) {
		// If we're visible, draw the meerkat
		if (visible) {
			// Reset any transformations
			matrix.reset();
			// place the meerkat
			matrix.postTranslate((float) getX(), (float) getY());
			// Add each animator in turn
			synchronized(animators) {
				for (Animator a : animators) {
					a.animate();
					bm = a.getBitmap();
					matrix = a.getMatrix();
				}
			}
			canvas.drawBitmap(getBitmap(), matrix, null);
		}
	}


	/**
	 * Places this meerkat on the gameboard at random. Ensures the meerkat doesn't
	 * overlap with any other meerkats. The overlap check ensures this mover
	 * doesn't have the same X co-ordinate as any other movers.
	 * 
	 * @throws Exception
	 *             If this meerkat is already visible
	 */
	public void show() throws Exception {
		super.show();
		register(new PopUpper(this, POPUP_SPEED));
	}

	

	/**
	 * Hides this meerkat
	 */
	public void hide() {
		super.hide();
		// Reset the meerkat image
		bm = originalBm;
		setLocation(-1, -1);
	}

	public Bitmap getBitmap() {
		return bm;
	}

	@Override
	public void play() throws Exception {
		behavior.play();
	}

	@Override
	public void register(Animator a) {
			animators.add(a);
	}

	@Override
	public void unregister(Animator a) {
			animators.remove(a);
	}

	@Override
	public Matrix getMatrix() {
		return matrix;
	}
}