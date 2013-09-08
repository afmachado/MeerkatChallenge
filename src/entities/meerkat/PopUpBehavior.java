package entities.meerkat;

import interfaces.GameComponent;

import java.util.Random;

public class PopUpBehavior implements GameComponent {
	// Minimum and maximum times (ms) for the meerkat to be on screen
	final int MIN_SHOW_TIME = 1000;
	final int MAX_SHOW_TIME = 4000;
	
	// Minimum and maximum times (ms) for the meerkat to stay hidden
	final int MIN_HIDE_TIME = 500;
	final int MAX_HIDE_TIME = 2000;
	
	private long nextHideTime = 0;
	private long nextShowTime = 0;

	// The meerkat this behavior controls
	Sprite sprite;

	public PopUpBehavior(Sprite meerkat) {
		this.sprite = meerkat;
	}

	/**
	 * What to do when this meerkat is hit.
	 * Add one to the score and show it again after a delay
	 */
	public void hit() {
		sprite.hide();
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
		long hideTime = MIN_SHOW_TIME + new Random().nextInt(MAX_SHOW_TIME);
		nextHideTime = System.currentTimeMillis() + hideTime;

	}


	// Shows and hides the actor
	@Override
	public void play() throws Exception {
		// check whether to show or hide this meerkat
		long now = System.currentTimeMillis();
		if((!sprite.isVisible()) && now > nextShowTime) {
			sprite.show();
			hideDelayed();
		}
		
		if(sprite.isVisible() && now > nextHideTime) {
			sprite.hide();
			showDelayed();
		}
		
	}

}
