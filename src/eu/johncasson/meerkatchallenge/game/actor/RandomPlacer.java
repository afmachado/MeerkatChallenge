package eu.johncasson.meerkatchallenge.game.actor;

import java.util.Random;

import android.graphics.Rect;
import eu.johncasson.meerkatchallenge.game.GameBoard;

/**
 * Places an actor on a gameboard
 * @author John Casson
 *
 */
public class RandomPlacer {
	private GameBoard gameBoard;

	public RandomPlacer(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	/**
	 * Places an animatable on the Gameboard
	 */
	public void place(Actor actor) {
		Random r = new Random();
		int x = 0;
		int y = 0;

		int maxX = gameBoard.getWidth() - actor.getBounds().width();
		int maxY = gameBoard.getHeight() - actor.getBounds().height();
		
		int count = 0;
		do {
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
			count++;
			if (count > 100) {
				throw new RuntimeException("Can't place locatable");
			}
		} while (gameBoard.doesOverlap(new Rect(x, y, x + actor.getBounds().width(), y + actor.getBounds().height())));
		actor.setLocation(x, y);
	}
}
