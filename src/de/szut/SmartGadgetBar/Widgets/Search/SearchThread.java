package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

public class SearchThread extends Thread{
	private boolean isRunning = true;
	private FileVisitor file;
	private Search search;
	public SearchThread(FileVisitor file, Search search){
		this.file = file;
		this.search = search;
	}
	@Override
    public void run() {
    	try {
    		Files.walkFileTree(Paths.get(File.listRoots()[2].toString().replace('\\', '/')), file);
    		//System.out.println(isRunning);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(isRunning == true)
    		search.update();
    }
	public void setRunning(boolean isRunning){
   	 this.isRunning = isRunning;
    }
	public boolean getRunning(){
		return isRunning;
	}
}
