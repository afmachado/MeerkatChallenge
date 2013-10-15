package levels.gamebuilder;

import game.entities.Game;
import game.entities.GameBoard;
import game.loops.GameLoop;
import game.loops.InputLoop;
import android.media.SoundPool;

public interface GameBuilder {
	public void addInputLoop(InputLoop inputLoop);
	public void addGameLoop(GameLoop gameLoop);
	public void addGameBoard(GameBoard gameBoard);
	public void addSoundPool(SoundPool soundPool);
	public Game getGame();
}
