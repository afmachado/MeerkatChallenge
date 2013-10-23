package game.interfaces.visual;

import game.actor.interfaces.Locatable;

/** 
 * Interface for objects that can place a locatable
 * @author John Casson
 *
 */
public interface Placer {
	public void place(Locatable locatable);
}
