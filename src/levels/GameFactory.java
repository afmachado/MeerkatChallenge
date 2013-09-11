package levels;

import meerkatchallenge.main.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import game.entities.Background;
import game.entities.Game;
import game.entities.GameBoard;
import game.entities.Timer;
import game.entities.VisibleScore;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;

public class GameFactory {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private GameBoard gameBoard;

	public void createGame(Game gameActivity, Level level)
			throws Exception {
		// Set up the game engine
		this.gameLoop = new GameLoop();
		this.inputLoop = new InputLoop();
		graphicsLoop = ((GraphicsLoop) gameActivity.findViewById(R.id.canvas));
		gameLoop.addGameComponent(graphicsLoop);
		gameBoard = new GameBoard(graphicsLoop.getWidth(),
				graphicsLoop.getHeight());
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
		VisibleScore score = new VisibleScore(gameBoard, gameActivity, level);

		for (int i = 0; i < level.getPopUpMeerkats(); i++) {
			MeerkatFactory.addMeerkat(score, meerkatPic, gameLoop, gameBoard, graphicsLoop, inputLoop);
		}

		// Receive user input from the canvas
		canvasInput.setOnTouchListener(inputLoop);

		// Set a timer to stop the game after a specified time
		Timer t = new Timer(level.getTimeLimit() * 1000, gameBoard,
				gameActivity);
		gameLoop.addGameComponent(t);
		gameLoop.registerStop(t);
		graphicsLoop.register(t);

		// Register the score at the end so it's always drawn on top
		graphicsLoop.register(score);
		// Register the action to take when the game stops
		gameLoop.addStopAction(graphicsLoop);
		// On game end, the background has a stopaction to remove the bitmap
		// This is to try and avoid out of memory errors
		gameLoop.addStopAction(background);
		// The final stop action is to show the level end screen
		ShowLevelEnd sle = new ShowLevelEnd(gameActivity, score, level);
		gameLoop.addStopAction(sle);

		// Start the game
		gameLoop.start();
	}
}
