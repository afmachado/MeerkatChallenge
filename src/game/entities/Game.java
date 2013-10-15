package game.entities;

import game.interfaces.Pausable;
import game.loops.GameLoop;
import game.loops.InputLoop;

import java.util.ArrayList;

import android.media.SoundPool;

public class Game {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GameBoard gameBoard;
	private SoundPool soundPool;
	public boolean paused = true;
	public boolean started = false;
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();


	public void setSoundPool(SoundPool soundPool) {
		this.soundPool = soundPool;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void setGameLoop(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}
	
	public void setInputLoop(InputLoop inputLoop) {
		this.inputLoop = inputLoop;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	public InputLoop getInputLoop() {
		return inputLoop;
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
	
	public void start() {
		getGameLoop().start();
		paused = false;
		started = true;
	}
	
	public void pause() {
		for(Pausable pausable : pausables) {
			pausable.onPause();
		}
		paused = true;
	}
	
	public void unPause() {
		for(Pausable pausable : pausables) {
			pausable.onUnPause();
		}
		paused = false;
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void addPausable(Pausable pausable) {
		pausables.add(pausable);
	}
	
	/**
	 * Has the game been started?
	 * @return Boolean
	 */
	public boolean isStarted() {
		return started;
	}
	
	public SoundPool getSoundPool() {
		return soundPool;
	}
}
