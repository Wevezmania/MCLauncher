package net.tanaka.heper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHelper {
	
	private static final Charset charset = Charset.forName("MS932");;
	
	public static String readString(final File file) {
		final StringBuffer readBuffer = new StringBuffer();
		try {
            if (!file.exists()) {
                System.out.print("File does not exist");
                return null;
            }
            final FileReader fileReader = new FileReader(file);
            int data;
            while ((data = fileReader.read()) != -1) {
            	final char chData = (char) data;
            	if (chData == ' ') continue;
            	readBuffer.append(chData);
            }
            fileReader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
		return readBuffer.toString();
	}
	
	public static void download(final URL url, final File file) {
		try {
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				throw new Exception("HTTP Status " + httpStatusCode);
			}
			final DataInputStream dataInStream = new DataInputStream(conn.getInputStream());
			final DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			final byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = dataInStream.read(b))) {
				dataOutStream.write(b, 0, readByte);
			}
			dataInStream.close();
			dataOutStream.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final ProtocolException e) {
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unzip(final File zip, final File unzip) {
        try (final FileInputStream fis = new FileInputStream(zip);
        		final BufferedInputStream bis = new BufferedInputStream(fis);
        		final ZipInputStream zis = new ZipInputStream(bis,charset)) {
        	ZipEntry zipentry;
        	while ((zipentry = zis.getNextEntry()) != null) {
        		try (final FileOutputStream fos = new FileOutputStream(unzip + zipentry.getName());
        				final BufferedOutputStream bos = new BufferedOutputStream(fos)) {
        			final byte[] data = new byte[1024];
        			int count = 0;
        			while ((count = zis.read(data)) != -1) {
        				bos.write(data,0,count);
        			}
        		}
        	}
        } catch (final IOException e) {
        	e.printStackTrace();
        }
	}

}
