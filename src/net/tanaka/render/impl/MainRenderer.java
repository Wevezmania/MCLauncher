package net.tanaka.render.impl;


import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import net.tanaka.render.SceneRenderer;

public class MainRenderer extends SceneRenderer {
	
	private Image title = null;
	private Image launch = null;
	private Image launched = null;
	private int buttonX, buttonY; // ボタンの位置
    private boolean isPressed = false; // ボタンが押されたかどうか
    private long pressedTime = 0; // ボタンが押された時刻
	
	@Override
	public void init(GameContainer game) throws SlickException {
		
        launch = new Image("resources/launch.png");
        buttonX = 750;
        buttonY = 680;
    }
		// TODO Auto-generated method stub
	
	@Override
	public void render(GameContainer game, Graphics graphic) throws SlickException {
		
		graphic.setColor(new Color(0xff363636));
		graphic.fillRect (0, 0, 1080, 800);
		
		title = new Image("resources/title.png");
		title.draw();
	
        launch.draw(buttonX, buttonY);
        
        if (isPressed && System.currentTimeMillis() - pressedTime < 1000) {
            launched = new Image("resources/launched.png");
            launched.draw(buttonX, buttonY); }
     }
		// TODO Auto-generated method stub

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		Input input = gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int mouseX = input.getMouseX();
            int mouseY = input.getMouseY();
            
            if (mouseX > buttonX && mouseX < buttonX + launch.getWidth()
            && mouseY > buttonY && mouseY < buttonY + launch.getHeight()) {
            	
            isPressed = true;
            pressedTime = System.currentTimeMillis();
        }
		// TODO Auto-generated method stub
		
	}
	}
	
	private static class ClientPanel {
		
	}
}
