package levels;

import game.entities.Actor;
import game.entities.Game;
import game.entities.PopUpBehavior;
import game.entities.PopUpper;
import game.entities.RandomPlacer;
import game.entities.Sprite;
import game.entities.TouchHitDetector;
import game.interfaces.OnHideListener;
import game.interfaces.OnHitDetected;
import game.interfaces.OnShowListener;
import game.interfaces.Placer;
import game.interfaces.Scorer;
import android.graphics.Bitmap;

/**
 * Creates a meerkat
 * @author John Casson
 *
 */
public class MeerkatFactory {
	public static void addMeerkat(final Scorer s, final Bitmap meerkatPic, final Game game) {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		// Set up a meerkat
		Placer placer = new RandomPlacer(game.getGameBoard());
		final Actor meerkat = new Actor(placer, new Sprite());
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		final int size = (int) (game.getGameBoard().getHeight() * 0.08);
		meerkat.setBitmap(meerkatPic, size);
		
		meerkat.setOnShow(new OnShowListener() {
			public void onShow() {
				game.getGameBoard().addMover(meerkat);
				meerkat.registerAnimation(new PopUpper(meerkat, POPUP_SPEED));
			}
		});
		
		meerkat.setOnHide(new OnHideListener() {
			public void onHide() {
				game.getGameBoard().removeMover(meerkat);
			}
		});
		
		// Add a pop up behavior for this meerkat
		final PopUpBehavior behavior = new PopUpBehavior(meerkat);
		game.addPausable(behavior);
		
		// When we're hit, add one to the score and tell the behavior we've been hit
		OnHitDetected ohd = new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible
				if(meerkat.isVisible()) {
					s.add(1);
					behavior.hit();
					meerkat.setBitmap(meerkatPic, size);
				}
			}
		};
		
		TouchHitDetector thd = new TouchHitDetector(ohd, meerkat);
		
		game.getGameLoop().addGameComponent(behavior);
		game.getInputLoop().register(thd);
		game.getGraphicsLoop().register(meerkat);
		behavior.enable();
	}
}
