package net.tanaka.heper;

import java.io.File;

import net.tanaka.main.Start;

public class LWJGLHelper {
	
	public static final File NATIVE_ROOT = new File(Start.LWJ_ROOT, "natives");
	
	public static void update() {
		if (!NATIVE_ROOT.exists()) NATIVE_ROOT.mkdir();
		
	}

}
