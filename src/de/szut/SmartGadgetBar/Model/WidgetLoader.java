package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Lädt aus einem übergebenen Dateipfad das Widget
 * @author Simeon
 */
public class WidgetLoader  {
	
	private File directory;
	private String className;
	private URL classUrl;
	private URLClassLoader classLoader;
	private Object widgetObjekt;
	private Class<?> widgetClass;
	private static final String packageName = "de.szut.SmartGadgetBar.PGP";
	
	public WidgetInterface[] loadWidgets(String[] widgetNames) {
		File[] files = directory.listFiles();
		ArrayList<WidgetInterface> widgets = new ArrayList<WidgetInterface>();
		
		for (String widgetName : widgetNames) {
			for (File file : files) {
				if (widgetName.equals(file.getName())) {
					WidgetInterface widget = loadWidget(file);
					if (widget != null) {
						widgets.add(widget);
					}
				}
			}
		}
		return widgets.toArray(new WidgetInterface[widgets.size()]);
	}
	
	public WidgetInterface loadWidget(String fileName){
		return loadWidget(new File(fileName));
	}
	
	
    private WidgetInterface loadWidget(File file) {
		try {
			System.out.println(file.getAbsolutePath());
			classUrl = file.getParentFile().getParentFile().toURI().toURL();
		} catch (MalformedURLException e1) {
			return null;
		}
    	classLoader = new URLClassLoader(new URL[]{classUrl}, this.getClass().getClassLoader());
    	className = file.getName();
    	int index = className.indexOf(".");
		try {
			widgetClass = classLoader.loadClass(packageName + "." + className.substring(0, index));
		} catch (ClassNotFoundException e1) {
			return null;
		}
		try {
			widgetObjekt = widgetClass.newInstance();
		} catch (Exception e) {
			return null;
		}
    	if(widgetObjekt instanceof WidgetInterface) {
    		return (WidgetInterface) widgetObjekt;
    	}
		return null;
    }

}
