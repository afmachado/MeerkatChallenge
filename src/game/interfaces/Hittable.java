package game.interfaces;

import android.graphics.Rect;

public interface Hittable {
	// Does the passed rect hit this entity?
	public boolean isHit(Rect rectangle);
}
