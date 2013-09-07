package loops;

import interfaces.Actor;
import interfaces.Drawable;
import interfaces.StopAction;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class GraphicsLoop extends View implements Actor, StopAction {
	ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	BitmapDrawable background;
	boolean running = true;
	
	public GraphicsLoop(Context context, AttributeSet aSet) {
		super(context, aSet);
	}
	
	@Override
	synchronized public void onDraw(Canvas canvas) {
		if(!running) {
			return;
		}
		for(Drawable d : drawables) {
			d.draw(canvas);
		}
	}

	public void register(Drawable drawable) {
		drawables.add(drawable);
	}

	@Override
	public void act() {
		invalidate();
	}

	@Override
	public void onStop() {
		running = false;
	}
}
