package net.tanaka.main;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import net.tanaka.heper.OSHelper.OSType;
import net.tanaka.setting.LauncherSettings;
import net.tanaka.version.Client;

public class Start extends BasicGame {
	
	public static String TITLE = "KotaLauncher",
			VERSION = "1.0";

	private static final Start INSTANCE = new Start(String.format("%s %s", TITLE, VERSION));

	public static final OSType OS;
	
	public static final File ROOT = new File(String.format("%s/AppData/Roaming/%s", System.getProperty("user.home"), TITLE)),
			LIB_ROOT = new File(ROOT, "libraries"),
			VER_ROOT = new File(ROOT, "versions"),
			LWJ_ROOT = new File(ROOT, "lwjgl"),
			ASS_ROOT = new File(ROOT, "assets");
	
	static {
		final String OS_NAME = System.getProperty("os.name").toLowerCase();
		if (OS_NAME.startsWith("linux")) OS = OSType.LINUX;
		else if (OS_NAME.startsWith("mac")) OS = OSType.MACOS;
		else if (OS_NAME.startsWith("windows")) OS = OSType.WINDOWS;
		else OS = null;
	}
	
	public Start(final String title) {
		super(title);
	}
	
	public static void main(final String[] args) throws SlickException {
		if (!ROOT.exists()) ROOT.mkdirs();
		final File[] needed = { LIB_ROOT, VER_ROOT, LWJ_ROOT, ASS_ROOT };
		for (final File f : needed) {
			if (!f.exists()) {
				f.mkdir();
			}
		}
		final AppGameContainer app = new AppGameContainer(INSTANCE);
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(LauncherSettings.DEBUG.getValue());
		app.setTargetFrameRate(LauncherSettings.FPS.getValue());
		/*try {
			new Client(new File(VER_ROOT, "1.8.8")).launch();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		app.start();
		
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
