package de.szut.SmartGadgetBar.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Abstracte Klasse, die alle UI's der widgets erben muessen
 *
 */
public abstract class AbstractWidgetPanel extends JPanel {

	private static final long serialVersionUID = -4757612542318751284L;
	protected WidgetInterface widget;
	private PopoutFrame pop;
	private BackgroundPanel tmp;
	private int pX, pY;

	/**
	 * Konstruktor Die UI kennt das Widget um mit ihm zu interagieren Hat
	 * bereits die Menuepunkte Options und Delete
	 * 
	 * @param parent
	 */
	public AbstractWidgetPanel(WidgetInterface parent) {
		this.widget = parent;
		MouseAdapter l = new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				pop.setLocation(pop.getLocation().x + me.getX() - pX,
						pop.getLocation().y + me.getY() - pY);
			}

			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		};
		JPopupMenu popup = new JPopupMenu();
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			widget.getPanel().optionClicked();
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
			JMenuItem widgetOnTop = new JMenuItem("Set On Top");
			if (pop == null) {
				popoutWidget.setText("Back to Bar");
				tmp = (BackgroundPanel) this.getParent();
				this.getParent().remove(this);
				System.out.println(tmp);
				pop = new PopoutFrame(this);
				popup.add(widgetOnTop);
				widgetOnTop.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(pop.isAlwaysOnTop()){
							pop.setAlwaysOnTop(false);
							widgetOnTop.setText("Set On Top");
						}else{
							pop.setAlwaysOnTop(true);
							widgetOnTop.setText("Disable On Top");
						}
						
						
					}
				});
				addMouseListener(l);
				addMouseMotionListener(l);
			} else {
				popoutWidget.setText("Popout");
				pop.dispose();
				pop.remove(this);
				tmp.add(this);
				tmp.rebuild();
				pop = null;
				removeMouseListener(l);
				removeMouseMotionListener(l);
				popup.remove(3);
			}
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

	public abstract void optionClicked();

	abstract void initializePanel();

	public abstract void pushFiles(File[] files);
}
