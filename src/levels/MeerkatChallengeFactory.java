package levels;

import game.entities.Actor;
import game.entities.Background;
import game.entities.Game;
import game.entities.GameBoard;
import game.entities.Score;
import game.entities.Timer;
import game.entities.Updater;
import game.loops.GraphicsLoop;
import levels.gamebuilder.ConcreteGameBuilder;
import levels.gamebuilder.GameBuilder;
import levels.gamebuilder.MeerkatBuilderDirector;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.widget.TextView;

public class MeerkatChallengeFactory {

	public Game createMeerkatChallenge(GraphicsLoop graphicsLoop, int width, int height,
			Bitmap meerkatPic, Bitmap backgroundPic, TextView scoreText,
			TextView timerText, Level level, Score score, int meerkatHitSoundId, SoundPool soundPool) {
		// Set up the game engine

		GameBoard gameBoard = new GameBoard(width, height);		
		
		Game game = createGame(graphicsLoop, gameBoard, soundPool);

		game.getGameLoop().addGameComponent(graphicsLoop);

		gameBoard.reset();


		// Set up background
		Background background = new Background(width, height, backgroundPic);
		graphicsLoop.register(background);
		
		Updater scoreUpdater = new Updater(score, scoreText);
		game.getGameLoop().addGameComponent(scoreUpdater);

		for (int i = 0; i < level.getPopUpMeerkats(); i++) {
			Actor meerkat = MeerkatBuilder.addMeerkat(score, meerkatPic, game, meerkatHitSoundId);
			graphicsLoop.register(meerkat);
		}

		// Set a timer to stop the game after a specified time
		Timer timer = new Timer(level.getTimeLimit() * 1000);
		game.getGameLoop().addGameComponent(timer);
		game.getGameLoop().registerStopCondition(timer);

		
		Updater timerUpdater = new Updater(timer, timerText);
		game.getGameLoop().addGameComponent(timerUpdater);

		// Show the level end screen when the game stops
		
		
		game.addPausable(timer);
		/**
		 * The game loop should be the last thing to be unpaused so the other
		 * entities can prepare themselves
		 */
		game.addPausable(game.getGameLoop());
		game.start();

		return game;
	}

	/**
	 * Generic method to create a Game
	 * @param gameBoard 
	 * @param soundPool 
	 * 
	 * @return
	 */
	private Game createGame(GraphicsLoop graphicsLoop, GameBoard gameBoard, SoundPool soundPool) {
		GameBuilder gameBuilder = new ConcreteGameBuilder(); 
		new MeerkatBuilderDirector(gameBuilder, gameBoard, soundPool, graphicsLoop);
		return gameBuilder.getGame();	
	}
}
