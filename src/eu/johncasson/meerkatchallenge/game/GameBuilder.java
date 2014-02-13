package eu.johncasson.meerkatchallenge.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.TextView;
import eu.johncasson.meerkatchallenge.R;
import eu.johncasson.meerkatchallenge.game.actor.Actor;
import eu.johncasson.meerkatchallenge.game.actor.OnHitDetected;
import eu.johncasson.meerkatchallenge.game.actor.TouchHitDetector;
import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.status.OnStopListener;
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
			public void play(long runTime) {
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
		gameLoop.addGameComponent(new GameComponent() {	
			@Override
			public void play(long runTime) {
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
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		meerkatHitSoundId = soundPool.load(context, R.raw.hit, 1);
	}

	/**
	 * Adds meerkats to the game
	 * @param meerkatPic
	 */
	public void addMeerkats(Bitmap meerkatPic) {
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
		final int HIT_MARGIN = 5;
		final Actor meerkat = new Actor(gameBoard);
		// Set the size of the meerkat to be a fixed % of the gameboard's height
		final int size = (int) (gameBoard.getWidth() * 0.13);
		meerkat.setBitmap(meerkatPic, size);

		gameLoop.addGameComponent(meerkat);
		
		// When we're hit, add one to the score and tell the behavior we've been
		// hit
		OnHitDetected ohd = getMeerkatHitDetected(meerkat, meerkatPic, size);
		TouchHitDetector touchHitDetector = new TouchHitDetector(ohd, meerkat, HIT_MARGIN);

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
	private OnHitDetected getMeerkatHitDetected(final Actor meerkat,
			final Bitmap meerkatPic,
			final int size) {
		return new OnHitDetected() {
			public void onHit() {
				// Only react if the meerkat is visible and the game isn't
				// paused
				if (meerkat.isVisible() && !game.isPaused()) {
					score.add(1);
					meerkat.hit();
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
		return game;
	}

	public void addGame() {
		this.game = new Game(score, level, gameLoop);
	}
}
