package eu.johncasson.meerkatchallenge.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.TextView;
import eu.johncasson.meerkatchallenge.R;
import eu.johncasson.meerkatchallenge.activities.GameActivity;
import eu.johncasson.meerkatchallenge.game.actor.Actor;
import eu.johncasson.meerkatchallenge.game.actor.PopUpBehavior;
import eu.johncasson.meerkatchallenge.game.actor.PopUpper;
import eu.johncasson.meerkatchallenge.game.actor.RandomPlacer;
import eu.johncasson.meerkatchallenge.game.actor.TouchHitDetector;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.OnHideListener;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.OnHitDetected;
import eu.johncasson.meerkatchallenge.game.actor.interfaces.OnShowListener;
import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.status.OnStopListener;
import eu.johncasson.meerkatchallenge.game.interfaces.visual.Placer;
import eu.johncasson.meerkatchallenge.game.loops.GameLoop;
import eu.johncasson.meerkatchallenge.game.loops.GraphicsLoop;
import eu.johncasson.meerkatchallenge.game.loops.InputLoop;
import eu.johncasson.meerkatchallenge.levels.Level;

/**
 * Builds the game
 * @author John Casson
 *
 */
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
	 * 
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Sets the game board size.
	 * @param width
	 * @param height
	 */
	public void setGameBoardSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Instantiates the loops needed for a game
	 * 
	 * @param graphicsLoop
	 *            A graphicsLoop to be added to the gameLoop
	 */
	public void makeLoops(GraphicsLoop graphicsLoop) {
		gameLoop = new GameLoop();
		inputLoop = new InputLoop();
		this.graphicsLoop = graphicsLoop;
		graphicsLoop.setOnTouchListener(inputLoop);
		gameLoop.addGameComponent(graphicsLoop);
	}

	/**
	 * Creates a score entity to keep score
	 */
	public void addScore(final TextView scoreText) {
		score = new Score(level);
		gameLoop.addGameComponent(new GameComponent() {	
			@Override
			public void play() {
				scoreText.setText(score.toString());
			}
		});
	}

	/**
	 * Creates the game background
	 * @param backgroundPic
	 * @param graphicsLoop
	 */
	public void makeBackground(Bitmap backgroundPic, GraphicsLoop graphicsLoop) {
		Background background = new Background(width, height, backgroundPic);
		graphicsLoop.register(background);
	}
	
	/**
	 * Creates a count down timer
	 * @param timerText The textview to update with the time
	 */
	public void makeTimer(final TextView timerText) {
		// Set a timer to stop the game after a specified time
		final Timer timer = new Timer(level.getTimeLimit() * 1000);
		gameLoop.addGameComponent(timer);
		gameLoop.registerStoppable(timer);
		game.addPausable(timer);
		gameLoop.addGameComponent(new GameComponent() {	
			@Override
			public void play() {
				timerText.setText(timer.toString());
			}
		});
	}

	/**
	 * Shows the level end screen when the level finishes
	 * @param endLevelStarter
	 */
	public void addLevelEnd(final GameActivity gameActivity) {
		// Show the level end screen when the game stops
		gameLoop.addStopListener(new OnStopListener() {
			@Override
			public void onStop() {
				gameActivity.endLevel();
			}
		});
	}

	/**
	 * Adds a sound pool so the game can play sound
	 * @param context
	 */
	public void addSoundPool(Context context) {
		// Contained
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		meerkatHitSoundId = soundPool.load(context, R.raw.hit, 1);
	}

	/**
	 * Adds meerkats to the game
	 * @param meerkatPic
	 */
	public void addMeerkats(Bitmap meerkatPic) {
		// Draw Meerkats
		GameBoard gameBoard = new GameBoard(width, height);
		for (int i = 0; i < level.getMeerkats(); i++) {
			Actor meerkat = addMeerkat(meerkatPic, gameBoard);
			graphicsLoop.register(meerkat);
		}
	}

	/**
	 * Adds an individual meerkat to the game
	 * @param meerkatPic
	 * @param gameBoard
	 * @return
	 */
	private Actor addMeerkat(final Bitmap meerkatPic, final GameBoard gameBoard) {
		// The speed to pop up at
		final int POPUP_SPEED = 150;
		final int HIT_MARGIN = 5;
		Placer placer = new RandomPlacer(gameBoard);
		final Actor meerkat = new Actor(placer);
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		final int size = (int) (gameBoard.getWidth() * 0.13);
		meerkat.setBitmap(meerkatPic, size);

		meerkat.setOnShowListener(new OnShowListener() {
			public void onShow() {
				gameBoard.addActor(meerkat);
				meerkat.startAnimation(new PopUpper(meerkat, POPUP_SPEED));
			}
		});

		meerkat.setOnHideListener(new OnHideListener() {
			public void onHide() {
				gameBoard.removeActor(meerkat);
			}
		});

		// Add a pop up behavior for this meerkat
		final PopUpBehavior behavior = new PopUpBehavior(meerkat);
		game.addPausable(behavior);

		// When we're hit, add one to the score and tell the behavior we've been
		// hit
		OnHitDetected ohd = getMeerkatHitDetected(meerkat, behavior,
				meerkatPic, size);

		TouchHitDetector touchHitDetector = new TouchHitDetector(ohd, meerkat, HIT_MARGIN);

		gameLoop.addGameComponent(behavior);
		inputLoop.register(touchHitDetector);
		return meerkat;
	}

	/**
	 * Returns an OnHitDetected object that decides
	 * what action to take when a meerkat is hit.
	 * @param meerkat
	 * @param behavior
	 * @param meerkatPic
	 * @param size
	 * @return
	 */
	public OnHitDetected getMeerkatHitDetected(final Actor meerkat,
			final PopUpBehavior behavior, final Bitmap meerkatPic,
			final int size) {
		return new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible and the game isn't
				// paused
				if (meerkat.isVisible() && !game.isPaused()) {
					score.add(1);
					behavior.hit();
					meerkat.setBitmap(meerkatPic, size);
					soundPool.play(meerkatHitSoundId, 1, 1, 1, 0, 1f);
				}
			}
		};
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

	public void addGame() {
		this.game = new Game(score, level);
	}
}
