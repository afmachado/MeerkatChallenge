package game.interfaces.status;

/**
 * Implemented by a component that keeps score
 * @author John Casson
 *
 */
public interface Scorer {
	/**
	 * Adds a value to the score
	 * @param toAdd
	 */
	public void add(int toAdd);
	/**
	 * Returns the score
	 * @return
	 */
	public int get();
}
