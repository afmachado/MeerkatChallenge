package interfaces;

import android.graphics.Rect;

public interface Locatable {
	// Sets the location
	public void setLocation(int x, int y);
	// Gets the size of the locatable
	public Rect getBounds();
	// Gets the size of the locatable's container
	public Rect getContainerSize();
	// Finds out whether the container already holds an object at a location
	// TODO: Refactor this
	public boolean doesOverlap(int x, int y);
}
