package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Search_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;
public class Search implements WidgetInterface{

	private Search_UI ui;
	private ArrayList<SearchThread> threads;
	private SearchThread t;
	
	public Search() throws IOException{
		ui = new Search_UI(this);
		threads = new ArrayList<SearchThread>();
		//Files.walkFileTree(Paths.get(File.listRoots()[0].toString().replace('\\', '/')), new FileVisitor(this, "Windoof"));
	}
	
	public void foundFile(Path path){
		//System.out.println(path);
		threads.get(threads.size()-1).foundFile(path);
	}
	
	public void update(){
		ui.updateComboBox();
	}
	
	public void search(String name){
//		files.clear();
		if (t == null){
			t = new SearchThread(new FileVisitor(this, name), this);
			//t.start();
		}else if(!t.isInterrupted()){
			//t.stop();
			//t.interrupt();
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this);
			//t.start();
		}else{
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this);
			//t.start();
		}
		t.start();
		threads.add(t);
		
		
	}
	
	public ArrayList<Path> getFiles(){
		return threads.get(threads.size()-1).getFiles();
	}
	
	@Override
	public AbstractWidgetPanel getPanel() {
		
		// TODO Auto-generated method stub
		return ui;
	}
	
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return "Search";
	}
	public boolean get(){
		return t.getRunning();
		
	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Properties getDefaultProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}