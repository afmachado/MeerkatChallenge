package main;

import interfaces.OnHideListener;
import interfaces.OnHitDetected;
import interfaces.OnShowListener;
import interfaces.Placer;
import interfaces.Scorer;
import loops.GameLoop;
import loops.GraphicsLoop;
import loops.InputLoop;
import android.graphics.Bitmap;
import entities.Actor;
import entities.meerkat.PopUpBehavior;
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
			final GameBoard gameBoard, GraphicsLoop graphicsLoop, InputLoop inputLoop) throws Exception {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		// Set up a meerkat
		Placer placer = new RandomPlacer(gameBoard);
		final Actor meerkat = new Actor(placer, new Sprite());
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		int size = (int) (gameBoard.getHeight() * 0.08);
		meerkat.setBitmap(meerkatPic, size);
		
		meerkat.setOnShow(new OnShowListener() {
			public void onShow() {
				gameBoard.addMover(meerkat);
				meerkat.registerAnimation(new PopUpper(meerkat, POPUP_SPEED));
			}
		});
		
		meerkat.setOnHide(new OnHideListener() {
			public void onHide() {
				gameBoard.removeMover(meerkat);
			}
		});
		
		// Add a pop up behavior for this meerkat
		final PopUpBehavior behavior = new PopUpBehavior(meerkat);
		
		// When we're hit, add one to the score and tell the behavior we've been hit
		OnHitDetected ohd = new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible
				if(meerkat.isVisible()) {
					s.add(1);
					behavior.hit();
				}
			}
		};
		
		TouchHitDetector thd = new TouchHitDetector(ohd, meerkat);
		
		gameLoop.addGameComponent(behavior);
		inputLoop.register(thd);
		graphicsLoop.register(meerkat);
	}
}
