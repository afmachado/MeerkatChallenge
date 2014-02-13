package eu.johncasson.meerkatchallenge.game;

import eu.johncasson.meerkatchallenge.game.interfaces.visual.Drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * A game's background
 * @author John Casson
 *
 */
class Background implements Drawable {
	private Bitmap bm;

	/**
	 * Creates a background
	 * @param width in pixels
	 * @param height in pixels
	 * @param bm the background image
	 */
	protected Background(int width, int height, Bitmap bm) {
		// Scale the background to the game board size
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inPurgeable = true;
		this.bm = Bitmap.createScaledBitmap(bm, width, height, false);
	}

	/**
	 * Draws this background on a canvas
	 * @param canvas from the Android framework
	 */
	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bm, 0, 0, null);
	}
}