package entities;

import interfaces.Showable;
import main.GameBoard;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Actor implements Showable {
	private Point location;
	protected Rect bounds;
	protected GameBoard gameBoard;
	protected boolean visible = false;
	
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
	
	public Actor(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}
	
	/**
	 * Shows this actor on a gameboard. 
	 * 
	 * @throws Exception
	 *             If this actor is already visible
	 */
	public void show() throws Exception {
		if (visible) {
			throw new Exception("Can't show a visible actor");
		}

		gameBoard.addMover(this);
		visible = true;
	}
	
	public void hide() {
		visible = false;
		gameBoard.removeMover(this);
	}
}
