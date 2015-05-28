package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class SearchThread extends Thread {
	private boolean isRunning = true;
	private FileVisitor file;
	private Search search;
	private String searchpath;
	private ArrayList<Path> files;

	public SearchThread(FileVisitor file, Search search, String searchpath) {
		this.file = file;
		this.searchpath = searchpath;
		this.search = search;
		files = new ArrayList<Path>();
	}

	@Override
	public void run() {
		try {
			files.clear(); //Löschen der Liste
			if (searchpath.equals("")) { //Überprüfung ob es einen Startpath gibt
				File[] roots = File.listRoots();
				for (int i = 0; i < roots.length; i++) { //For Schleife zum durchsuchen aller Festplatten
					searchpath = File.listRoots()[i].toString().replace('\\',
							'/');
					Files.walkFileTree(Paths.get(searchpath), file);
				}
			} else { //Durchsuchen nach Datei ab angegben Startpath
				Files.walkFileTree(
						Paths.get(searchpath.toString().replace('\\', '/')),
						file);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isRunning == true) {
			search.update();
		}

	}

	/**
	 * Setzt das updaten
	 * @param isRunning
	 */
	public synchronized void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Check if Running
	 * @return boolean isRunning
	 */
	public boolean getRunning() {
		return isRunning;
	}

	/**
	 * Added gefunden Datein zu einer ArrayList
	 * @param path Pfad der gefunden Datei
	 */
	public void foundFile(Path path) {
		boolean tmp = true;
		for (int i = 0; i < files.size(); i++) {
			if (!files.get(i).toString().contains(path.toString())) {
				tmp = true;
			} else {
				tmp = false;
			}
		}
		if (tmp) {
			files.add(path);
		}

	}

	/**
	 * ArrayList mit Pfaden
	 * @return ArrayList
	 */
	public ArrayList<Path> getFiles() {
		return files;
	}
}