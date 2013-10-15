package levels.gamebuilder;

import game.entities.Game;
import game.entities.GameBoard;
import game.loops.GameLoop;
import game.loops.InputLoop;
import android.media.SoundPool;

public class ConcreteGameBuilder implements GameBuilder {
	private Game game = new Game();

	@Override
	public void addInputLoop(InputLoop inputLoop) {
		game.setInputLoop(inputLoop);
	}

	@Override
	public void addGameLoop(GameLoop gameLoop) {
		game.setGameLoop(gameLoop);
	}

	@Override
	public void addGameBoard(GameBoard gameBoard) {
		game.setGameBoard(gameBoard);
	}

	@Override
	public void addSoundPool(SoundPool soundPool) {
		game.setSoundPool(soundPool);
	}

	@Override
	public Game getGame() {
		return game;
	}

}
