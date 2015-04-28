package de.szut.SmartGadgetBar.GUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class OptionPanelMain extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631129137304118779L;
	private JPanel contentPane;
	private boolean top;

	/**
	 * Create the frame.
	 */
	public OptionPanelMain(MainFrame mainf) {
		top = mainf.isAlwaysOnTop();
		setResizable(false);
		setTitle("Main Options");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWindowSize = new JLabel("Window Size");
		lblWindowSize.setBounds(5, 11, 92, 14);
		contentPane.add(lblWindowSize);
		
		JSpinner spinner = new JSpinner();		
		spinner.setModel(new SpinnerNumberModel(new Integer((int) mainf.getSize().getHeight()), new Integer(0), null, new Integer(1)));
		spinner.setBounds(294, 8, 60, 20);
		contentPane.add(spinner);
		
		JSpinner spinner_1 = new JSpinner(new SpinnerNumberModel(new Integer((int) mainf.getSize().getWidth()), new Integer(0), null, new Integer(1)));
		spinner_1.setBounds(364, 8, 60, 20);
		contentPane.add(spinner_1);
		
		JButton apply = new JButton("Apply");
		apply.addActionListener(e->{
			mainf.setSize((int) spinner_1.getValue(),(int) spinner.getValue());
		});
		apply.setBounds(335, 227, 89, 23);
		contentPane.add(apply);
		
		JCheckBox boxalwaysontop = new JCheckBox("Always on Top");
		if(mainf.isAlwaysOnTop()){
			boxalwaysontop.setSelected(true);
		}
		boxalwaysontop.setBounds(5, 32, 97, 23);
		contentPane.add(boxalwaysontop);
		boxalwaysontop.addActionListener(e->{
			if (boxalwaysontop.isSelected()) {
				top = true;
			} else {
				top = false;
			}
		});
		mainf.setAlwaysOnTop(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainf.setAlwaysOnTop(top);
			}
		});
		
	}
}
