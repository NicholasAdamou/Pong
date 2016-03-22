package Managers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameEngine.Game;

public class KeyManager implements KeyListener {
	private Game game;
	
	public KeyManager(Game game) {
		this.game = game;
	
		game.addKeyListener(this);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_UP) Game.playerPaddle.isMovingUp = true;
		 else if (keyCode == KeyEvent.VK_DOWN) Game.playerPaddle.isMovingDown = true;
		
		if (keyCode == KeyEvent.VK_ESCAPE) Game.quitGame();
		if (Game.playerScore == Game.MAX_SCORE || Game.computerScore == Game.MAX_SCORE) {
			if (keyCode ==KeyEvent.VK_SPACE) Game.resetGame();
			game.inGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode == KeyEvent.VK_UP) Game.playerPaddle.isMovingUp = false;
		else if (keyCode == KeyEvent.VK_DOWN) Game.playerPaddle.isMovingDown = false;
	}
}
