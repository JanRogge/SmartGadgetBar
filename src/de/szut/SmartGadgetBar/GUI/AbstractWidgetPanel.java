package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Abstracte Klasse, die alle UI's der widgets erben muessen
 * 
 * @author Fabian Brinkmann, Simeon Kublenz
 *
 */
public abstract class AbstractWidgetPanel extends JPanel {

	private static final long serialVersionUID = -4757612542318751284L;
	protected WidgetInterface widget;
	private PopoutFrame pop;
	private BackgroundPanel tmp;
	private int pX, pY;
	private String manualText;
	private BufferedImage image;
	private Color c;

	/**
	 * Konstruktor Die UI kennt das Widget um mit ihm zu interagieren Hat
	 * bereits die Menuepunkte Options und Delete
	 * 
	 * @param parent
	 *            Das Widget zu dem dieses Panel gehört
	 */
	public AbstractWidgetPanel(WidgetInterface parent) {
		this.widget = parent;
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				pop.setLocation(pop.getLocation().x + me.getX() - pX,
						pop.getLocation().y + me.getY() - pY);
			}

			@Override
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
				pop = new PopoutFrame(this);
				popup.add(widgetOnTop);
				widgetOnTop.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (pop.isAlwaysOnTop()) {
							pop.setAlwaysOnTop(false);
							widgetOnTop.setText("Set On Top");
						} else {
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
				popup.remove(popup.getComponentCount() - 1);
			}
		});
		JMenuItem manualItem = new JMenuItem("Usermanual");
		manualItem.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, manualText);
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		popup.add(popoutWidget);
		popup.add(manualItem);
		setComponentPopupMenu(popup);
		setVisible(true);
		setOpaque(false);
		setBackground(Color.cyan);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;

		RoundRectangle2D.Float rr = new RoundRectangle2D.Float(0, 0,
				(getWidth()), (getHeight()), 10, 10);

		Shape clipShape = graphics.getClip();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (image == null) {
			graphics.setColor(c);
			graphics.fill(rr);
		} else {
			RoundRectangle2D.Float rr2 = new RoundRectangle2D.Float(0, 0,
					(getWidth()), (getHeight()), 10, 10);
			graphics.setClip(rr2);
			graphics.drawImage(image, 0, 0, null);
			graphics.setClip(clipShape);
		}
		new File("").getAbsolutePath();
	};

	/**
	 * Gibt das Widget zu dem dieses Panel gehört zurück
	 * 
	 * @return Das Widget zu dem dieses Panel gehört
	 */
	public WidgetInterface getWidget() {
		return widget;
	}

	/**
	 * Setzt die Hintergrundfarbe dieses Panels
	 * 
	 * @param c
	 *            Die Hintergrundfarbe
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * Setzt den Pfad des Hintergrundbildes für dieses Panels
	 * 
	 * @param imgpath
	 *            Der Hintergrundbildpfad
	 */
	public void setImage(String imgpath) {
		try {
			image = ImageIO.read(new File(imgpath));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Hintergrund nicht gefunden",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Öffnet die Optionen
	 */
	public abstract void optionClicked();

	/**
	 * Initialisiert das Panel
	 */
	abstract void initializePanel();

	/**
	 * Bearbeitet Dateien für das Widget
	 * @param files
	 */
	public abstract void pushFiles(File[] files);
}
