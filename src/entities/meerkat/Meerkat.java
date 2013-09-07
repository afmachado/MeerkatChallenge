package entities.meerkat;

import interfaces.Actor;
import interfaces.Animatable;
import interfaces.Animator;
import interfaces.Drawable;
import interfaces.ReceivesInput;

import java.util.ArrayList;
import java.util.Random;

import main.GameActivity;
import main.GameBoard;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import entities.Mover;
import entities.Score;

/**
 * Responsible for the meerkat's picture, receiving and processing user input
 * and showing and hiding the Meerkat.
 * @author John Casson
 *
 */
public class Meerkat extends Mover implements ReceivesInput, Drawable, Actor, Animatable {
	// The meerkat size in density independent pixels
	final int MEERKAT_SIZE = 50;
	// The speed to pop up at
	final int POPUP_SPEED = 150;
	
	private Bitmap bm;
	private PopUpBehavior behavior;
	public GameActivity mainActivity;
	private boolean visible = false;
	private ArrayList<Animator> animators = new ArrayList<Animator>();
	Matrix matrix;
	
	public Meerkat(GameBoard gameboard, Score score, GameActivity ma) {
		super(gameboard);
		this.behavior = new PopUpBehavior(this, score);
		this.mainActivity = ma;
		matrix = new Matrix();
		behavior.showDelayed();
	}
	
	/**
	 * Sets this Meerkat's image
	 * @param Bitmap bm This Meerkat's image
	 */
	public void setBitmap(Bitmap bm) {
		// Convert the meerkat size to density independent pixels
		int size = mainActivity.dpToPx(MEERKAT_SIZE);
		this.bm = Bitmap.createScaledBitmap(bm, size, size, false);
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
			for(Animator a : animators) {
				a.animate(getBitmap(), matrix);
				bm = a.getBitmap();
				matrix = a.getMatrix();
			}
			canvas.drawBitmap(getBitmap(), matrix, null);
		}
	}

	/**
	 *  On user input, detect whether this Meerkat has been hit
	 */
	@Override
	public void onInput(View v, MotionEvent ev) {
		// If we're not visible, don't respond to input events
		if(!visible) {
			return;
		}
		final int action = ev.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			if (detectCollision(ev.getX(), ev.getY())) {
				behavior.hit();
			}
			break;
		}
	}

	/**
	 * Detects a collision between a point and this meerkat
	 * @param ev
	 * @return
	 */
	public boolean detectCollision(float x, float y) {
		Rect r1 = new Rect(getX(), getY(), getX() + getBounds().width(), getY()
				+ getBounds().height());

		Rect r2 = new Rect((int) x - 5, (int) y - 5,
				(int) x + 5, (int) y + 5);

		if (r1.intersect(r2)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Places this meerkat on the gameboard at random
	 * Ensures the mover doesn't overlap with any other
     * movers.
	 * The overlap check ensures this mover doesn't have
	 * the same X co-ordinate as any other movers.
	 * @throws Exception If this meerkat is already visible
	 */
	public void show() throws Exception {
		if(visible) {
			throw new Exception("Can't show an already shown meerkat");
		}
		Point p = findEmptySpace();
		gameBoard.addMover(this);
		setLocation(p.x, p.y);
		visible = true;
		register(new PopUpAnimator(this, POPUP_SPEED));
	}
	
	/**
	 * Finds an empty space on the gameboard
	 * @return
	 * @throws Exception If the meerkat can't be placed
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
			if(count > 100) {
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
		setLocation(-1, -1);
	}

	public Bitmap getBitmap() {
		return bm;
	}

	@Override
	public void act() throws Exception {
		behavior.act();
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
}
