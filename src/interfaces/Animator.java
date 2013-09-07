package interfaces;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public interface Animator {
	public void animate(Bitmap bm, Matrix matrix);
	public Bitmap getBitmap();
	public Matrix getMatrix();
}
