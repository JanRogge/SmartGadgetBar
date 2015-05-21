package de.szut.SmartGadgetBar.GUI;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;

import de.szut.SmartGadgetBar.Widgets.Search.Search;

public class Search_UI extends AbstractWidgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8036032464275587088L;
	private Search search;
	private JComboBox<Object> comboBox;
	private String tmp;

	public Search_UI(Search parent) {
		super(parent);
		search = parent;
		initializePanel();
		// TODO Auto-generated constructor stub
	}

	@Override
	void initializePanel() {
		setSize(280,40);
		setLayout(null);
		setImage("graphics/search.png");
		
		comboBox = new JComboBox<Object>();
		comboBox.setEditable(true);
		comboBox.setUI(new BasicComboBoxUI() {
		    protected JButton createArrowButton() {
		        return new JButton() {
		            /**
					 * 
					 */
					private static final long serialVersionUID = 2597150876771746912L;

					public int getWidth() {
		                return 0;
		            }
		        };
		    }
		});
		comboBox.getEditor().getEditorComponent().addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == 10){
					search.search(comboBox.getEditor().getItem().toString());
					tmp = comboBox.getEditor().getItem().toString();
				}
				if (arg0.getKeyCode() == 17){
					Desktop desktop = Desktop.getDesktop();
					try {
						File f = new File(comboBox.getEditor().getItem().toString());
						if (f.canExecute()){
							desktop.open(f);
						}
							
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});

		comboBox.setBounds(10, 10, 260, 20);
		add(comboBox);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub

	}

	public void updateComboBox() {
		comboBox.removeAllItems();
		comboBox.setModel(new DefaultComboBoxModel<Object>(search.getFiles()
				.toArray()));
		comboBox.getEditor().setItem(tmp);
		JTextField tf = (JTextField) comboBox.getEditor().getEditorComponent();
		tf.setCaretPosition(tmp.length());
		if(search.getFiles().size() != 0){
			comboBox.showPopup();
		}
		//comboBox.setMaximumRowCount(50);
		repaint();
	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addProperty(Search.start, "Startpath:");
		opw.finish();
	}
}