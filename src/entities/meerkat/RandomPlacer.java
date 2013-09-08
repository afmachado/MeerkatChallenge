package entities.meerkat;

import interfaces.Locatable;
import interfaces.Placer;

import java.util.Random;

import main.GameBoard;
import android.graphics.Rect;

public class RandomPlacer implements Placer {
	private GameBoard gameBoard;

	public RandomPlacer(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public void place(Locatable locatable) {
		Random r = new Random();
		int minX = 0;
		int minY = 0;
		int x = 0;
		int y = 0;

		int width = gameBoard.getWidth();
		Rect rect1 = locatable.getBounds();
		int maxX = width - rect1.width();
		int maxY = gameBoard.getHeight() - locatable.getBounds().height();

		int count = 0;
		do {
			x = r.nextInt(maxX - minX + 1) + minX;
			y = r.nextInt(maxY - minY + 1) + minY;
			count++;
			if (count > 100) {
				throw new RuntimeException("Can't place locatable");
			}
		} while (gameBoard.doesOverlap(new Rect(x, y, x + gameBoard.getWidth(), y + gameBoard.getHeight())));
		locatable.setLocation(x, y);
	}
}
