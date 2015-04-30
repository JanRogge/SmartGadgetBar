package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class Comprimator {
	static final int BUFFER = 2048;

	public static void comprimate(String[] files, String outName) {

		List<File> fileList = new ArrayList<File>();

		for (String s : files) {

			getAllFiles(new File(s), fileList);
		}

		writeZipFile(new File(outName), fileList);

		System.out.println("---Done");
	}

	private static void getAllFiles(File dir, List<File> fileList) {
		try {
			if (dir.isDirectory()) {

				File[] files = dir.listFiles();
				for (File file : files) {
					fileList.add(file);
					if (file.isDirectory()) {
						System.out.println("directory:"
								+ file.getCanonicalPath());
						getAllFiles(file, fileList);
					} else {
						System.out.println("     file:"
								+ file.getCanonicalPath());
					}
				}
			}
			else{
				fileList.add(dir);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeZipFile(File directoryToZip, List<File> fileList) {

		try {
			FileOutputStream fos = new FileOutputStream(
					directoryToZip.getName() + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addToZip(File directoryToZip, File file,
			ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		System.out.println(directoryToZip.getCanonicalPath());
		System.out.println(file.getCanonicalPath());
		String zipFilePath = file.getCanonicalPath().substring(
				file.getCanonicalPath().length()-directoryToZip.getCanonicalPath().length()-1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(File.pathSeparator+zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

}