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
import javax.swing.SwingConstants;

import de.szut.SmartGadgetBar.Model.PropertyLoader;

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
		setBounds(100, 100, 450, 150);
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
		apply.setBounds(345, 87, 89, 23);
		contentPane.add(apply);
		
		JCheckBox boxalwaysontop = new JCheckBox("Always on Top");
		if(mainf.isAlwaysOnTop()){
			boxalwaysontop.setSelected(true);
		}
		boxalwaysontop.setBounds(5, 32, 128, 23);
		contentPane.add(boxalwaysontop);
		
		JLabel lblAvailableWidgets = new JLabel("Available Widgets:");
		lblAvailableWidgets.setBounds(5, 61, 154, 14);
		contentPane.add(lblAvailableWidgets);
		
		JLabel lblWidgets = new JLabel();
		lblWidgets.setVerticalAlignment(SwingConstants.TOP);
		lblWidgets.setHorizontalAlignment(SwingConstants.LEFT);
		lblWidgets.setBounds(5, 75, 330, 35);
		String [] availabelWidgets = new PropertyLoader().loadProperties("config/config.ini", null).getProperty("availableWidgets").split(",");
		for (int i = 0; i < availabelWidgets.length;i++){
			if(i==0){
				lblWidgets.setText(availabelWidgets[i]);
			} else{
				lblWidgets.setText(lblWidgets.getText() + ", " + availabelWidgets[i]);
			}
		}
		contentPane.add(lblWidgets);
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
