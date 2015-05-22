package de.szut.SmartGadgetBar.Widgets.Calculator;

import java.util.Properties;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Calculator_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Taschenrechner, der die Grundrechenarten +,-,*,/ und Potenzen mit Math.pow(b,e) beherrscht.
 * "^" - Operator ist ein logisches Oder
 *
 */
public class Calculator implements WidgetInterface{

	public static final String MSG = "trollmessage";
	private ScriptEngineManager sem;
	private Calculator_UI ui;
	private Properties props;
	protected final String WIDGETNAME = "Calculator";
	
	public Calculator(){
		sem = new ScriptEngineManager();
		ui = new Calculator_UI(this);
		loadProperties();
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
		// TODO Auto-generated method stub
		return WIDGETNAME;
	}

	public String calculate(String queue){
		try {
			return sem.getEngineFactories().get(0).getScriptEngine().eval(queue).toString();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"Die Rechnung konnte nicht durchgeführt werden du solltest deine Eingabe überprüfen","Calculation Error",JOptionPane.WARNING_MESSAGE);
		}
		return null;
		
	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("trollmessage","What are you searching for? Calculator need no options!");
		return defaultProps;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
