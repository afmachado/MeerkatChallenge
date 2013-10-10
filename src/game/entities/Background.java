package game.entities;

import game.interfaces.Drawable;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background implements Drawable {
	Bitmap bm;
	
	GameBoard gameboard;
	
	public Background(GameBoard gameBoard, Bitmap bm) {
		this.gameboard = gameBoard;
		// Scale the background to the game board size
		this.bm = Bitmap.createScaledBitmap(bm, gameBoard.getWidth()
				, gameBoard.getHeight(), false);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bm, 0, 0, null);
	}
}