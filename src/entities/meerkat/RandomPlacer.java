package entities.meerkat;

import interfaces.Locatable;
import interfaces.Placer;

import java.util.Random;

import android.graphics.Rect;
import android.util.Log;

public class RandomPlacer implements Placer {

	@Override
	public void place(Locatable locatable) throws Exception {
		Random r = new Random();
		int minX = 0;
		int minY = 0;
		int x = 0;
		int y = 0;

		int width = locatable.getContainerSize().width();
		Rect rect1 = locatable.getBounds();
		int maxX = width - rect1.width();
		int maxY = locatable.getContainerSize().height() - locatable.getBounds().height();

		int count = 0;
		do {
			x = r.nextInt(maxX - minX + 1) + minX;
			y = r.nextInt(maxY - minY + 1) + minY;
			count++;
			if (count > 100) {
				Log.e("JC", "Can't place locatable");
				throw new Exception("Can't place locatable");
			}
		} while (locatable.doesOverlap(x, y));
		locatable.setLocation(x, y);
	}
}
