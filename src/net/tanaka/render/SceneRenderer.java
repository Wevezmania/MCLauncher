package net.tanaka.render;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class SceneRenderer {
	
	public abstract void render(GameContainer game, Graphics graphic) throws SlickException;
	public abstract void init(GameContainer game) throws SlickException;
	public abstract void update(GameContainer game, int arg1) throws SlickException;
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
