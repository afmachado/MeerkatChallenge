package entities;

import interfaces.Hittable;
import interfaces.Locatable;
import interfaces.Placer;
import interfaces.Showable;
import main.GameBoard;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Actor implements Locatable, Showable, Hittable {
	private Point location;
	protected Rect bounds;
	protected GameBoard gameBoard;
	protected boolean visible = false;
	private Placer placer;

	public Actor(GameBoard gameBoard, Placer placer) {
		this.gameBoard = gameBoard;
		this.placer = placer;
	}

	public void setLocation(int x, int y) {
		this.location = new Point(x, y);
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}

	public int getX() {
		return location.x;
	}

	public int getY() {
		return location.y;
	}

	public int getMaxX() {
		return gameBoard.getWidth() - bounds.width();
	}

	public int getMaxY() {
		return gameBoard.getHeight() - bounds.height();
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
	public void show() throws Exception {
		if (visible) {
			throw new Exception("Can't show a visible actor");
		}
		
		placer.place(this);

		gameBoard.addMover(this);
		visible = true;
	}

	public void hide() {
		visible = false;
		gameBoard.removeMover(this);
	}

	/**
	 * Detects a hit between a point and this object
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
	
	public boolean doesOverlap(int x, int y) {
		return gameBoard.doesOverlap(x, y);
	}
	
	public Rect getContainerSize() {
		return new Rect(0, 0, gameBoard.getWidth(), gameBoard.getHeight());
	}
}
