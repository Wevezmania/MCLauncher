package net.tanaka.version;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.tanaka.heper.FileHelper;
import net.tanaka.main.Start;

public class Depency {
	
	private final URL URL;
	private final File FILE;
	private final long SIZE;
	
	private Thread downloadThread;
	
	public Depency(final String url, final String path, final long size) throws MalformedURLException {
		if (!path.contains("/")) {
			if (url.contains(".json")) {
				final String[] sp = url.split("/");
				this.FILE = new File(new File(Start.ASS_ROOT, "indexes"), sp[sp.length-1]);
				this.SIZE = size;
				this.URL = new URL(url);
				return;
			} else throw new MalformedURLException("NO");
		}
		this.URL = new URL(url);
		this.FILE = new File(Start.LIB_ROOT, path);
		this.SIZE = size;
	}
	
	public void download() {
		final File parent = FILE.getParentFile();
		if (!parent.exists()) FILE.getParentFile().mkdirs();
		try {
			if (!FILE.exists() || Files.size(Paths.get(this.FILE.getAbsolutePath())) != this.SIZE) {
				downloadThread = new Thread(() -> FileHelper.download(URL, FILE), String.format("DL-THREAD-%d", SIZE));
				downloadThread.start();
				System.out.println(String.format("Downloading library:%s", FILE.getAbsolutePath()));
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public Thread getDLThread() { return this.downloadThread; }
	public File getFile() { return this.FILE; }

}
