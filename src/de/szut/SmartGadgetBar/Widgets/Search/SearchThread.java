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
    		files.clear();
    		//System.out.println(File.listRoots()[0].toString().replace('\\', '/'));
    		Files.walkFileTree(Paths.get(File.listRoots()[0].toString().replace('\\', '/')), file);
    		//System.out.println(System.currentTimeMillis() - time);
    		//System.out.println(isRunning);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(isRunning);
    	try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
		//System.out.println(path);
		//System.out.println(files.size());
		System.out.println(path);
		for (int i = 0; i < files.size();i++){	
			//System.out.println(files.get(i));
			if (!files.get(i).toString().contains(path.toString())){
				tmp = true;
			} else{
				tmp = false;
			}
		}
		//System.out.println(tmp);
		//
		if(tmp){
			files.add(path);
		}
		
	}
	public ArrayList<Path> getFiles(){
		return files;
	}
}
