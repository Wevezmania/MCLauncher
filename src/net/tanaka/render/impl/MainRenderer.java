package net.tanaka.render.impl;


import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.tanaka.render.SceneRenderer;

public class MainRenderer extends SceneRenderer {
	
	private Image title = null;
	private Image launch = null;
	
	@Override
	public void init(GameContainer game) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GameContainer game, Graphics graphic) throws SlickException {
		
		graphic.setColor(new Color(0xff363636));
		graphic.fillRect (0, 0, 1080, 800);
		
		title = new Image("resources/title.png");
		title.draw();
		
		launch = new Image("resources/launch.png");
        launch.draw(750, 680);
     }
		// TODO Auto-generated method stub

	@Override
	public void update(GameContainer game, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	private static class ClientPanel {
		
	}

}
