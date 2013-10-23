package game.loops;

import game.interfaces.Stoppable;
import game.interfaces.status.GameComponent;
import game.interfaces.status.OnStopListener;
import game.interfaces.status.Pausable;

import java.util.ArrayList;

import android.os.Handler;

public class GameLoop implements Pausable {
	// Divide the frame by 1000 to calculate how many times per second the
	// screen will update.
	private static final int FRAME_RATE = 20; // 50 frames per second

	private Handler frame = new Handler();
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	private ArrayList<Stoppable> stoppables = new ArrayList<Stoppable>();
	private ArrayList<OnStopListener> stopListeners = new ArrayList<OnStopListener>();

	public void addGameComponent(GameComponent a) {
		components.add(a);
	}

	public void addStopListener(OnStopListener stopAction) {
		this.stopListeners.add(stopAction);
	}

	public void start() {
		frame.removeCallbacks(frameUpdate);
		frameUpdate.run();
	}

	public Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run() {
			for (GameComponent gameComponent : components) {
				try {
					gameComponent.play();
				} catch (Exception e) {
					// TODO: Global exception handling
					e.printStackTrace();
				}
			}

			for (Stoppable sc : stoppables) {
				frame.removeCallbacks(frameUpdate);
				if (sc.stopCondition()) {
					for (OnStopListener sa : stopListeners) {
						sa.onStop();
					}
					
					return;
				}
			}

			frame.removeCallbacks(frameUpdate);
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
	};

	/**
	 * Registers a stoppable to be stopped
	 * when the game loop stops
	 * @param sc Stoppable to be stopped
	 */
	public void registerStoppable(Stoppable sc) {
		stoppables.add(sc);
	}

	public void stop() {
		frame.removeCallbacks(frameUpdate);
	}

	@Override
	public void onPause() {
		frame.removeCallbacks(frameUpdate);

	}

	@Override
	public void onUnPause() {
		frameUpdate.run();
	}
}