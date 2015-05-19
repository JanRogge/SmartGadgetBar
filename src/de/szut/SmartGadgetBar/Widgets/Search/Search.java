package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.GUI.Search_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;
public class Search implements WidgetInterface{

	private Search_UI ui;
	private ArrayList<Path> files;
	private SearchThread t;
	public Search() throws IOException{
		ui = new Search_UI(this);
		//Files.walkFileTree(Paths.get(File.listRoots()[0].toString().replace('\\', '/')), new FileVisitor(this, "Windoof"));
	}
	public void foundFile(Path path){
		//System.out.println(path);
		files.add(path);
	}
	public void update(){
		ui.updateComboBox();
	}
	public void search(String name){
		if (t == null){
			t = new SearchThread(new FileVisitor(this, name), this);
			t.start();
		}else if(!t.isInterrupted()){
			t.stop();
			//t.interrupt();
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this);
			t.start();
		}else{
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this);
			t.start();
		}
		files = new ArrayList<Path>();
		//threads.add(t);
		
		
	}
	public ArrayList<Path> getFiles(){
		return files;
	}
	@Override
	public JPanel getPanel() {
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
}
