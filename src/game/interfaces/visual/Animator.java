package game.interfaces.visual;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animator {
	public void animate();
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
