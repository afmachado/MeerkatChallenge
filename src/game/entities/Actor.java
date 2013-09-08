package game.entities;

import game.interfaces.Animatable;
import game.interfaces.Animator;
import game.interfaces.Drawable;
import game.interfaces.Hittable;
import game.interfaces.Locatable;
import game.interfaces.OnHideListener;
import game.interfaces.OnShowListener;
import game.interfaces.Placer;
import game.interfaces.Showable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Responsible for having and setting a location, showing and hiding and being
 * hit.
 * 
 * @author John Casson
 * 
 */
public class Actor implements Locatable, Showable, Hittable, Drawable,
		Animatable {
	private Point location;
	protected Rect bounds;
	protected boolean visible = false;
	private Placer placer;
	private OnShowListener onShowListener;
	private OnHideListener onHideListener;
	private Sprite sprite;

	public Actor(Placer placer, Sprite sprite) {
		this.placer = placer;
		this.sprite = sprite;
	}

	public void setLocation(int x, int y) {
		this.location = new Point(x, y);
	}

	public Rect getBounds() {
		return this.bounds;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Shows this actor on the gameboard.
	 * 
	 * @throws Exception
	 *             If this actor is already visible
	 */
	public void show() {
		if (visible) {
			throw new RuntimeException("Can't show a visible actor");
		}

		placer.place(this);
		visible = true;
		onShowListener.onShow();
	}

	public void hide() {
		visible = false;
		setLocation(-1, -1);
		onHideListener.onHide();
	}

	/**
	 * Detects a hit between a point and this object
	 * 
	 * @param Rect shot the shot to detect
	 * @return
	 */
	public boolean isHit(Rect shot) {
		if(!visible) {
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

	public void setOnShow(OnShowListener onShowListener) {
		this.onShowListener = onShowListener;
	}
	
	public void setOnHide(OnHideListener onHideListener) {
		this.onHideListener = onHideListener;
	}

	/**
	 * Sets this Actor's image
	 * 
	 * @param Bitmap
	 *            bm This Actor's image
	 */
	public void setBitmap(Bitmap bm, int size) {
		this.sprite.setBitmap(bm, size);
		this.bounds = new Rect(0, 0, size, size);
	}

	@Override
	public void draw(Canvas canvas) {
		// If we're visible, draw the actor
		if (visible) {
			sprite.draw(canvas, location.x, location.y);
		}
	}

	@Override
	public void registerAnimation(Animator a) {
		sprite.registerAnimation(a);
	}

	@Override
	public void unregisterAnimation(Animator a) {
		sprite.unregisterAnimation(a);
	}

	@Override
	public Bitmap getBitmap() {
		return sprite.getBitmap();
	}

	@Override
	public Matrix getMatrix() {
		return sprite.getMatrix();
	}
}
