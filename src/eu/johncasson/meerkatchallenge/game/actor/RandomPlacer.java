package eu.johncasson.meerkatchallenge.game.actor;

import eu.johncasson.meerkatchallenge.game.GameBoard;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.Locatable;
import eu.johncasson.meerkatchallenge.game.interfaces.visual.Placer;

import java.util.Random;

import android.graphics.Rect;

/**
 * Places a locatable on a gameboard
 * @author John Casson
 *
 */
public class RandomPlacer implements Placer {
	private GameBoard gameBoard;

	public RandomPlacer(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	/**
	 * Places an animatable on the Gameboard
	 */
	@Override
	public void place(Locatable locatable) {
		Random r = new Random();
		int x = 0;
		int y = 0;

		int maxX = gameBoard.getWidth() - locatable.getBounds().width();
		int maxY = gameBoard.getHeight() - locatable.getBounds().height();
		
		int count = 0;
		do {
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
			count++;
			if (count > 100) {
				throw new RuntimeException("Can't place locatable");
			}
		} while (gameBoard.doesOverlap(new Rect(x, y, x + locatable.getBounds().width(), y + locatable.getBounds().height())));
		locatable.setLocation(x, y);
	}
}
