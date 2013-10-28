package game.interfaces.visual;

import game.actor.interfaces.Locatable;

/** 
 * Interface for objects that can place a locatable
 * @author John Casson
 */
public interface Placer {
	/**
	 * Place a locatable
	 * @param locatable
	 */
	public void place(Locatable locatable);
}
