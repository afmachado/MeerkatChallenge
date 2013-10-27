package game.actor.interfaces;

import android.graphics.Rect;

public interface Hittable {
	// Does the passed rect hit this entity?
	public boolean isOverlapping(Rect rectangle);
}
