package eu.johncasson.meerkatchallenge.game;


import eu.johncasson.meerkatchallenge.game.actor.Actor;

import java.util.ArrayList;

import android.graphics.Rect;

/**
 * A board to store the game's size and to manage the location
 * of actors interacting with the game.
 * @author John Casson
 *
 */
public class GameBoard {
	private ArrayList<Actor> actors = new ArrayList<Actor>();

	int width;
	int height;

	public GameBoard(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets the width
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the height
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	
	/**
	 * Registers an actor that wants to interact with
	 * the game. Each actor can only be added once.
	 * @param actor
	 */
	synchronized public void addActor(Actor actor) {
		if (hasActor(actor)) {
			throw new RuntimeException("Actor already registered with the game board");
		}
		actors.add(actor);
	}

	/**
	 * Is the passed actor already registered
	 * on the gameboard?
	 * @param actor
	 * @return
	 */
	public boolean hasActor(Actor actor) {
		if (actors.contains(actor)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove an actor from this gameboard
	 * @param actor
	 */
	synchronized public void removeActor(Actor actor) {
		actors.remove(actor);
	}
	
	/**
	 * Gets all actors registered with this gameboard.
	 * @return
	 */
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