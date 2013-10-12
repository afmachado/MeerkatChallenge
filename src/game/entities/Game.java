package game.entities;

import game.interfaces.Pausable;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;

import java.util.ArrayList;

import android.media.SoundPool;

public class Game {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private GameBoard gameBoard;
	private SoundPool soundPool;
	public boolean paused = true;
	public boolean started = false;
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();
	
	public Game(GameLoop gameLoop, InputLoop inputLoop,
			GraphicsLoop graphicsLoop, GameBoard gameBoard,
			SoundPool soundPool) {
		this.gameLoop = gameLoop;
		this.inputLoop = inputLoop;
		this.graphicsLoop = graphicsLoop;
		this.gameBoard = gameBoard;
		this.soundPool = soundPool;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	public GraphicsLoop getGraphicsLoop() {
		return graphicsLoop;
	}
	
	public InputLoop getInputLoop() {
		return inputLoop;
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
	
	public void start() {
		gameLoop.start();
		paused = false;
		started = true;
	}
	
	public void pause() {
		gameLoop.stop();
		for(Pausable pausable : pausables) {
			pausable.onPause();
		}
		paused = true;
	}
	
	public void unPause() {
		for(Pausable pausable : pausables) {
			pausable.onUnPause();
		}
		gameLoop.start();
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
