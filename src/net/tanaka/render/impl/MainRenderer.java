package net.tanaka.render.impl;


import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import net.tanaka.main.Start;
import net.tanaka.render.SceneRenderer;
import net.tanaka.version.Client;

public class MainRenderer extends SceneRenderer {
	
	private Image title = null;
	private Image launch = null;
	private Image launched = null;
	private boolean pressed = false; // ボタンが押されたか
	private int buttonX, buttonY; // ボタンの位置
	private UnicodeFont font;
	
	private final List<ClientPanel> panels = new ArrayList<>();
	
	@Override
	public void init(GameContainer game) throws SlickException {
		for (File f : Start.VER_ROOT.listFiles()) {
			if (!f.isFile()) {
				try {
					panels.add(new ClientPanel(new Client(f)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!this.panels.isEmpty()) this.panels.get(0).selected = true;
		title = new Image("resources/title.png");
		
        launch = new Image("resources/launch.png");
        
        launched = new Image("resources/launched.png");
        // font 初期化
        font = new UnicodeFont("resources/font/medium.otf", 50, false, false);
        font.getEffects().add(new ColorEffect());
        font.addAsciiGlyphs();
        this.font.loadGlyphs();
        
        buttonX = 750;
        buttonY = 680;
    }
	
	@Override
	public void render(GameContainer game, Graphics graphic) throws SlickException {
		graphic.setColor(new Color(0xff363636));
		graphic.fillRect (0, 0, 1080, 800);
		
		graphic.setColor(new Color(0xff2C2C2C));
		graphic.fillRect (0, 0, 300, 1080);
		
		title.draw();
		if (pressed) {
            launched.draw(buttonX, buttonY);
        } else {
        	 launch.draw(buttonX, buttonY);
        }
		graphic.setColor(Color.white);
		for (int i = 0, l = this.panels.size(); i < l; i++) {
			final ClientPanel p = this.panels.get(i);
			final Rectangle buttonRect = new Rectangle(0, 56 + i * 100, 300, 100);
			graphic.setColor(new Color(p.selected ? 0xff4CE134 : 0xff008001));
			graphic.fill(buttonRect);
			font.drawString(20, 75 + i * 100, p.parent.getName());
			if (p.selected) {
				font.drawString(350, 80, p.parent.getName());
			}
		}
		//mania.draw(0, 56);
        
     }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int mouseX = input.getMouseX();
            int mouseY = input.getMouseY();
            if (mouseX > buttonX && mouseX < buttonX + launch.getWidth() && mouseY > buttonY && mouseY < buttonY + launch.getHeight()) {
            	
            	this.pressed = true;
            	return;
            }
            ClientPanel selectedPanel = null;
            for (int i = 0, l = this.panels.size(); i < l; i++) {
    			final ClientPanel p = this.panels.get(i);
    			final Rectangle buttonRect = new Rectangle(0, 56 + i * 100, 300, 100);
    			if (buttonRect.contains(mouseX, mouseY)) {
    				selectedPanel = p;
    			}
    		}
            if (selectedPanel != null) {
            	for (final ClientPanel p : this.panels) {
            		p.selected = p == selectedPanel;
            	}
            }
		}
	}
	
	private static class ClientPanel {
		
		private boolean selected;
		private final Client parent;
		
		public ClientPanel(Client client) {
			this.parent = client;
			this.selected = false;
		}
		
	}
}
