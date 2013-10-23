package game.actor.interfaces;

import android.graphics.Rect;

public interface Locatable {
	// Sets the location
	public void setLocation(int x, int y);
	// Gets the size of the locatable
	public Rect getBounds();
}
