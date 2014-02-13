package eu.johncasson.meerkatchallenge.game.actor;

import eu.johncasson.meerkatchallenge.game.interfaces.status.GameComponent;
import eu.johncasson.meerkatchallenge.game.interfaces.status.Pausable;

import java.util.Random;

/**
 * Makes an Actor pop up, then hide itself after a short time
 * @author John Casson
 *
 */
class PopUpBehavior implements GameComponent, Pausable {
	/**
	 * Minimum time (ms) for the Actor to be shown
	 */
	final int MIN_SHOW_TIME = 1000;
	/**
	 * Maximum time (ms) for the Actor to be shown
	 */
	final int MAX_SHOW_TIME = 4000;

	/**
	 * Minimum time (ms) for the Actor to be hidden
	 */
	final int MIN_HIDE_TIME = 500;
	/**
	 * Maximum time (ms) for the Actor to be shown
	 */
	final int MAX_HIDE_TIME = 2000;

	/**
	 * The time at which the Actor should be hidden
	 */
	private long nextHideTime = 0;
	/**
	 * The time at which the Actor should be shown
	 */
	private long nextShowTime = 0;

	private long pauseTime;

	/**
	 * The actor controlled by this behavior
	 */
	Actor actor;

	/**
	 * Creates a new PopUpBehavior that controls the passed Actor.
	 * Sets the next show time to be at least a second after creation.
	 */
	public PopUpBehavior(Actor actor) {
		this.actor = actor;
		// Set the actors to start showing at least a second after being enabled
		this.nextShowTime = System.currentTimeMillis()
				+ new Random().nextInt(500) + 1000;
	}

	/**
	 * When the actor is hit, hide the actor then show it again after a
	 * delay
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

	/**
	 * Repeatedly shows and hides the Actor
	 */
	@Override
	public void play() {
		long now = System.currentTimeMillis();
		if (actor.isVisible() && now > nextHideTime) {
			actor.hide();
			showDelayed();
		}

		if ((!actor.isVisible()) && now > nextShowTime) {
			actor.show();
			hideDelayed();
		}
	}

	/**
	 * Pauses the behavior by saving the pause time.
	 */
	@Override
	public void onPause() {
		pauseTime = System.currentTimeMillis();
	}
	
	/**
	 * Unpauses the behavior by updating the next show and next hide times
	 * to reflect the time we spent paused.
	 */
	@Override
	public void onUnPause() {
		long pausedTime = System.currentTimeMillis() - pauseTime;
		nextShowTime = nextShowTime + pausedTime;
		nextHideTime = nextHideTime + pausedTime;
	}

}
