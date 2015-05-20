package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;

import de.szut.SmartGadgetBar.Widgets.Search.Search;

public class Search_UI extends AbstractWidgetPanel{

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
		setBounds(new Rectangle(0, 0, 280, 40));
		setLayout(null);
		setBackground(Color.YELLOW);
		
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
					System.out.println(arg0.getKeyCode());
//					Desktop desktop = Desktop.getDesktop();
//					try {
//						desktop.open(new File(comboBox.getSelectedItem().toString()));
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(comboBox.getEditor().getItem().toString());
//				long time = System.currentTimeMillis();
//				
//				search.search(comboBox.getEditor().getItem().toString());
//				tmp = comboBox.getEditor().getItem().toString();
				//System.out.println(arg0.getKeyCode());
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
//		comboBox.addActionListener(e -> {
//			Desktop desktop = Desktop.getDesktop();
//			try {
//				desktop.open(new File(comboBox.getSelectedItem().toString()));
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
		comboBox.setBounds(10, 10, 260, 20);
		comboBox.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File(comboBox.getSelectedItem().toString()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(comboBox);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	 public void paintComponent(final Graphics g) {
		    g.setColor(this.getBackground());
		    g.fillRoundRect(0,0,this.getBounds().width, this.getBounds().height,10, 10);
		    };
		    
	public void updateComboBox(){
		comboBox.removeAllItems();
		comboBox.setModel(new DefaultComboBoxModel<Object>(search.getFiles().toArray()));
		comboBox.getEditor().setItem(tmp);
		JTextField tf = (JTextField) comboBox.getEditor().getEditorComponent();
		tf.setCaretPosition(tmp.length());
		comboBox.showPopup();
		comboBox.setMaximumRowCount(50);
		repaint();
	}
}
