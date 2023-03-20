package net.tanaka.version;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.tanaka.heper.FileHelper;
import net.tanaka.heper.LWJGLHelper;
import net.tanaka.heper.OSHelper.OSType;
import net.tanaka.main.Start;

public class Client {
	
	private static final String ASSET_URL = "https://github.com/InventivetalentDev/minecraft-assets/zipball/refs/heads/";
	
	private String NAME;
	private File JSON, JAR;
	private final File ROOT;
	
	private final List<Depency> DEPENCY = new ArrayList<>();
	
	public Client(final File folder) throws Exception {
		this.ROOT = folder;
		if (!folder.isFile()) {
			for (final File f : folder.listFiles()) {
				if (f.isFile()) {
					final String fileName = f.getName();
					final int length = fileName.length();
					if (length > 5) {
						final String endStr = fileName.substring(length - 5).toUpperCase();
						if (endStr.contains("JSON")) {
							this.JSON = f;
						} else if (endStr.contains("JAR")) {
							this.JAR = f;
							this.NAME = fileName.substring(0, length - 4);
						}
					}
				}
			}
			if (JSON == null) throw new Exception("The folder doesn't contain json");
			if (JAR == null) throw new Exception("The folder dosen't contain jar");
			if (NAME == null) throw new Exception("The name is null(Unexcepted)");
		} else {
			throw new Exception("The folder is not folder");
		}
	}
	
	public String getName() { return this.NAME; }
	
	public void launch() {
		LWJGLHelper.update();
		jsonRead:{
			final String jsonData = FileHelper.readString(JSON);
			final String[] sp = jsonData.split("\"downloads\":");
			for (final String s : sp) {
				final String foundURL = solve2(s);
				if (osCheck(foundURL)) {
					try {
						this.DEPENCY.add(new Depency(foundURL, solve(s), solve3(s)));
					} catch (final MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		libDownloade:{
			this.DEPENCY.forEach(d -> d.download());
			this.DEPENCY.forEach(d -> {
				final Thread thread = d.getDLThread();
				if (thread != null) {
					try {
						thread.join();
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		/*assetsDownload:{
			final File ASSETS = new File(Start.ASS_ROOT, this.NAME);
			boolean shouldDL = false;
			if (!ASSETS.exists()) {
				shouldDL = true;
				ASSETS.mkdirs();
			}
			final File[] ASSETS_CONT = ASSETS.listFiles();
			if (ASSETS_CONT == null) shouldDL = true;
			for (final File f : ASSETS_CONT) {
				if (f.isFile()) {
					
				}
			}
			if (shouldDL) {
				final String assetsDL = String.format(ASSET_URL, this.NAME);
				System.out.println(String.format("Downloading assets from %s", assetsDL));
				final File temp = new File(ASSETS, "temp.zip");
				try {
					FileHelper.download(new URL(assetsDL), temp);
				} catch (final MalformedURLException e) {
					e.printStackTrace();
				}
				
			}
		}*/
		final char pre = Start.OS == OSType.LINUX ? ':' : ';';
		final StringBuffer cmd = new StringBuffer(String.format("java -Djava.library.path=%s -cp %s", LWJGLHelper.NATIVE_ROOT.getAbsolutePath(), JAR.getAbsolutePath()));
		cmd.append(pre);
		for (final Depency d : this.DEPENCY) {
			final String str = d.getFile().getAbsolutePath();
			cmd.append(str);
			cmd.append(pre);
		}
		cmd.append(String.format(" net.minecraft.client.main.Main %s %s %s %d %s %s %s %s %s %s",
				"--version", "mcp", "--accessToken", 0, String.format("assets/%s/", this.NAME), "assets", "--assetIndex", "1.8", "--userProperties", "{}"));
		final Runtime runtime = Runtime.getRuntime();
		Process pro = null;
		try {
			System.out.println(cmd);
			pro = runtime.exec(cmd.toString());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		if (pro != null) {
			try {
				pro.waitFor();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String solve(final String next) {
		final int start = next.indexOf("\"path\":\"") + 8;
		int end = -1;
		for (int i = start + 1, l = next.length(); i < l; i++) {
			if (next.charAt(i) == '"') {
				end = i;
				break;
			}
		}
		return next.substring(start, end);
	}
	
	private static String solve2(final String next) {
		final int start = next.indexOf("\"url\":\"") + 7;
		int end = -1;
		for (int i = start + 1, l = next.length(); i < l; i++) {
			if (next.charAt(i) == '"') {
				end = i;
				break;
			}
		}
		return next.substring(start, end);
	}
	
	private static long solve3(final String next) {
		final int start = next.indexOf("\"size\":") + 7;
		int end = -1;
		for (int i = start + 1, l = next.length(); i < l; i++) {
			if (next.charAt(i) == ',') {
				end = i;
				break;
			}
		}
		return Long.valueOf(next.substring(start, end));
	}
	
	private static boolean osCheck(final String str) {
		final String upper = str.toUpperCase();
		final boolean win  = upper.contains("WINDOWS"),
				mac = upper.contains("OSX"),
				linux = upper.contains("LINUX");
		if (!win && !mac && !linux) return true;
		switch (Start.OS) {
		case WINDOWS: return win;
		case LINUX: return linux;
		case MACOS: return mac;
		}
		return false;
	}

}
