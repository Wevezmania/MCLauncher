package net.tanaka.render;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class SceneRenderer {
	
	public abstract void render(GameContainer game, Graphics graphic) throws SlickException;
	public abstract void init(GameContainer game) throws SlickException;
	public abstract void update(GameContainer game, int arg1) throws SlickException;

}
