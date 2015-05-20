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
	private String searchpath;
	private ArrayList<Path> files;
	public SearchThread(FileVisitor file, Search search, String searchpath){
		this.file = file;
		this.searchpath = searchpath;
		this.search = search;
		files = new ArrayList<Path>();
	}
	@Override
    public void run() {
    	try {
    		files.clear();
    		System.out.println(searchpath);
    		if (searchpath.contains("")){
    			System.out.println("got it");
    			File [] roots = File.listRoots();
    			for(int i = 0; i < roots.length; i++){
    				searchpath = File.listRoots()[i].toString().replace('\\', '/');
    				Files.walkFileTree(Paths.get(searchpath), file);
    			}
    		} else{
    			Files.walkFileTree(Paths.get(searchpath.toString().replace('\\', '/')), file);
    		}
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.out.println(isRunning);
    	try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	if(isRunning == true){
    		search.update();
    		System.out.println("bin drinne" + this.getId());
    	}
    		
    }
	
	public synchronized void setRunning(boolean isRunning){
   	 this.isRunning = isRunning;
    }
	
	public boolean getRunning(){
		return isRunning;
	}
	
	public void foundFile(Path path){
		boolean tmp = true;
		System.out.println(path);
		for (int i = 0; i < files.size();i++){	
			if (!files.get(i).toString().contains(path.toString())){
				tmp = true;
			} else{
				tmp = false;
			}
		}
		if(tmp){
			files.add(path);
		}
		
	}
	public ArrayList<Path> getFiles(){
		return files;
	}
}