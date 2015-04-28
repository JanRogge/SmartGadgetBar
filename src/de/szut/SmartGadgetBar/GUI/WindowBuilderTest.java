package de.szut.SmartGadgetBar.GUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WindowBuilderTest extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public WindowBuilderTest() {
		setLayout(null);
		setBounds(10, BackgroundPanel.GAP , 280, 100);
		
		textField = new JTextField();
		textField.setBounds(10, 35, 260, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Encrypt");
		btnNewButton.setBounds(10, 66, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Open File");
		btnNewButton_1.setBounds(181, 11, 89, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Decrypt");
		btnNewButton_2.setBounds(181, 66, 89, 23);
		add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Public Key");
		lblNewLabel.setBounds(10, 15, 89, 14);
		add(lblNewLabel);

	}
}
