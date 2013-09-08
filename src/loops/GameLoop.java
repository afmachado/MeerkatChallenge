package loops;

import interfaces.GameComponent;
import interfaces.StopAction;
import interfaces.StopCondition;

import java.util.ArrayList;

import android.os.Handler;

public class GameLoop {
	// Divide the frame by 1000 to calculate how many times per second the
	// screen will update.
	private static final int FRAME_RATE = 20; // 50 frames per second
	
	private Handler frame = new Handler();
	private ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	private ArrayList<StopCondition> stopConditions = new ArrayList<StopCondition>();
	private ArrayList<StopAction> stopAction = new ArrayList<StopAction>();
	
	public void addGameComponent(GameComponent a) {
		components.add(a);
	}
	
	public void addStopAction(StopAction stopAction) {
		this.stopAction.add(stopAction);
	}
	
	public void start() {
		frame.removeCallbacks(frameUpdate);
		frame.postDelayed(frameUpdate, FRAME_RATE);
	}
	
	public Runnable frameUpdate = new Runnable() {
		@Override
		synchronized public void run() {
			for(GameComponent gameComponent : components) {
				try {
					gameComponent.play();
				} catch (Exception e) {
					// TODO: Global exception handling
					e.printStackTrace();
				}
			}
			
			for(StopCondition sc : stopConditions) {
				frame.removeCallbacks(frameUpdate);
				if(sc.stopCondition()) {
					for(StopAction sa : stopAction) {
						sa.onStop();
						
					}
					
					return;
				}
			}
			
			frame.removeCallbacks(frameUpdate);
			frame.postDelayed(frameUpdate, FRAME_RATE);
		}
	};

	public void registerStop(StopCondition sc) {
		stopConditions.add(sc);
	}
}
