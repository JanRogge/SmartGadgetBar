package de.szut.SmartGadgetBar.GUI;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.FileDropper;
import de.szut.SmartGadgetBar.Widgets.PGP.PGP;

import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Das Panel fuer das Widget PGP
 *
 */
public class PGP_UI extends AbstractWidgetPanel {
	
	private static final long serialVersionUID = -3787640035406114125L;
	private JFileChooser fileChooser;
	private JFileChooser keyChooser;
	private PGP widget;
	private File keyfile;
	private boolean key = false;

	public PGP_UI(PGP pgp) {
		super(pgp);
		this.widget = pgp;
		setSize(280,80);
		initializePanel();
		
	}

	@Override
	void initializePanel() {
		setLayout(null);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(10, 45, 89, 23);
		btnEncrypt.addActionListener(e -> {
			try{
				if(fileChooser.getSelectedFile().exists() && keyChooser.getSelectedFile().exists()){
					widget.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(),keyChooser.getSelectedFile().getAbsolutePath());
				}
			} catch(NullPointerException k){
				JOptionPane.showMessageDialog(null, "No File to Encrypt or No Key was selected");
			}
			
			
		});
		add(btnEncrypt);

		JButton btnFile = new JButton("Open File");
		btnFile.setBounds(10, 11, 89, 23);
		btnFile.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Please choose a file");
			fileChooser.showOpenDialog(null);
			
		});
		add(btnFile);

		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(181, 45, 89, 23);
		btnDecrypt.addActionListener(e -> {
			try{
				if(fileChooser.getSelectedFile().exists() && fileChooser.getSelectedFile().getAbsolutePath().endsWith(".pgp")){
					widget.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(),"keys/private.skr");
				}
			}catch(NullPointerException k){
				JOptionPane.showMessageDialog(null, "No File to Encrypt");
			}
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
		
		new DropTarget(btnPublicKey, new FileDropper(this));
		new DropTarget(btnFile, new FileDropper(this));
		
		setVisible(true);
	}

	@Override
	public void pushFiles(File[] files) {
		boolean decrypt = true; 
		for (File file : files) {
			if (!file.getAbsolutePath().endsWith(".pgp")) {
				decrypt = false;
			}
			if(file.getAbsolutePath().endsWith(".asc")){

				key = true;
				keyfile = file;
			}
		}
		if (decrypt) {
			for (File file : files) {
				widget.decryptFile(file.getAbsolutePath(),"keys/private.skr");
			}
		}
		else {	
				for (File file : files) {
					if(key && !file.getAbsolutePath().endsWith(".asc")){
						widget.encryptFile(file.getAbsolutePath(),keyfile.getAbsolutePath());
					}
				}
			
		}
	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(PGP.EMAIL, "E-Mail:");
		opw.addProperty(PGP.PASS, "Password(Keys are useless when changed)");
		opw.addProperty(PGP.KEYSIZE, "Size of Keys");
		opw.addButton("Generate new Keys", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widget.generateKeyPair();
			}
			
		});
		opw.finish();
	}
}
