package de.szut.SmartGadgetBar.Widgets.Calculator;

import java.util.Properties;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Calculator_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Taschenrechner, der die Grundrechenarten +,-,*,/ und Potenzen mit
 * Math.pow(b,e) beherrscht. "^" - Operator ist ein logisches Oder "&" -
 * Operator ist ein logisches Und
 * 
 * @author Fabian Brinkmann
 * 
 */
public class Calculator implements WidgetInterface {

	public static final String MSG = "trollmessage";
	private ScriptEngineManager sem;
	private Calculator_UI ui;
	private Properties props;
	protected final String WIDGETNAME = "Calculator";

	/**
	 * Erzeugt ein neues Objekt des Calculators
	 */
	public Calculator() {
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

	/**
	 * Berechnet aus dem erhaltenen String ein Ergebniss
	 * 
	 * @param queue
	 *            Die Rechenopperation
	 * @return Das Ergebnis
	 */
	public String calculate(String queue) {
		try {
			return sem.getEngineFactories().get(0).getScriptEngine()
					.eval(queue).toString();
		} catch (ScriptException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Die Rechnung konnte nicht durchgeführt werden du solltest deine Eingabe überprüfen",
							"Calculation Error", JOptionPane.WARNING_MESSAGE);
		}
		return null;

	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("trollmessage",
				"What are you searching for? Calculator need no options!");
		return defaultProps;
	}

	/**
	 * Nicht in Gebrauch. Tut nichts
	 */
	@Override
	public void close() {
	}
}
