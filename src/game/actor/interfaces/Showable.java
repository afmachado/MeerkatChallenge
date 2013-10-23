package game.actor.interfaces;

public interface Showable {
	// Can you see this showable?
	public boolean isVisible();
	public void show() throws Exception;
	public void hide() throws Exception;
}
