package game.actor;

import game.actor.interfaces.Hittable;
import game.actor.interfaces.OnHitDetected;
import game.interfaces.status.ReceivesInput;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class TouchHitDetector implements ReceivesInput  {
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
			float x = ev.getX(actionIndex);
			float y = ev.getY(actionIndex);
			// Define a "hit area" that's wider than the point given
			Rect hitArea = new Rect((int) x - 5, (int) y - 5, (int) x + 5, (int) y + 5);
			if (hittable.isOverlapping(hitArea)) {
				callback.onHit();
			}
			break;
		}
	}
}
