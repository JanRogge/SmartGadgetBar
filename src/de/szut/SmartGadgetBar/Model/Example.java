package de.szut.SmartGadgetBar.Model;

import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Example {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("FileDrop");
		final JTextArea text = new JTextArea();
		frame.getContentPane().add(new JScrollPane(text),
				java.awt.BorderLayout.CENTER);
		
		//Add this line to your WidgetPanel to make you Component Droppable
		new DropTarget(text, new FileDropper(this));
		
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
