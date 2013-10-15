package levels.gamebuilder;

import game.entities.GameBoard;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;
import android.media.SoundPool;

/**
 * Creates a meerkat using a meerkat builder
 * @author John Casson
 */
public class MeerkatBuilderDirector {
	public MeerkatBuilderDirector(GameBuilder gameBuilder, GameBoard gameBoard, SoundPool soundPool
			, GraphicsLoop graphicsLoop) {
		GameLoop gameLoop = new GameLoop();
		InputLoop inputLoop = new InputLoop();
		gameBuilder.addGameBoard(gameBoard);
		gameBuilder.addGameLoop(gameLoop);
		gameBuilder.addInputLoop(inputLoop);
		gameBuilder.addSoundPool(soundPool);
	}
	
	
}
