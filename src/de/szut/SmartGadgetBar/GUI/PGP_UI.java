package de.szut.SmartGadgetBar.GUI;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.FileDropper;
import de.szut.SmartGadgetBar.Widgets.PGP.PGP;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;

/**
 * Das Panel fuer das Widget PGP
 *
 */
public class PGP_UI extends AbstractWidgetPanel {
	
	private static final long serialVersionUID = -3787640035406114125L;
	private JFileChooser fileChooser;
	private JFileChooser keyChooser;
	private PGP widget;

	public PGP_UI(PGP pgp) {
		super(pgp);
		this.widget = pgp;
		setBounds(new Rectangle(0, 0, 280, 40));
		initializePanel();
		
	}

	@Override
	void initializePanel() {
		setLayout(null);
		setBackground(Color.BLUE);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(10, 45, 89, 23);
		btnEncrypt.addActionListener(e -> {
			widget.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
					keyChooser.getSelectedFile().getAbsolutePath());
		});
		add(btnEncrypt);

		JButton btnFile = new JButton("Open File");
		btnFile.setBounds(10, 11, 89, 23);
		btnFile.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Please choose a file");
			// fileChooser.setCurrentDirectory(new File("db"));
			int option = fileChooser.showOpenDialog(null);
			
		});
		add(btnFile);

		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(181, 45, 89, 23);
		btnDecrypt.addActionListener(e -> {
			widget.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
					"keys/private.skr");
		});
		add(btnDecrypt);
		
		JButton btnPublicKey = new JButton("Public Key");
		btnPublicKey.setBounds(181, 11, 89, 23);
		btnPublicKey.addActionListener(e -> {
			keyChooser = new JFileChooser();
			keyChooser.setDialogTitle("Please choose a Key file");
			keyChooser.setCurrentDirectory(new File("keys"));
			keyChooser.setFileFilter(new FileNameExtensionFilter("Key File",
					"skr", "asc"));
			int option = keyChooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
			}
		});
		add(btnPublicKey);
		
		new DropTarget(this, new FileDropper(this));
		
		setVisible(true);
	}

	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub
	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(PGP.EMAIL, "E-Mail:");
		opw.addProperty(PGP.PASS, "Password(Keys are useless when changed)");
		opw.addProperty(PGP.KEYSIZE, "Size of Keys");
		opw.finish();
	}
}
