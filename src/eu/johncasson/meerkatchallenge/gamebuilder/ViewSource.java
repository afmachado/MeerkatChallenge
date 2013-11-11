package eu.johncasson.meerkatchallenge.gamebuilder;

import android.view.View;

/**
 * Implemented by classes that can return Views from their ID
 * @author John Casson
 *
 */
public interface ViewSource {
	/**
	 * Return a view given by the passed ID
	 * @param id
	 * @return
	 */
	public View findViewById(int id);
}