package GameObjects;

import java.awt.Color;
import java.awt.Graphics;

import GameEngine.Game;

public class Net {
	private static int SPACE_BETWEEN = 25;
	public static final int NET_SIZE = 10;
	public static final Color DEFAULT_COLOR = Color.white;
	
	private static int x = (Game.WIDTH / 2) - (Net.NET_SIZE / 2);
	
	public static void render(Graphics g) {
		g.setColor(Net.DEFAULT_COLOR);
		for (int y = 0; y < Game.HEIGHT; y += SPACE_BETWEEN) { 
			g.fillRect(Net.x, y, Net.NET_SIZE, Net.NET_SIZE);
		}
	}
	
}
