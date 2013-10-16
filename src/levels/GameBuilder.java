package levels;

import game.entities.Actor;
import game.entities.Background;
import game.entities.Game;
import game.entities.GameBoard;
import game.entities.PopUpBehavior;
import game.entities.PopUpper;
import game.entities.RandomPlacer;
import game.entities.Score;
import game.entities.Sprite;
import game.entities.Timer;
import game.entities.TouchHitDetector;
import game.entities.Updater;
import game.interfaces.EndLevelStarter;
import game.interfaces.OnHideListener;
import game.interfaces.OnHitDetected;
import game.interfaces.OnShowListener;
import game.interfaces.Placer;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;
import meerkatchallenge.activities.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.TextView;

public class GameBuilder {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private Level level;
	private Game game;
	private int width;
	private int height;
	private Score score;
	private int meerkatHitSoundId;
	private SoundPool soundPool;
	
	/**
	 * Sets the level around which this game is built
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Instantiates the loops needed for a game
	 * @param graphicsLoop A graphicsLoop to be added to the gameLoop
	 */
	public void makeLoops(GraphicsLoop graphicsLoop) {
		gameLoop = new GameLoop();
		inputLoop = new InputLoop();
		game = new Game();
		this.graphicsLoop = graphicsLoop;
		graphicsLoop.setOnTouchListener(inputLoop);
		gameLoop.addGameComponent(graphicsLoop);
	}
	
	/**
	 * Creates a score entity to keep score
	 */
	public void addScore(TextView scoreText) {
		score = new Score(level);
		Updater scoreUpdater = new Updater(score, scoreText);
		gameLoop.addGameComponent(scoreUpdater);
	}
	
	public void makeBackground(Bitmap backgroundPic, GraphicsLoop graphicsLoop) {
		Background background = new Background(width, height, backgroundPic);
		graphicsLoop.register(background);
	}
	
	public void makeTimer(TextView timerText) {
		// Set a timer to stop the game after a specified time
		Timer timer = new Timer(level.getTimeLimit() * 1000);
		gameLoop.addGameComponent(timer);
		gameLoop.registerStopCondition(timer);
		game.addPausable(timer);
		Updater timerUpdater = new Updater(timer, timerText);
		gameLoop.addGameComponent(timerUpdater);
	}
	
	public void addShowLevelEnd(EndLevelStarter endLevelStarter) {
		// Show the level end screen when the game stops
		// Contained
		ShowLevelEnd showLevelEnd = new ShowLevelEnd(endLevelStarter, score, level);
		gameLoop.addStopAction(showLevelEnd);
	}
	
	public void addSoundPool(Context context) {
		// Contained
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		meerkatHitSoundId = soundPool.load(context, R.raw.hit, 1);
	}

	public void addMeerkats(Bitmap meerkatPic) {
		// Draw Meerkats
		GameBoard gameBoard = new GameBoard(width, height);
		for (int i = 0; i < level.getPopUpMeerkats(); i++) {
			Actor meerkat = addMeerkat(meerkatPic, gameBoard);
			graphicsLoop.register(meerkat);
		}
	}
	
	private Actor addMeerkat(final Bitmap meerkatPic, final GameBoard gameBoard) {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		Placer placer = new RandomPlacer(gameBoard);
		final Actor meerkat = new Actor(placer, new Sprite());
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		final int size = (int) (gameBoard.getWidth() * 0.13);
		meerkat.setBitmap(meerkatPic, size);
		
		meerkat.setOnShowListener(new OnShowListener() {
			public void onShow() {
				gameBoard.addMover(meerkat);
				meerkat.startAnimation(new PopUpper(meerkat, POPUP_SPEED));
			}
		});
		
		meerkat.setOnHideListener(new OnHideListener() {
			public void onHide() {
				gameBoard.removeMover(meerkat);
			}
		});
		
		// Add a pop up behavior for this meerkat
		final PopUpBehavior behavior = new PopUpBehavior(meerkat);
		game.addPausable(behavior);
		
		// When we're hit, add one to the score and tell the behavior we've been hit
		OnHitDetected ohd = new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible and the game isn't paused
				if(meerkat.isVisible() && !game.isPaused()) {
					score.add(1);
					behavior.hit();
					meerkat.setBitmap(meerkatPic, size);
					soundPool.play(meerkatHitSoundId, 1, 1, 1, 0, 1f);
				}
			}
		};
		
		TouchHitDetector touchHitDetector = new TouchHitDetector(ohd, meerkat);
		
		gameLoop.addGameComponent(behavior);
		inputLoop.register(touchHitDetector);
		behavior.enable();
		return meerkat;
	}
	
	/**
	 * Completes the game building process
	 */
	public Game getGame() {
		/**
		 * The game loop should be the last thing to be unpaused so the other
		 * entities can prepare themselves for pausing first
		 */
		game.addPausable(gameLoop);
		return game;
	}
}
