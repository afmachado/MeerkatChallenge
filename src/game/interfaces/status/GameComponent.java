package game.interfaces.status;
/**
 * Implemented by a component of the game can be "played"
 * e.g. the behavior of an actor
 * @author John Casson
 *
 */
public interface GameComponent {
	public void play() throws Exception;
}
