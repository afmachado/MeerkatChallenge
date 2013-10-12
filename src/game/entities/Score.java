package game.entities;

import game.interfaces.Scorer;
import levels.Level;

public class Score implements GivesUpdates, Scorer {
	private int score = 0;
	private Level level;
	
	public Score(Level level) {
		this.level = level;
	}

	synchronized public void add(int toAdd) {
		score = score + toAdd;
	}
	
	public int get() {
		return this.score;
	}

	@Override
	public String getStatus() {
		int meerkatsLeft = level.getTargetScore() - score;
		String scoreText;
		// If the target hasn't been met show the number of meerkats left
		if(meerkatsLeft >= 0) {
			scoreText = Integer.toString(meerkatsLeft);
		} else {
			scoreText = "+" + (meerkatsLeft * -1);
		}
		
		return scoreText;
	}
}
