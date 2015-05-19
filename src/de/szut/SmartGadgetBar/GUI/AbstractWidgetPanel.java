package de.szut.SmartGadgetBar.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

public abstract class AbstractWidgetPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4757612542318751284L;
	protected WidgetInterface widget;
	private PopoutFrame pop;
	private BackgroundPanel tmp;
	private JFrame tmp2;
	private int pX, pY;
	
	public AbstractWidgetPanel(WidgetInterface parent){
		this.widget = parent;
		MouseAdapter l= new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				pop.setLocation(pop.getLocation().x + me.getX() - pX, pop.getLocation().y+ me.getY() - pY);
			}
			public void mousePressed(MouseEvent me) {
						pX = me.getX();
						pY = me.getY();
					}
		};
		JPopupMenu popup = new JPopupMenu();
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			new OptionPanelWidget(widget);
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e -> {
			this.getParent().remove(this);
			widget.close();
			if (pop != null)
				pop.dispose();
		});
		JMenuItem popoutWidget = new JMenuItem("Popout");
		popoutWidget.addActionListener(e -> {
			if(pop == null){
				popoutWidget.setText("Back to Bar");
				tmp = (BackgroundPanel) this.getParent();
				this.getParent().remove(this);
				System.out.println(tmp);
				pop = new PopoutFrame(this);
				addMouseListener(l);
				addMouseMotionListener(l);
			} else{
				popoutWidget.setText("Popout");
				pop.dispose();
				pop.remove(this);
				tmp.add(this);
				tmp.rebuild();
				pop = null;
				removeMouseListener(l);
				removeMouseMotionListener(l);
			}
			System.out.println("Popout");
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		popup.add(popoutWidget);
		setComponentPopupMenu(popup);
		setVisible(true);
	}
	
	public WidgetInterface getWidget() {
		return widget;
	}
	
	public void optionClicked(){
		new OptionPanelWidget(widget);
	}
	
	abstract void initializePanel();
	
	public abstract void pushFiles(File[] files);
}
