package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameEngine.Game;
import Utilities.Utilities;

public abstract class Entity {
	private String name;
	public int x, y, width, height;
	public Rectangle collisionBox;
	
	public static final Color DEFAULT_COLOR = Color.white;
	public static final Color DEBUG_COLOR = Color.red;
	
	public Entity(String name, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		setBounds(x, y, width, height);
	}
	
	public void setBounds(int x, int y, int width, int height) { 
		this.collisionBox = new Rectangle(x, y, width, height);
	}
	
	public boolean checkCollisions(Entity e) {
		if (this.collisionBox.intersects(e.collisionBox.getBounds())) { 
			return true;
		}
		return false;
	}
	public abstract void tick(Game game);
	public abstract void render(Graphics g);
	
	public void renderDebug(Graphics g, Entity e) {
		g.setColor(Entity.DEBUG_COLOR);
		g.drawRect(e.collisionBox.x, e.collisionBox.y, width, height);
	}
	
	public void printDebug(Entity e) { 
		Utilities.printMessage("[Name]: " + e.getName());
		Utilities.printMessage("[x]: " + e.getX());
		Utilities.printMessage("[y]: " + e.getY());
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Rectangle getCollisionBox() { 
		return collisionBox;
	}
	
	public void resetEntity(int x, int y, int width, int height) { 
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		setBounds(x, y, width, height);
	}
}
