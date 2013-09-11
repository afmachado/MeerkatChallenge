package game.entities;

import levels.GameFactory;
import levels.Level;
import levels.StartLevel;
import meerkatchallenge.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

public class Game extends Activity {
	// To detect whether the game needs resetting
	// This is set to true if the Activity goes into the background
	// It's checked in the onResume activity
	boolean needsResetting = false;

	Level challenge;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		challenge = (Level) getIntent().getExtras().getSerializable(
				"main.challenge");
		final Game ga = this;

		Handler h = new Handler();
		// We can't initialize the graphics immediately because the layout
		// manager needs to run first, thus call back in a sec.
		h.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					new GameFactory().createGame(ga, challenge);
				} catch (Exception e) {
					// TODO global exception handler
					e.printStackTrace();
				}
			}
		}, 1000);
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
	 * When resuming the activity, see if we were running before (needsResetting
	 * = true) and if so, reset the activity.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (needsResetting) {
			needsResetting = false;
			reset();
		}
	}

	/**
	 * If the activity is stopped, set a flag to reset it next time it's brought
	 * forwards
	 */
	protected void onStop() {
		super.onStop();
		needsResetting = true;
	}

	/**
	 * Stop the back button from doing anything
	 */
	@Override
	public void onBackPressed() {
	}
}
