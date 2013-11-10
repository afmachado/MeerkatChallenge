package activities;

import game.Game;
import game.Score;
import game.interfaces.EndLevelStarter;
import game.loops.GraphicsLoop;
import gamebuilder.GameBuilder;
import gamebuilder.GameBuilderDirector;
import gamebuilder.ViewSource;
import levels.Level;
import meerkatchallenge.activities.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * The game itself
 * 
 * @author John Casson
 * 
 */
public class GameActivity extends VolumeControlActivity implements
		EndLevelStarter, ViewSource {
	private Game game;
	private Level level;
	private boolean firstRun = true;

	/**
	 * Calls the StartLevel activity to show the level start screen
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		level = (Level) getIntent().getExtras().getSerializable("level");
		GameActivity gameActivity = this;

		Intent intent = new Intent(gameActivity, StartLevel.class);
		intent.putExtra("level", level);
		startActivity(intent);

	}

	/**
	 * Unpause the game when the activity is resumed
	 */
	@Override
	protected void onResume() {
		// Draw the gameboard the second time onResume is called
		// (After the user presses "Go" in StartLevel
		if (game == null && !firstRun) {
			createGame();
		}
		// game won't be initialized for the first onResume call
		// as onResume is called when an activity first starts
		if (game != null && game.isStarted()) {
			game.unPause();
		}
		super.onResume();
	}

	/**
	 * Creates the game
	 */
	private void createGame() {
		// Set the width and height
		ImageView placeholderBackground = (ImageView) findViewById(R.id.game_background_placeholder);
		int width = placeholderBackground.getWidth();
		int height = placeholderBackground.getHeight();

		GameBuilder gameBuilder = new GameBuilder();
		GameBuilderDirector gameBuilderDirector = new GameBuilderDirector(
				gameBuilder);
		gameBuilderDirector.construct(this, this, this, this.getResources(),
				width, height, level);

		game = gameBuilder.getGame();
		// Hide the placeholder gameboard and show the proper gameboard
		placeholderBackground.setVisibility(View.GONE);
		GraphicsLoop graphicsLoop = (GraphicsLoop) findViewById(R.id.canvas);
		graphicsLoop.setVisibility(View.VISIBLE);
		game.start();
		game.pause();
	}

	/**
	 * If the activity is stopped and restarted, go to the level select screen.
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		Intent intent = new Intent(this, LevelSelect.class);
		startActivity(intent);
	}

	/**
	 * Disable the back button
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * Pause the game with the menu button
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!game.isPaused()) {
				game.pause();
				Intent intent = new Intent(this, Pause.class);
				startActivity(intent);
			} else {
				game.unPause();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * If this activity is paused, pause the game
	 */
	@Override
	public void onPause() {
		firstRun = false;
		if (game != null) {
			if (!game.isPaused()) {
				game.pause();
			}
		}
		super.onPause();
	}

	/**
	 * Explicitly end the activity when it's not visible. This significantly
	 * reduces the frequency of out of memory errors in the rest of the game.
	 */
	@Override
	public void onStop() {
		finish();
		super.onStop();
	}

	/**
	 * When a level ends, show the "End Level" activity
	 */
	@Override
	public void startEndLevel(Score score, Level level) {
		Intent intent = new Intent(this, EndLevel.class);
		intent.putExtra("score", score.get());
		intent.putExtra("level", level);
		startActivity(intent);
	}
}