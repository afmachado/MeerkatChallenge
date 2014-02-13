package eu.johncasson.meerkatchallenge.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import eu.johncasson.meerkatchallenge.R;
import eu.johncasson.meerkatchallenge.game.loops.GraphicsLoop;
import eu.johncasson.meerkatchallenge.levels.Level;

/**
 * Directs the game builder process
 * @author John Casson
 *
 */
public class GameBuilderDirector {
	GameBuilder gameBuilder;

	public GameBuilderDirector(GameBuilder gameBuilder) {
		this.gameBuilder = gameBuilder;
	}

	/**
	 * Creates the game.
	 * @param viewSource
	 * @param endLevelStarter
	 * @param context
	 * @param resources
	 * @param width
	 * @param height
	 * @param level
	 */
	public void construct(GameActivity gameActivity, Context context,
			Resources resources, int width, int height,
			Level level) {
		
		gameBuilder.setLevel(level);
		gameBuilder.setGameBoardSize(width, height);
		GraphicsLoop graphicsLoop = (GraphicsLoop) gameActivity
				.findViewById(R.id.canvas);
		gameBuilder.makeLoops(graphicsLoop);

		TextView scoreText = (TextView) gameActivity
				.findViewById(R.id.game_score);
		gameBuilder.addScore(scoreText);
		gameBuilder.addGame();

		Bitmap backgroundPic = BitmapFactory.decodeResource(resources,
				R.drawable.background);
		gameBuilder.makeBackground(backgroundPic, graphicsLoop);

		gameBuilder.addLevelEnd(gameActivity);

		gameBuilder.addSoundPool(context);

		// Set up the game's timer
		TextView timerText = (TextView) gameActivity.findViewById(R.id.game_time);
		gameBuilder.makeTimer(timerText);

		Bitmap meerkatPic = (BitmapFactory.decodeResource(resources,
				R.drawable.meerkat_hole));
		gameBuilder.addMeerkats(meerkatPic);
	}
}
