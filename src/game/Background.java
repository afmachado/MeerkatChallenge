package game;

import game.interfaces.visual.Drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background implements Drawable {
	Bitmap bm;

	public Background(int width, int height, Bitmap bm) {
		// Scale the background to the game board size
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inPurgeable = true;
		this.bm = Bitmap.createScaledBitmap(bm, width, height, false);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bm, 0, 0, null);
	}
}