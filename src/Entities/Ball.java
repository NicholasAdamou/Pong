package Entities;

import java.awt.Graphics;
import java.util.Random;

import GameEngine.Game;

public class Ball extends Entity {
	private int xVelocity, yVelocity;
	
	private Paddle p1, p2;
	
	public static final String BALL_NAME = "Ball";
	public static final int SIZE = 10;
	public static final int DEFAULT_SPEED = 3;

	public Ball(String name, int x, int y, int width, int height, Paddle p1, Paddle p2) {
		super(name, x, y, width, height);
		this.p1 = p1;
		this.p2 = p2;
		
		xVelocity = DEFAULT_SPEED;
		yVelocity = DEFAULT_SPEED;
	}
	
	@Override
	public void tick(Game game) {
		if (x <= 0) { 
			Game.computerScore += 1;
			Game.newGame();
		} else if (x + Ball.SIZE >= Game.WIDTH) {
			Game.playerScore += 1;
			Game.newGame();
		}
		
		if (y <= 0) { 
			yVelocity = Ball.DEFAULT_SPEED;
		} else if (y + Ball.SIZE >= Game.HEIGHT) {
			yVelocity = -Ball.DEFAULT_SPEED;
		}
		
		if (checkCollisions(p1)) { 
			int randomDirection = new Random().nextInt(3);
			int randomNumber = 5;
			switch(randomDirection) { 
			case 0: 
				xVelocity = Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				yVelocity = -Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				break;
			case 1: 
				xVelocity = -Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				yVelocity = Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				break;
			case 2: 
				xVelocity = -Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				yVelocity = -Ball.DEFAULT_SPEED + new Random().nextInt(randomNumber);
				break;
			}
		}
		
		if (checkCollisions(p2)) { 
			int randomDirection = new Random().nextInt(3);
			switch(randomDirection) { 
			case 0: 
				xVelocity = -Ball.DEFAULT_SPEED;
				yVelocity = Ball.DEFAULT_SPEED;
				break;
			case 1: 
				xVelocity = Ball.DEFAULT_SPEED;
				yVelocity = -Ball.DEFAULT_SPEED;
				break;
			case 2: 
				xVelocity = -Ball.DEFAULT_SPEED;
				yVelocity = -Ball.DEFAULT_SPEED;
				break;
			}
		}
		
		x += xVelocity;
		y += yVelocity; 
		
		setBounds(x, y, Ball.SIZE, Ball.SIZE);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Entity.DEFAULT_COLOR);
		g.fillRect(this.x, this.y, Ball.SIZE, Ball.SIZE);
	}
	
	public void resetBall(int x, int y, int width, int height, Paddle p1, Paddle p2) { 
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.p1 = p1;
		this.p2 = p2;
		
		setBounds(x, y, width, height);
		
		xVelocity = Ball.DEFAULT_SPEED;
		yVelocity = Ball.DEFAULT_SPEED;
	}
}
