package game.interfaces;

public interface Scorer {
	// Adds to the score
	public void add(int scoreToAdd);
	// Returns the current score
	public int get();
}
