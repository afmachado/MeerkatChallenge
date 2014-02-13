package eu.johncasson.meerkatchallenge.game.actor;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import eu.johncasson.meerkatchallenge.game.GameBoard;
import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.visual.Drawable;

/**
 * Responsible for having and setting a location, being shown and hidden and
 * being hit.
 * 
 * @author John Casson
 * 
 */
public class Actor implements Hittable, Drawable, GameComponent {
	final int POPUP_SPEED = 150;
	private Point location;
	private Rect bounds;
	private boolean visible = false;
	private Sprite sprite;
	private GameBoard gameBoard;
	final private PopUpBehavior behavior;

	/**
	 * Creates a new actor that is placed with the injected placer and draws
	 * itself with the passed sprite.
	 * 
	 * @param placer
	 * @param sprite
	 */
	public Actor(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		this.sprite = new Sprite();
		this.behavior = new PopUpBehavior(this);
	}

	/**
	 * Sets the location of the actor
	 */
	protected void setLocation(int x, int y) {
		this.location = new Point(x, y);
	}

	/**
	 * Gets the bounds of this actor as a rectangle
	 */
	protected Rect getBounds() {
		return this.bounds;
	}

	/**
	 * Is the actor visible?
	 */
	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Shows this actor on the gameboard.
	 */
	protected void show() {
		this.place();
		visible = true;
		gameBoard.addActor(this);
		popUp();
	}

	/**
	 * Hides the actor
	 */
	protected void hide() {
		visible = false;
		setLocation(-1, -1);
		gameBoard.removeActor(this);
	}

	/**
	 * Detects whether the passed Rect overlaps with this actor
	 * 
	 * @param shot
	 *            the Rect that could overlap
	 * @return
	 */
	public boolean isOverlapping(Rect shot) {
		if (!visible) {
			return false;
		}
		int x = location.x;
		int y = location.y;
		Rect thisBounds = new Rect(x, y, x + getBounds().width(), y
				+ getBounds().height());

		if (thisBounds.intersect(shot)) {
			return true;
		}
		return false;
	}

	/**
	 * Sets this Actor's image
	 * 
	 * @param bitmap
	 *            The image to use
	 * @param size
	 *            The actor's size
	 */
	public void setBitmap(Bitmap bitmap, int size) {
		this.sprite.setBitmap(bitmap, size);
		this.bounds = new Rect(0, 0, size, size);
	}

	/**
	 * Draws the actor onto the passed canvas
	 */
	@Override
	public void draw(Canvas canvas) {
		// If we're visible, draw the actor
		if (visible) {
			sprite.draw(canvas, location.x, location.y);
		}
	}

	/**
	 * Returns the bitmap used for this actor
	 */
	protected Bitmap getBitmap() {
		return sprite.getBitmap();
	}

	/**
	 * Returns the matrix used to draw the actor
	 */
	protected Matrix getMatrix() {
		return sprite.getMatrix();
	}

	/**
	 * Tells the actor it has been hit
	 */
	public void hit() {
		behavior.hit();
	}

	@Override
	public void play(long runTime) {
		behavior.play(runTime);
	}

	private void popUp() {
		sprite.startAnimation(new PopUpper(sprite, POPUP_SPEED));
	}
	
	/**
	 * Places this on the Gameboard
	 */
	private void place() {
		Random r = new Random();
		int x = 0;
		int y = 0;

		int maxX = gameBoard.getWidth() - getBounds().width();
		int maxY = gameBoard.getHeight() - getBounds().height();
		
		int count = 0;
		do {
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
			count++;
			if (count > 100) {
				throw new RuntimeException("Can't place locatable");
			}
		} while (gameBoard.doesOverlap(new Rect(x, y, x + getBounds().width(), y + getBounds().height())));
		setLocation(x, y);
	}
}
