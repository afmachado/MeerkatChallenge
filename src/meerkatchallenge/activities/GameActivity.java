package meerkatchallenge.activities;

import game.entities.Game;
import levels.GameFactory;
import levels.Level;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

public class GameActivity extends Activity {
	private Game game;
	private Level level;
	private boolean firstRun = true;

	/**
	 * Bundle contains an optional name of an activity to call back Activity is
	 * passed the level number in the bundle e.g. a "Start Level" overlay
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		level = (Level) getIntent().getExtras().getSerializable("level");
		final GameActivity ga = this;

		// When in this activity make the volume buttons control the music
		// volume (e.g. vs the ringtone volume)
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		Intent intent = new Intent(ga, StartLevel.class);
		intent.putExtra("level", level);
		startActivity(intent);
	}

	/**
	 * Resets the game
	 */
	public void reset() {
		Intent intent = new Intent(this, StartLevel.class);
		finish();
		// Hide the default animation
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
	}

	/**
	 * Convert from pixels to density independent pixels
	 * 
	 * @param dp
	 *            Density dependent pixel value to convert
	 * @return int The size in density independent pixels
	 */
	public int dpToPx(int dp) {
		DisplayMetrics displayMetrics = getApplicationContext().getResources()
				.getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	/**
	 * Unpause the game when the activity is resumed
	 */
	@Override
	protected void onResume() {
		// Draw the gameboard the second time onResume is called
		// (After the user presses "Go" in StartLevel
		if (game == null && !firstRun) {
			game = new GameFactory().createGame(this, level);
			game.start();
			game.pause();
		}
		// game won't be initialized for the first onResume call
		// as onResume is called when an activity first starts
		if (game != null && game.isStarted()) {
			game.unPause();
		}
		super.onResume();
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
	 * Stop the back button from doing anything
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * Pause the game with the menu button
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (game.isPaused()) {
				game.unPause();
			} else {
				game.pause();
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
			game.pause();
		}
		super.onPause();
	}
}