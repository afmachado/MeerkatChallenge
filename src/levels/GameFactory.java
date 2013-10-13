package levels;

import game.entities.Background;
import game.entities.Game;
import game.entities.GameBoard;
import game.entities.Timer;
import game.entities.Updater;
import game.entities.Score;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;
import meerkatchallenge.activities.GameActivity;
import meerkatchallenge.activities.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.widget.TextView;

public class GameFactory {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private GameBoard gameBoard;

	public Game createGame(GameActivity gameActivity, Level level) {
		// Set up the game engine
		this.gameLoop = new GameLoop();
		this.inputLoop = new InputLoop();
		graphicsLoop = ((GraphicsLoop) gameActivity.findViewById(R.id.canvas));
		
		gameLoop.addGameComponent(graphicsLoop);
		
		gameBoard = new GameBoard(graphicsLoop.getWidth(),
				graphicsLoop.getHeight());
		
		SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		
		Game game = new Game(gameLoop, inputLoop, graphicsLoop, gameBoard, soundPool);
		
		gameBoard.reset();
		View canvasInput = (View) gameActivity.findViewById(R.id.canvas);

		// Load images
		Bitmap meerkatPic = (BitmapFactory.decodeResource(
				gameActivity.getResources(), R.drawable.meerkat));
		Bitmap backgroundPic = BitmapFactory.decodeResource(
				gameActivity.getResources(), R.drawable.background);

		// Set up background
		Background background = new Background(gameBoard, backgroundPic);
		graphicsLoop.register(background);

		// Create a score entity to keep score
		Score score = new Score(level);
		TextView scoreText = (TextView) gameActivity.findViewById(R.id.game_score);
		Updater scoreUpdater = new Updater(score,scoreText);
		gameLoop.addGameComponent(scoreUpdater);

		for (int i = 0; i < level.getPopUpMeerkats(); i++) {
			MeerkatFactory.addMeerkat(score, meerkatPic, game, gameActivity);
		}

		// Receive user input from the canvas
		canvasInput.setOnTouchListener(inputLoop);

		// Set a timer to stop the game after a specified time
		Timer timer = new Timer(level.getTimeLimit() * 1000, gameBoard);
		gameLoop.addGameComponent(timer);
		gameLoop.registerStopCondition(timer);
		
		TextView timerText = (TextView) gameActivity.findViewById(R.id.game_time);
		Updater timerUpdater = new Updater(timer, timerText);
		gameLoop.addGameComponent(timerUpdater);

		// Show the level end screen when the game stops
		ShowLevelEnd showLevelEnd = new ShowLevelEnd(gameActivity, score, level);
		gameLoop.addStopAction(showLevelEnd);
		game.addPausable(timer);
		/** The game loop should be the last thing to be unpaused
		 * so the other entities can prepare themselves
		 */
		game.addPausable(gameLoop);
		game.start();
		return game;
	}
}
