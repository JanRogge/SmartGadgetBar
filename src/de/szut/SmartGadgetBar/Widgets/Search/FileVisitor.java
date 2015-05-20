package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitor extends SimpleFileVisitor<Path>{
	private Search search;
	private String name;
	public FileVisitor(Search search, String name){
		this.search = search;
		this.name = name;
	}
	@Override
	public FileVisitResult visitFileFailed(Path file,IOException exc) {
		if (search.get() == true)
			return FileVisitResult.CONTINUE;
		else
			return FileVisitResult.TERMINATE;
	}

	@Override
	public FileVisitResult visitFile(Path path,BasicFileAttributes attribs) {
		if(path.getFileName().toString().contains(name)){
			search.foundFile(path);
		}
		if (search.get() == true)
			return FileVisitResult.CONTINUE;
		else
			return FileVisitResult.TERMINATE;
	}
}
