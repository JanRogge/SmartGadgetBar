package de.szut.SmartGadgetBar.GUI;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8252911925423161561L;
	private PropLoader props;

	/**
	 * Create the frame.
	 */
	public MainFrame(PropLoader props) {
		this.props = props;
		setAlwaysOnTop(props.getAOT());
		setUndecorated(true);
		setType(javax.swing.JFrame.Type.UTILITY);
		setContentPane(new BackgroundPanel(this));
		//setBounds(getContentPane().getBounds());
		setBounds(props.getWindowDimension());
	}
	public void close(){
		setVisible(false);
		dispose();
		props.setWindowDimension(getBounds());
		props.setAOT(String.valueOf(isAlwaysOnTop()));
	}

}
