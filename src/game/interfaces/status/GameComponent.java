package game.interfaces.status;
/**
 * Implemented by a component of the game can be "played"
 * e.g. the behavior of an actor
 * @author John Casson
 *
 */
public interface GameComponent {
	/**
	 * Actions the GameComponent takes for
	 * each iteration of the game loop.
	 * @throws Exception
	 */
	public void play();
}
