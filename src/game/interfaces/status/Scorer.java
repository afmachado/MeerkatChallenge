package game.interfaces.status;

/**
 * A component that can keep score
 * @author John Casson
 *
 */
public interface Scorer {
	// Adds to the score
	public void add(int scoreToAdd);
	// Returns the current score
	public int get();
}
