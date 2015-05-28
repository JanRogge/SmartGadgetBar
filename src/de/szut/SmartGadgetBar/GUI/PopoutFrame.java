package de.szut.SmartGadgetBar.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

/**
 * Das PopupFrame zum anzeigen von Nachrichten
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class PopoutFrame extends JFrame {

	private static final long serialVersionUID = 3257957647667454108L;
	private int pX, pY;

	/**
	 * Erzeugt ein neues Objekt des PopupFrame
	 * @param abstractWidgetPanel
	 */
	public PopoutFrame(AbstractWidgetPanel abstractWidgetPanel) {
		setContentPane(abstractWidgetPanel);
		setBounds(10, 10, 100, 100);
		setSize((int) abstractWidgetPanel.getSize().getWidth(),
				(int) abstractWidgetPanel.getSize().getHeight());
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(0, 0, (int) abstractWidgetPanel
				.getSize().getWidth(), (int) abstractWidgetPanel.getSize()
				.getHeight(), 30, 30));
		setVisible(true);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				setLocation(getParent().getParent().getParent().getLocation().x
						+ me.getX() - pX, getLocation().y + me.getY() - pY);
			}
		});
	}

}