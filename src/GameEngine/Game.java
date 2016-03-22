package GameEngine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import Entities.Ball;
import Entities.Paddle;
import Managers.InterfaceManager;
import Managers.KeyManager;
import Utilities.Utilities;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	private static final String NAME = "Pong";
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 4 * 3; 
	private static final int SCALE = 1;
	
	private boolean isRunning = false;
	
	@SuppressWarnings("unused")
	private KeyManager keyManager;
	private static InterfaceManager interfaceManager;
	public static Paddle playerPaddle;
	public static Paddle computerPaddle;
	public static Ball gameBall; 
	
	public static int playerScore = 0, computerScore = 0;
	public static final int MAX_SCORE = 11;
	public boolean inGame = true;
	
	public static final String DEFAULT_FONT_FAMILY = "monospaced";
	public static final int DEFAULT_FONT_SIZE = 36;
	
	public Game() { 
		init();
	}
	
	private void init() {
		int distanceFromWindow = 0;
		playerPaddle = new Paddle(Paddle.PLAYER_ONE_NAME, distanceFromWindow, (Game.HEIGHT / 2) - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
		computerPaddle = new Paddle(Paddle.PLAYER_TWO_NAME, (Game.WIDTH - Paddle.WIDTH) - distanceFromWindow, (Game.HEIGHT / 2) - (Paddle.HEIGHT / 2), Paddle.WIDTH, Paddle.HEIGHT);
		gameBall = new Ball(Ball.BALL_NAME, new Random().nextInt((Game.WIDTH / 2) - Ball.SIZE + 20), new Random().nextInt((Game.HEIGHT / 2) - Ball.SIZE + 20), 
				Ball.SIZE, Ball.SIZE, playerPaddle, computerPaddle);
		interfaceManager = new InterfaceManager(this);
		keyManager = new KeyManager(this);
	}
	
	public void start() {
		new Thread(this, Game.NAME);
		isRunning = true;
	}
	
	public void stop() { 
		isRunning = false;
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		init();

		while (isRunning) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	private void tick() {
		if (inGame) { 
			playerPaddle.tick(this);
			computerPaddle.tick(this);
			gameBall.tick(this);
		} else if (isGameOver()) inGame = false;
	}
	
	private void render() { 
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (isGameOver()) {
			Game.endGame(g);
			inGame = false;
		}
		
		if (inGame) {
			interfaceManager.render(g);
		
			gameBall.render(g);
			playerPaddle.render(g);
			computerPaddle.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) { 
		Game game = new Game();
		
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
		game.run();
	}
	
	private boolean isGameOver() { 
		return Game.playerScore == Game.MAX_SCORE || Game.computerScore == Game.MAX_SCORE;
	}
	
	public static void resetGame() { 
		playerPaddle.resetEntity(2, (Game.HEIGHT / 2) - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
		computerPaddle.resetEntity((Game.WIDTH - Paddle.WIDTH) - 2, (Game.HEIGHT / 2) - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
		gameBall.resetBall((Game.WIDTH / 2) - Ball.SIZE, (Game.HEIGHT / 2) - Ball.SIZE, Ball.SIZE, Ball.SIZE, playerPaddle, computerPaddle);
		
		Game.setPlayerScore(0);
		Game.setComputerScore(0);
	}
	
	public static void newGame() { 
		playerPaddle.resetEntity(2, (Game.HEIGHT / 2) - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
		computerPaddle.resetEntity((Game.WIDTH - Paddle.WIDTH) - 2, (Game.HEIGHT / 2) - Paddle.HEIGHT / 2, Paddle.WIDTH, Paddle.HEIGHT);
		gameBall.resetBall((Game.WIDTH / 2) - Ball.SIZE, (Game.HEIGHT / 2) - Ball.SIZE, Ball.SIZE, Ball.SIZE, playerPaddle, computerPaddle);
	}
	
	public static void endGame(Graphics g) { 
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		int fontSize = 16;
		Utilities.simpleMessage(g, Color.white, "press [Space] to restart the Game.", Game.DEFAULT_FONT_FAMILY, fontSize, (Game.WIDTH / 2) - Utilities.getWidthOfString("press [Space] to resert the Game.") + 20, Game.HEIGHT / 2);
	}
	
	public static void quitGame() { 
		System.exit(0);
	}

	public int getPlayerScore() {
		return playerScore;
	}
	
	public int getComputerScore() { 
		return computerScore;
	}

	public static void setPlayerScore(int playerScore) {
		Game.playerScore = playerScore;
	}

	public static void setComputerScore(int computerScore) {
		Game.computerScore = computerScore;
	}
	
	public static InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}
}
