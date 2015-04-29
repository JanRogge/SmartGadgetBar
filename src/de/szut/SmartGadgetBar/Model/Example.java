package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A simple example showing how to use {@link FileDrop}
 * 
 * @author Robert Harder, rob@iharder.net
 */
public class Example {

	/** Runs a sample program that shows dropped files */
	public static void main(String[] args) {
		JFrame frame = new JFrame("FileDrop");
		final JTextArea text = new JTextArea();
		frame.getContentPane().add(new JScrollPane(text),
				java.awt.BorderLayout.CENTER);

		new FileDrop(System.out, text, /* dragBorder, */
				new FileDrop.Listener() {
					public void filesDropped(File[] files) {
						for (int i = 0; i < files.length; i++) {
							try {
								text.append(files[i].getCanonicalPath() + "\n");
							} // end try
							catch (IOException e) {
								e.printStackTrace();
							}
						} // end for: through each dropped file
					} // end filesDropped
				}); // end FileDrop.Listener

		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // end main

}
