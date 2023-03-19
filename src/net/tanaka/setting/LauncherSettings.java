package net.tanaka.setting;

import java.util.ArrayList;
import java.util.List;

import net.tanaka.setting.impl.*;

public class LauncherSettings {
	
	private static final List<Setting<?>> SETTINGS = new ArrayList<>();
	
	public static final IntegerSetting FPS;
	public static final BooleanSetting DEBUG;
	
	static {
		SETTINGS.add(FPS = new IntegerSetting("FPS", 30));
		SETTINGS.add(DEBUG = new BooleanSetting("Debug", false));
	}

}
