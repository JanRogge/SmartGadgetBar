package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Klasse zum dekomprimieren von *.zip Dateien Sollte ueber die Klasse ZIP
 * benutzt werden
 * 
 * @author Fabian Brinkmann
 * 
 */
public class Decomprimator {
	final static int BUFFER = 2048;

	public static void decomprimate(String zipFile) {
		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(zipFile);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				File f = new File(entry.getName());
				if (entry.isDirectory()) {
					f.mkdirs();
				} else {
					f.createNewFile();
				}

				int count;
				byte data[] = new byte[BUFFER];
				if (f.isFile()) {
					FileOutputStream fos = new FileOutputStream(entry.getName());
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();

				}
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
