package entities;

import main.GameBoard;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Mover {
	private Point location;
	protected Rect bounds;
	protected GameBoard gameBoard;
	
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
	
	public Mover(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
}
