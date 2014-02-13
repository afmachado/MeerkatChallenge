package eu.johncasson.meerkatchallenge.game.actor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.Hittable;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.OnHideListener;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.OnShowListener;
import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.status.Pausable;
import eu.johncasson.meerkatchallenge.game.interfaces.visual.Drawable;

/**
 * Responsible for having and setting a location, being shown and hidden and
 * being hit.
 * 
 * @author John Casson
 * 
 */
public class Actor implements Hittable, Drawable,
		Pausable, GameComponent {
	final int POPUP_SPEED = 150;
	private Point location;
	private Rect bounds;
	private boolean visible = false;
	private RandomPlacer placer;
	private OnShowListener onShowListener;
	private OnHideListener onHideListener;
	private Sprite sprite;
	final private PopUpBehavior behavior;

	/**
	 * Creates a new actor that is placed with the injected placer and draws
	 * itself with the passed sprite.
	 * 
	 * @param placer
	 * @param sprite
	 */
	public Actor(RandomPlacer placer) {
		this.placer = placer;
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
		placer.place(this);
		visible = true;
		onShowListener.onShow();
	}

	/**
	 * Hides the actor
	 */
	protected void hide() {
		visible = false;
		setLocation(-1, -1);
		onHideListener.onHide();
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
	 * Sets a listener to be called when the actor is shown
	 * 
	 * @param onShowListener
	 */
	public void setOnShowListener(OnShowListener onShowListener) {
		this.onShowListener = onShowListener;
	}

	/**
	 * Sets a listener to be called when the actor is hidden
	 * 
	 * @param onShowListener
	 */
	public void setOnHideListener(OnHideListener onHideListener) {
		this.onHideListener = onHideListener;
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
	public void onPause() {
		behavior.onPause();
	}

	@Override
	public void onUnPause() {
		behavior.onUnPause();
	}

	@Override
	public void play() {
		behavior.play();
	}

	public void popUp() {
		sprite.startAnimation(new PopUpper(sprite, POPUP_SPEED));
	}
}
