package Managers;

import java.awt.Color;
import java.awt.Graphics;

import GameEngine.Game;
import GameObjects.Net;
import Utilities.Utilities;

public class InterfaceManager {
	@SuppressWarnings("unused")
	private Game game; 
	
	public InterfaceManager(Game game) { 
		this.game = game;
	}
	
	public void render(Graphics g) {
		Net.render(g);
		Utilities.simpleMessage(g, Color.white, "" + Game.playerScore, Game.DEFAULT_FONT_FAMILY, Game.DEFAULT_FONT_SIZE, 10, 40);
		Utilities.complexMessage(g, Color.white, "" + Game.computerScore, Game.DEFAULT_FONT_FAMILY, Game.DEFAULT_FONT_SIZE, Game.WIDTH, 20, 40);
	}
}
