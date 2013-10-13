package game.entities;

import game.interfaces.Behavior;
import game.interfaces.GameComponent;
import game.interfaces.Pausable;

import java.util.Random;

public class PopUpBehavior implements GameComponent, Behavior, Pausable {
	// Minimum and maximum times (ms) for the meerkat to be on screen
	final int MIN_SHOW_TIME = 1000;
	final int MAX_SHOW_TIME = 4000;
	
	// Minimum and maximum times (ms) for the meerkat to stay hidden
	final int MIN_HIDE_TIME = 500;
	final int MAX_HIDE_TIME = 2000;
	
	private long nextHideTime = 0;
	private long nextShowTime = 0;
	
	private boolean enabled = false;
	private long pauseTime;

	// The Actor this behavior controls
	Actor actor;

	public PopUpBehavior(Actor actor)  {
		this.actor = actor;
		// Set the actors to start showing at least a  second after being enabled
		this.nextShowTime = System.currentTimeMillis() + new Random().nextInt(500) + 1000;
	}

	/**
	 * What to do when we get hit.
	 * Hide the actor then show it again after a delay
	 */
	public void hit() {
		actor.hide();
		showDelayed();
	}

	/**
	 * Shows the actor after a delay
	 */
	public void showDelayed() {
		long showTime = MIN_HIDE_TIME + new Random().nextInt(MAX_HIDE_TIME);
		nextShowTime = System.currentTimeMillis() + showTime;
	}

	/**
	 * Hides the actor after a delay
	 */
	private void hideDelayed() {
		long hideTime = MIN_SHOW_TIME + new Random().nextInt(MAX_SHOW_TIME);
		nextHideTime = System.currentTimeMillis() + hideTime;
	}


	// Shows and hides the actor
	@Override
	public void play() {
		if(!enabled) {
			return;
		}

		long now = System.currentTimeMillis();
		if(actor.isVisible() && now > nextHideTime) {
			actor.hide();
			showDelayed();
		}	
		
		
		if((!actor.isVisible()) && now > nextShowTime) {
			actor.show();
			hideDelayed();
		}
	}

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void onPause() {
		pauseTime = System.currentTimeMillis();
	}

	@Override
	public void onUnPause() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		nextShowTime = nextShowTime + pausedTime;
		nextHideTime = nextHideTime + pausedTime;
	}

}
