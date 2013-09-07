package main;


import java.util.ArrayList;

import entities.Mover;

public class GameBoard {
	private ArrayList<Mover> movers = new ArrayList<Mover>();

	int width;
	int height;

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void reset() {
		movers = new ArrayList<Mover>();
	}

	// To be called when a mover is added to the game board
	synchronized public void addMover(Mover m) throws Exception {
		if (hasMover(m)) {
			throw new Exception("Mover already registered with the game board");
		}
		movers.add(m);
	}

	public boolean hasMover(Mover m) {
		if (movers.contains(m)) {
			return true;
		} else {
			return false;
		}
	}

	synchronized public void removeMover(Mover m) {
		movers.remove(m);
	}

	/**
	 * Does the passed co-ordinate overlap with any registered movers?
	 * 
	 * @param Mover The mover to check for overlapping
	 * @return boolean
	 */
	synchronized public boolean doesOverlap(int x, int y) {
		for (Mover m : movers) {
			boolean xOverlap = false;
			if (Math.abs(m.getX() - x) < m.getBounds().width()) {
				xOverlap = true;
			}
			
			boolean yOverlap = false;
			if (Math.abs(m.getY() - y) < m.getBounds().height()) {
				yOverlap = true;
			}
			
			if(xOverlap && yOverlap) {
				return true;
			}
		}
		return false;

	}
}