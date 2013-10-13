package game.entities;

import game.interfaces.Drawable;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background implements Drawable {
	Bitmap bm;

	public Background(int width, int height, Bitmap bm) {
		// Scale the background to the game board size
		this.bm = Bitmap.createScaledBitmap(bm, width, height, false);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bm, 0, 0, null);
	}
}