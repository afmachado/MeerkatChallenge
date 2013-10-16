package levels;

import game.interfaces.EndLevelStarter;
import game.loops.GraphicsLoop;
import meerkatchallenge.activities.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;

public class GameBuilderDirector {
	GameBuilder gameBuilder;

	public GameBuilderDirector(GameBuilder gameBuilder) {
		this.gameBuilder = gameBuilder;
	}

	public void construct(ViewSource viewSource,
			EndLevelStarter endLevelStarter, Context context,
			Resources resources, int width, int height,
			Level level) {
		
		gameBuilder.setLevel(level);
		gameBuilder.setSize(width, height);
		GraphicsLoop graphicsLoop = (GraphicsLoop) viewSource
				.findViewById(R.id.canvas);
		gameBuilder.makeLoops(graphicsLoop);

		TextView scoreText = (TextView) viewSource
				.findViewById(R.id.game_score);
		gameBuilder.addScore(scoreText);

		Bitmap backgroundPic = BitmapFactory.decodeResource(resources,
				R.drawable.background);
		gameBuilder.makeBackground(backgroundPic, graphicsLoop);

		gameBuilder.addShowLevelEnd(endLevelStarter);

		gameBuilder.addSoundPool(context);

		// Set up the game's timer
		TextView timerText = (TextView) viewSource.findViewById(R.id.game_time);
		gameBuilder.makeTimer(timerText);

		Bitmap meerkatPic = (BitmapFactory.decodeResource(resources,
				R.drawable.meerkat_hole));
		gameBuilder.addMeerkats(meerkatPic);
		
	}
}
