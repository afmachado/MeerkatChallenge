package entities.meerkat;

import interfaces.Animatable;
import interfaces.Animator;
import interfaces.Drawable;
import interfaces.GameComponent;
import interfaces.Hittable;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import main.GameBoard;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import entities.Actor;

/**
 * Responsible for the meerkat's picture, receiving and processing user input
 * and showing and hiding the Meerkat.
 * 
 * @author John Casson
 * 
 */
public class Meerkat extends Actor implements Drawable, GameComponent,	Animatable, Hittable {
	// The speed to pop up at
	final int POPUP_SPEED = 150;

	private Bitmap bm;
	private Bitmap originalBm;
	private PopUpBehavior behavior;
	private boolean visible = false;
	// CopyOnWriteArrayList used to avoid concurrent access + read / write issues
	private List<Animator> animators = new CopyOnWriteArrayList<Animator>();
	Matrix matrix;

	public Meerkat(GameBoard gameboard) {
		super(gameboard);
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
	 * Detects a hit between a point and 
	 * 
	 * @param ev
	 * @return
	 */
	public boolean isHit(float x, float y) {
		Rect r1 = new Rect(getX(), getY(), getX() + getBounds().width(), getY()
				+ getBounds().height());

		Rect r2 = new Rect((int) x - 5, (int) y - 5, (int) x + 5, (int) y + 5);

		if (r1.intersect(r2)) {
			return true;
		}

		return false;
	}


	/**
	 * Places this meerkat on the gameboard at random. Ensures the mover doesn't
	 * overlap with any other movers. The overlap check ensures this mover
	 * doesn't have the same X co-ordinate as any other movers.
	 * 
	 * @throws Exception
	 *             If this meerkat is already visible
	 */
	public void show() throws Exception {
		if (visible) {
			throw new Exception("Can't show an already shown meerkat");
		}
		Point p = findEmptySpace();
		gameBoard.addMover(this);
		setLocation(p.x, p.y);
		visible = true;
		register(new PopUpper(this, POPUP_SPEED));
	}

	/**
	 * Finds an empty space on the gameboard
	 * 
	 * @return
	 * @throws Exception
	 *             If the meerkat can't be placed
	 */
	private Point findEmptySpace() throws Exception {
		Random r = new Random();
		int minX = 0;
		int minY = 0;
		int x = 0;
		int y = 0;

		int width = gameBoard.getWidth();
		Rect rect1 = getBounds();
		int maxX = width - rect1.width();
		int maxY = gameBoard.getHeight() - getBounds().height();

		int count = 0;
		do {
			x = r.nextInt(maxX - minX + 1) + minX;
			y = r.nextInt(maxY - minY + 1) + minY;
			count++;
			if (count > 100) {
				Log.e("JC", "Can't place meerkat");
				throw new Exception("Can'tplacemeerkat");
			}
		} while (gameBoard.doesOverlap(x, y));
		return new Point(x, y);
	}

	/**
	 * Hides this meerkat
	 */
	public void hide() {
		visible = false;
		gameBoard.removeMover(this);
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

	public boolean getVisible() {
		return visible;
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