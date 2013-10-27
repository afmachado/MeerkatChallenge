package game;


import game.actor.Actor;

import java.util.ArrayList;

import android.graphics.Rect;

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

	// To be called when a mover is added to the game board
	synchronized public void addActor(Actor actor) {
		if (hasActor(actor)) {
			throw new RuntimeException("Actor already registered with the game board");
		}
		actors.add(actor);
	}

	public boolean hasActor(Actor actor) {
		if (actors.contains(actor)) {
			return true;
		} else {
			return false;
		}
	}

	synchronized public void removeActor(Actor actor) {
		actors.remove(actor);
	}
	
	synchronized public ArrayList<Actor> getActors() {
		return actors;
	}

	/**
	 * Does the passed rect overlap with any registered actors?
	 * 
	 * @param Sprite The mover to check for overlapping
	 * @return boolean
	 */
	synchronized public boolean doesOverlap(Rect rectangle) {
		for (Actor a : actors) {
			if(a.isOverlapping(rectangle)) {
				return true;
			}
		}
		return false;

	}
}