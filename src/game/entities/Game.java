package game.entities;

import java.util.ArrayList;

import game.interfaces.Pausable;
import game.loops.GameLoop;
import game.loops.GraphicsLoop;
import game.loops.InputLoop;

public class Game {
	private GameLoop gameLoop;
	private InputLoop inputLoop;
	private GraphicsLoop graphicsLoop;
	private GameBoard gameBoard;
	public boolean paused = true;
	private ArrayList<Pausable> pausables = new ArrayList<Pausable>();
	
	public Game(GameLoop gameLoop, InputLoop inputLoop,
			GraphicsLoop graphicsLoop, GameBoard gameBoard) {
		this.gameLoop = gameLoop;
		this.inputLoop = inputLoop;
		this.graphicsLoop = graphicsLoop;
		this.gameBoard = gameBoard;
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
		paused = false;
		gameLoop.start();
	}
	
	public void pause() {
		paused = true;
		gameLoop.stop();
		for(Pausable pausable : pausables) {
			pausable.onPause();
		}
	}
	
	public void unPause() {
		paused = false;
		gameLoop.start();
		for(Pausable pausable : pausables) {
			pausable.onUnPause();
		}
	}
	
	public boolean isPaused() {
		return paused;
	}

	public void addPausable(Pausable pausable) {
		pausables.add(pausable);
	}
}
