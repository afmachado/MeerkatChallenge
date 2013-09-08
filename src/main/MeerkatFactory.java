package main;

import interfaces.OnHitDetected;
import interfaces.OnShowListener;
import interfaces.Scorer;
import loops.GameLoop;
import loops.GraphicsLoop;
import loops.InputLoop;
import android.graphics.Bitmap;
import entities.meerkat.PopUpper;
import entities.meerkat.RandomPlacer;
import entities.meerkat.Sprite;
import entities.meerkat.TouchHitDetector;

/**
 * Creates a meerkat
 * @author John Casson
 *
 */
public class MeerkatFactory {
	public static void addMeerkat(final Scorer s, Bitmap meerkatPic, GameLoop gameLoop,
			GameBoard gameBoard, GraphicsLoop graphicsLoop, InputLoop inputLoop) throws Exception {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		
		// Set up the first meerkat
		final Sprite m = new Sprite(gameBoard, new RandomPlacer());
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		int size = (int) (gameBoard.getHeight() * 0.08);
		m.setBitmap(meerkatPic, size);
		graphicsLoop.register(m);
		
		m.setOnShow(new OnShowListener() {
			public void onShow() {
				m.registerAnimation(new PopUpper(m, POPUP_SPEED));
			}
		});
		
		// When we're hit, add one to the score and tell the behavior we've been hit
		OnHitDetected ohd = new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible
				if(m.isVisible()) {
					s.add(1);
					m.getPopUpBehavior().hit();
				}
			}
		};
		
		TouchHitDetector thd = new TouchHitDetector(ohd, m);
		inputLoop.register(thd);
		gameLoop.addGameComponent(m);
	}
}
