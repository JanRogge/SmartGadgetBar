package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Properties;

import javax.swing.JPanel;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

import de.szut.SmartGadgetBar.GUI.PGP_UI;
import de.szut.SmartGadgetBar.Model.PropertyLoader;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * @author Fabian
 *Klasse, die die nötigen Funktionalität zum
 *Verschlüsseln, Entschlüsseln und Key Generieren 
 *bereitstellt
 */

/**
 * @author Fabian
 *
 */
public class PGP implements WidgetInterface {

	private PGP_UI ui;
	private Properties props;

	public PGP() {
		Security.addProvider(new BouncyCastleProvider());
		generateKeyPair();
		ui = new PGP_UI(this);
		// props = new PropertyLoader().loadProperties("PGP.ini");
	}

	/**
	 * Generiert ein Schlüsselpaar und speichert dieses in zwei Dateien
	 */
	public void generateKeyPair() {
		if (!new File("keys/private.skr").exists()
				|| !new File("keys/public.asc").exists()) {
			KeyGenerator kgen = new KeyGenerator();
			kgen.setPrivateKeyFile("keys/private.skr");
			kgen.setPublicKeyFile("keys/public.asc");
			kgen.setPass("DiesIstEinePassPhrase".toCharArray());
			kgen.setEmail("example@mail.com");
			try {
				kgen.generateKeyPair();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void encryptFile(String fileName, String keyFile) {
		encryptFile(new File(fileName), keyFile);
	}

	public void encryptFile(File f, String keyFile) {
		try {
			PGPEncrypter enc = new PGPEncrypter();
			enc.setPublicKeyFile(keyFile);
			enc.encrypt(f.getAbsolutePath());
		} catch (NoSuchProviderException | IOException | PGPException e) {
			//
			e.printStackTrace();
		}
	}

	public void decryptFile(String encryptFileName, String prikeyFile) {
		try {
			FileInputStream inp = new FileInputStream(new File(encryptFileName));
			PGPDecrypter.decrypt(inp,
					new FileInputStream(new File(prikeyFile)),
					encryptFileName.substring(0, encryptFileName.length() - 4),
					"DiesIstEinePassPhrase".toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return "PGP";
	}

}