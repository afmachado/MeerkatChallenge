package entities.meerkat;

import interfaces.HitDetector;
import interfaces.Hittable;
import interfaces.InputReceiver;
import interfaces.OnHitDetected;
import interfaces.Scorable;
import android.view.MotionEvent;
import android.view.View;

public class TouchHitDetector implements InputReceiver, Scorable, HitDetector {
	private OnHitDetected callback;
	private Hittable hittable;
	public TouchHitDetector(OnHitDetected hitDetected, Hittable hittable) {
		this.callback = hitDetected;
		this.hittable = hittable;
	}
	
	/**
	 * On user input, detect whether this Meerkat has been hit
	 */
	@Override
	public void onInput(View v, MotionEvent ev) {	
		final int action = ev.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			// Deliberately no break here
		case MotionEvent.ACTION_POINTER_DOWN:
			// Find the index of the action (for multitouch e.g.
			// 0 is the first finger down, 1 is the second)
			int actionIndex = ev.getActionIndex();
			if (hittable.isHit(ev.getX(actionIndex), ev.getY(actionIndex))) {
				callback.onHit();
			}
			break;
		}
	}
}
