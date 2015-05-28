package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.*;
import java.util.zip.*;

/**
 * Klasse zum Komprimieren von Dateien als zip Sollte ueber die Klasse ZIP
 * benutzt werden
 * 
 * @author Fabian Brinkmann
 * 
 */
public class Comprimator {

	/**
	 * Geht durch alle Files die ausgewählt wurden
	 * @param fileNames zu komprimierende File
	 * @param outName Enddateiname
	 */
	public void comprimate(String[] fileNames, String outName) {
		ZipOutputStream out = null;
		try {
			File f = new File(outName);
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out = new ZipOutputStream(new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File[] files = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		String src = files[0].getParentFile().getAbsolutePath();
		comprimate(files, outName, out, src);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checkt ob die File eine File ist oder ein Directory
	 * @param files
	 * @param outName
	 * @param zout
	 * @param src
	 */
	private void comprimate(File[] files, String outName, ZipOutputStream zout,
			String src) {
		try {
			for (int i = 0; i < files.length; i++) {
				File child = files[i];
				if (child.isFile()) {
					addToZip(child, zout, src);
				} else {
					addDirToZip(child, zout, src);
					comprimate(child.listFiles(), outName, zout, src);
				}
			}

		} catch (Exception e) {

		}

	}

	/**
	 * Fügt eine Datei dem Archiv hinzu
	 * @param f File
	 * @param out Name der Zip
	 * @param src Grundname
	 * @throws IOException
	 */
	private void addToZip(File f, ZipOutputStream out, String src)
			throws IOException {
		out.putNextEntry(new ZipEntry(f.getAbsolutePath().substring(
				src.length() + 1, f.getAbsolutePath().length())));
		FileInputStream in = new FileInputStream(f);

		int len;
		byte[] buffer = new byte[2048];
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}

		in.close();
		out.closeEntry();

	}

	/**
	 * Fügt einen Ordner dem Archiv hinzu
	 * @param dir Directory
	 * @param out Name der ZIP
	 * @param src Grundname
	 * @throws IOException
	 */
	private void addDirToZip(File dir, ZipOutputStream out, String src)
			throws IOException {

		out.putNextEntry(new ZipEntry(dir.getAbsolutePath().substring(
				src.length() + 1, dir.getAbsolutePath().length())
				+ "/"));
	}

}