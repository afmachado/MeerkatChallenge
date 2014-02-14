package eu.johncasson.meerkatchallenge.game.actor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import eu.johncasson.meerkatchallenge.game.interfaces.Drawable;
import eu.johncasson.meerkatchallenge.game.interfaces.GameComponent;

/**
 * Responsible for having and setting a location, being shown and hidden and
 * being hit.
 * 
 * @author John Casson
 * 
 */
public class Actor implements Drawable, GameComponent {
//	private final int POPUP_SPEED = 150;
	private final int POPUP_SPEED = 2000;
	private Point location;
	private Rect bounds;
	private boolean visible = false;
	private final Sprite sprite;
	private final GameBoard gameBoard;
	private final PopUpBehavior behavior;

	/**
	 * Creates a new actor that is placed with the injected placer and draws
	 * itself with the passed sprite.
	 * @param i 
	 * @param meerkatPic 
	 * 
	 * @param placer
	 * @param sprite
	 */
	public Actor(GameBoard gameBoard, Bitmap meerkatPic, int picSize) {
		this.gameBoard = gameBoard;
		this.sprite = new Sprite();
		this.sprite.setBitmap(meerkatPic, picSize);
		this.bounds = new Rect(0, 0, picSize, picSize);
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
		gameBoard.place(this);
		visible = true;
		sprite.startAnimation(new PopUpper(sprite, POPUP_SPEED));
	}

	/**
	 * Hides the actor
	 */
	protected void hide() {
		visible = false;
		setLocation(-1, -1);
		gameBoard.remove(this);
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
	 * Tells the actor it has been hit
	 */
	public void hit() {
		behavior.hit();
	}

	@Override
	public void play(long runTime) {
		behavior.play(runTime);
	}
}
