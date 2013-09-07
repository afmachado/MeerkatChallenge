package entities.meerkat;

import interfaces.Actor;

import java.util.Random;

import entities.Score;

public class PopUpBehavior implements Actor {
	// Minimum and maximum times (ms) for the meerkat to be on screen
	final int MIN_SHOW_TIME = 1000;
	final int MAX_SHOW_TIME = 4000;
	
	// Minimum and maximum times (ms) for the meerkat to stay hidden
	final int MIN_HIDE_TIME = 500;
	final int MAX_HIDE_TIME = 2000;
	
	private long nextHideTime = 0;
	private long nextShowTime = 0;

	// The meerkat this behavior controls
	Meerkat meerkat;
	Score score;

	public PopUpBehavior(Meerkat meerkat, Score score) {
		this.meerkat = meerkat;
		this.score = score;
	}

	/**
	 * What to do when this meerkat is hit.
	 * Add one to the score and show it again after a delay
	 */
	public void hit() {
		meerkat.hide();
		score.add(1);
		showDelayed();
	}

	/**
	 * Shows the meerkat after a delay
	 */
	public void showDelayed() {
		long showTime = MIN_HIDE_TIME + new Random().nextInt(MAX_HIDE_TIME);
		nextShowTime = System.currentTimeMillis() + showTime;
	}

	/**
	 * Hides the meerkat after a delay
	 */
	private void hideDelayed() {
		long hideTime = MIN_HIDE_TIME + new Random().nextInt(MAX_HIDE_TIME);
		nextHideTime = System.currentTimeMillis() + hideTime;

	}


	// Shows and hides the meerkat
	@Override
	public void act() throws Exception {
		// check whether to show or hide this meerkat
		long now = System.currentTimeMillis();
		if((!meerkat.getVisible()) && now > nextShowTime) {
			meerkat.show();
			hideDelayed();
		}
		
		if(meerkat.getVisible() && now > nextHideTime) {
			meerkat.hide();
			showDelayed();
		}
		
	}

}
