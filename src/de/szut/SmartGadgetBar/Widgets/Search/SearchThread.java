package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SearchThread extends Thread{
	private boolean isRunning = true;
	private FileVisitor file;
	private Search search;
	private ArrayList<Path> files;
	public SearchThread(FileVisitor file, Search search){
		this.file = file;
		this.search = search;
		files = new ArrayList<Path>();
	}
	@Override
    public void run() {
    	try {
    		long time = System.currentTimeMillis();
    		System.out.println(File.listRoots()[0].toString());
    		Files.walkFileTree(Paths.get(File.listRoots()[0].toString()), file);
    		System.out.println(System.currentTimeMillis() - time);
    		//System.out.println(isRunning);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(isRunning);
    	if(isRunning == true){
    		search.update();
    		System.out.println("bin drinne");
    	}
    		
    }
	public void setRunning(boolean isRunning){
   	 this.isRunning = isRunning;
    }
	public boolean getRunning(){
		return isRunning;
	}
	public void foundFile(Path path){
		//System.out.println(path);
		files.add(path);
	}
	public ArrayList<Path> getFiles(){
		return files;
	}
}
