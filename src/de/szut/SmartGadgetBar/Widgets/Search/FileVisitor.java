package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * CustomFileVisitor
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class FileVisitor extends SimpleFileVisitor<Path> {
	private Search search;
	private String name;

	public FileVisitor(Search search, String name) {
		this.search = search;
		this.name = name;
	}

	/*
	 * Dateisuche geht bei einem nicht öffenbaren Baum oder Datei einfach zur nächsten Datei
	 * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
	 */
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		if (search.get() == true)
			return FileVisitResult.CONTINUE;
		else
			return FileVisitResult.TERMINATE;
	}

	/*
	 * wenn es sich bei der besuchen File um eine File mit gesuchtem Inhalt handelt wird dieser der ArrayList hinzugefügt
	 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
	 */
	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes attribs) {
		if (path.getFileName().toString().contains(name)) {
			search.foundFile(path);
		}
		if (search.get() == true)
			return FileVisitResult.CONTINUE;
		else
			return FileVisitResult.TERMINATE;
	}
}