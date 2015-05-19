package de.szut.SmartGadgetBar.GUI;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.Widgets.Notes.Notes;
import de.szut.SmartGadgetBar.Widgets.PGP.PGP;

public class Notes_UI extends AbstractWidgetPanel {
	
	public Notes_UI(WidgetInterface parent) {
		super(parent);
		setBounds(new Rectangle(0, 0, 280, 80));
		initializePanel();
	}
	
	@Override
	void initializePanel() {
		setLayout(null);
		JTextArea textfield = new JTextArea();
		textfield.setBounds(0, 7, 280, 66);
		add(textfield);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem saveDatabase = new JMenuItem("In Datenbank Speichern");
		JMenuItem saveTxt= new JMenuItem("Speichern als .txt");
		
		setVisible(true);
	}
	
	@Override
	public void pushFiles(File[] files) {
	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(Notes.DBPath, "Databasepath:");
		opw.addProperty(Notes.TXTPATH, "Dafault .txt file path");
		opw.finish();
	}
}
