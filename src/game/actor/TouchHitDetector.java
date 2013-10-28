package game.actor;

import game.actor.interfaces.Hittable;
import game.actor.interfaces.OnHitDetected;
import game.interfaces.status.ReceivesInput;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Detects whether a hittable has been hit
 * @author hqs71687
 *
 */
public class TouchHitDetector implements ReceivesInput  {
	private OnHitDetected callback;
	private Hittable hittable;
	private int hitMargin;
	
	/**
	 * Detects whether the hittable has been hit. If it has, we call back onHitDetected.
	 * @param onHitDetected
	 * @param hittable
	 * @param hitMargin Margin to be added to the hit area
	 */
	public TouchHitDetector(OnHitDetected onHitDetected, Hittable hittable, int hitMargin) {
		this.callback = onHitDetected;
		this.hittable = hittable;
		this.hitMargin = hitMargin;
	}
	
	/**
	 * On user input, detect whether the hittable has been hit and if it has 
	 * call back the callback.
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
			Rect hitArea = new Rect((int) x - hitMargin, (int) y - hitMargin, (int) x + hitMargin, (int) y + hitMargin);
			if (hittable.isOverlapping(hitArea)) {
				callback.onHit();
			}
			break;
		}
	}
}
