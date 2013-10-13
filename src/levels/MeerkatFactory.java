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
import meerkatchallenge.activities.GameActivity;
import meerkatchallenge.activities.R;
import android.graphics.Bitmap;
import android.media.SoundPool;

/**
 * Creates a meerkat
 * @author John Casson
 *
 */
public class MeerkatFactory {
	public static void addMeerkat(final Scorer s, final Bitmap meerkatPic, 
			final Game game, GameActivity gameActivity) {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		Placer placer = new RandomPlacer(game.getGameBoard());
		final Actor meerkat = new Actor(placer, new Sprite());
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		final int size = (int) (game.getGameBoard().getWidth() * 0.13);
		meerkat.setBitmap(meerkatPic, size);
		
		meerkat.setOnShowListener(new OnShowListener() {
			public void onShow() {
				game.getGameBoard().addMover(meerkat);
				meerkat.registerAnimation(new PopUpper(meerkat, POPUP_SPEED));
			}
		});
		
		meerkat.setOnHideListener(new OnHideListener() {
			public void onHide() {
				game.getGameBoard().removeMover(meerkat);
			}
		});
		
		// Add a pop up behavior for this meerkat
		final PopUpBehavior behavior = new PopUpBehavior(meerkat);
		game.addPausable(behavior);
		
		SoundPool soundPool = game.getSoundPool();
		final int hitId = soundPool.load(gameActivity, R.raw.hit, 1);
		
		// When we're hit, add one to the score and tell the behavior we've been hit
		OnHitDetected ohd = new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible and the game isn't paused
				if(meerkat.isVisible() && !game.isPaused()) {
					s.add(1);
					behavior.hit();
					meerkat.setBitmap(meerkatPic, size);
					game.getSoundPool().play(hitId, 1, 1, 1, 0, 1f);
				}
			}
		};
		
		TouchHitDetector touchHitDetector = new TouchHitDetector(ohd, meerkat);
		
		game.getGameLoop().addGameComponent(behavior);
		game.getInputLoop().register(touchHitDetector);
		game.getGraphicsLoop().register(meerkat);
		behavior.enable();
	}
}
