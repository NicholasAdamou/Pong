package Entities;

import java.awt.Graphics;

import GameEngine.Game;

public class Paddle extends Entity {
	public static final String PLAYER_ONE_NAME = "Player One";
	public static final String PLAYER_TWO_NAME = "Player Two";
	public static final int WIDTH = 10;
	public static final int HEIGHT = 100;
	public static final int DEFAULT_SPEED = 6;
	
	public boolean isMovingUp = false, isMovingDown = false;

	public Paddle(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}

	@Override
	public void tick(Game game) {
		setBounds(x, y, Paddle.WIDTH, Paddle.HEIGHT);
		if (this.getName().equalsIgnoreCase(Paddle.PLAYER_ONE_NAME)) movePlayer();
		else if (this.getName().equalsIgnoreCase(Paddle.PLAYER_TWO_NAME)) moveEntity(game);
	}
	
	private void movePlayer() { 
		if (isMovingUp && y >= 0) y -= DEFAULT_SPEED;
		if (isMovingDown && y <= Game.HEIGHT - Paddle.HEIGHT) y += DEFAULT_SPEED;
	}
	
	private void moveEntity(Game game) { 
		if (Game.gameBall.y < y + Paddle.HEIGHT && y >= 0) y -= Paddle.DEFAULT_SPEED;
		if (Game.gameBall.y > y && y <= Game.HEIGHT - Paddle.HEIGHT) y += Paddle.DEFAULT_SPEED;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Entity.DEFAULT_COLOR);
		g.fillRect(this.x, this.y, Paddle.WIDTH, Paddle.HEIGHT);
	}
}
