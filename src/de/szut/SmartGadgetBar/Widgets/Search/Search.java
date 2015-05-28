package de.szut.SmartGadgetBar.Widgets.Search;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Search_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class Search implements WidgetInterface {

	private Search_UI ui;
	public static final String start = "startpath";
	private ArrayList<SearchThread> threads;
	private Properties props;
	private SearchThread t;

	/*
	 * Startet die UI, den Thread und l�dt die einstellungen
	 */
	public Search() throws IOException {
		ui = new Search_UI(this);
		threads = new ArrayList<SearchThread>();
		loadProperties();
	}

	/*
	 * Gibt dem Thread den Path der gefunden File f�r die ArrayList
	 */
	public void foundFile(Path path) {
		threads.get(threads.size() - 1).foundFile(path);
	}

	/*
	 * Updated die ComboBox der UI
	 */
	public void update() {
		ui.updateComboBox();
	}

	/**
	 * Sucht die gesuchte Datei
	 * 
	 * @param name Name der gesuchten Datei
	 */
	public void search(String name) {
		if (t == null) {
			t = new SearchThread(new FileVisitor(this, name), this,
					props.getProperty(Search.start));
		} else if (!t.isInterrupted()) {
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this,
					props.getProperty(Search.start));
		} else {
			t.setRunning(false);
			t = new SearchThread(new FileVisitor(this, name), this,
					props.getProperty(Search.start));
		}
		t.start();
		threads.add(t);

	}

	/**
	 * Gibt die ArrayList mit Pfaden zur�ck
	 * @return ArrayList aus einem thread
	 */
	public ArrayList<Path> getFiles() {
		return threads.get(threads.size() - 1).getFiles();
	}

	@Override
	public AbstractWidgetPanel getPanel() {
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		this.props = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		return props;

	}

	@Override
	public String getWidgetName() {
		return "Search";
	}

	public boolean get() {
		return t.getRunning();

	}

	@Override
	public void close() {
		try {
			threads.get(threads.size() - 1).setRunning(false);
		} catch (ArrayIndexOutOfBoundsException e) {

		}

	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("startpath", "c:/");
		return defaultProps;
	}
}