package game.loops;

import game.interfaces.Drawable;
import game.interfaces.GameComponent;
import game.interfaces.StopAction;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class GraphicsLoop extends View implements GameComponent, StopAction {
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
	public void play() {
		invalidate();
	}

	@Override
	public void onStop() {
		running = false;
	}
}
