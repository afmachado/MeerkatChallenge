package main;


import java.util.ArrayList;

import android.graphics.Rect;
import entities.Actor;

public class GameBoard {
	private ArrayList<Actor> actors = new ArrayList<Actor>();

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
		actors = new ArrayList<Actor>();
	}

	// To be called when a mover is added to the game board
	synchronized public void addMover(Actor m) {
		if (hasMover(m)) {
			throw new RuntimeException("Mover already registered with the game board");
		}
		actors.add(m);
	}

	public boolean hasMover(Actor m) {
		if (actors.contains(m)) {
			return true;
		} else {
			return false;
		}
	}

	synchronized public void removeMover(Actor m) {
		actors.remove(m);
	}

	/**
	 * Does the passed rect overlap with any registered actors?
	 * 
	 * @param Sprite The mover to check for overlapping
	 * @return boolean
	 */
	synchronized public boolean doesOverlap(Rect rectangle) {
		for (Actor a : actors) {
			if(a.isHit(rectangle)) {
				return true;
			}
		}
		return false;

	}
}