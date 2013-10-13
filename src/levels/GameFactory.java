package levels;

import game.entities.Background;
import game.entities.Game;
import game.entities.GameBoard;
import game.entities.Score;
import game.entities.Timer;
import game.entities.Updater;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;
import meerkatchallenge.activities.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFactory {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private GameBoard gameBoard;

	public Game createGame(Activity activity, Level level) {
		// Set up the game engine
		this.gameLoop = new GameLoop();
		this.inputLoop = new InputLoop();
		graphicsLoop = ((GraphicsLoop) activity.findViewById(R.id.canvas));
		
		gameLoop.addGameComponent(graphicsLoop);
		
		// Intentionally duplicated further down
		// To be removed after SPRing this class
		ImageView placeholderBackground = (ImageView) activity.findViewById(R.id.game_background_placeholder);
		int width = placeholderBackground.getWidth();
		int height = placeholderBackground.getHeight();
		
		gameBoard = new GameBoard(width, height);
		
		SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		
		Game game = new Game(gameLoop, inputLoop, graphicsLoop, gameBoard, soundPool);
		
		gameBoard.reset();
		View canvasInput = (View) activity.findViewById(R.id.canvas);

		// Load images
		Bitmap meerkatPic = (BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.meerkat_hole));
		Bitmap backgroundPic = BitmapFactory.decodeResource(
				activity.getResources(), R.drawable.background);

		// Set up background
		Background background = new Background(width, height, backgroundPic);
		graphicsLoop.register(background);

		// Create a score entity to keep score
		Score score = new Score(level);
		TextView scoreText = (TextView) activity.findViewById(R.id.game_score);
		Updater scoreUpdater = new Updater(score,scoreText);
		gameLoop.addGameComponent(scoreUpdater);

		for (int i = 0; i < level.getPopUpMeerkats(); i++) {
			MeerkatFactory.addMeerkat(score, meerkatPic, game, activity);
		}

		// Receive user input from the canvas
		canvasInput.setOnTouchListener(inputLoop);

		// Set a timer to stop the game after a specified time
		Timer timer = new Timer(level.getTimeLimit() * 1000, gameBoard);
		gameLoop.addGameComponent(timer);
		gameLoop.registerStopCondition(timer);
		
		TextView timerText = (TextView) activity.findViewById(R.id.game_time);
		Updater timerUpdater = new Updater(timer, timerText);
		gameLoop.addGameComponent(timerUpdater);

		// Show the level end screen when the game stops
		ShowLevelEnd showLevelEnd = new ShowLevelEnd(activity, score, level);
		gameLoop.addStopAction(showLevelEnd);
		game.addPausable(timer);
		/** The game loop should be the last thing to be unpaused
		 * so the other entities can prepare themselves
		 */
		game.addPausable(gameLoop);
		game.start();
		
		// Hide the placeholder gameboard and show the proper gameboard
		// TOOD: commented out because we use this above
//		ImageView placeholderBackground = (ImageView) activity.findViewById(R.id.game_background_placeholder);
		placeholderBackground.setVisibility(View.GONE);
		graphicsLoop.setVisibility(View.VISIBLE);
		return game;
	}
}
