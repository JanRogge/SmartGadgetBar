package de.szut.SmartGadgetBar.Model;

import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Example {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("FileDrop");
		final JTextArea text = new JTextArea();
		frame.getContentPane().add(new JScrollPane(text), BorderLayout.CENTER);
		new DropTarget(text, new FileDropper());
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
