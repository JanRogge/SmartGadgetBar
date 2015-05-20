package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Properties;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.PGP_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * 
 *Klasse, die die nötigen Funktionalität zum
 *Verschlüsseln, Entschlüsseln und Key Generieren 
 *bereitstellt
 */



public class PGP implements WidgetInterface {
	
	public static final String PASS = "passwort";
	public static final String EMAIL = "email";
	public static final String KEYSIZE = "keysize";
	
	private PGP_UI ui;
	private Properties props;
	public final String widgetName = "PGP";
	
	public PGP() {
		Security.addProvider(new BouncyCastleProvider());
		generateKeyPair();
		ui = new PGP_UI(this);
		loadProperties();
	}
	
	/**
	 * Generiert ein Schlüsselpaar und speichert dieses in zwei Dateien
	 */
	public void generateKeyPair() {
		if (!new File("keys/private.skr").exists()
				|| !new File("keys/public.asc").exists()) {
			KeyGenerator kgen = new KeyGenerator();
			kgen.setKeySize(props.getProperty(PGP.KEYSIZE));
			kgen.setPrivateKeyFile("keys/private.skr");
			kgen.setPublicKeyFile("keys/public.asc");
			kgen.setPass(props.getProperty(PGP.PASS).toCharArray());
			kgen.setEmail(props.getProperty(PGP.EMAIL));
			try {
				kgen.generateKeyPair();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Verschluesselt eine gegebene Datei mit einem gegebenen Schluessel
	 * @param fileName
	 * @param keyFile
	 */
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
	
	/**
	 * @param encryptFileName
	 * @param prikeyFile
	 */
	public void decryptFile(String encryptFileName, String prikeyFile) {
		try {
			FileInputStream inp = new FileInputStream(new File(encryptFileName));
			PGPDecrypter.decrypt(inp,
					new FileInputStream(new File(prikeyFile)),
					encryptFileName.substring(0, encryptFileName.length() - 4),
					props.getProperty(PGP.PASS).toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public AbstractWidgetPanel getPanel() {
		return ui;
	}
	
	@Override
	public void setProperties(Properties properties) {
		this.props = properties;
		saveProperties();
	}
	
	@Override
	public Properties getProperties() {
		return props;
	}
	
	@Override
	public String getWidgetName() {
		return "PGP";
	}
	
	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("email", "max.musterman@muster.de");
		defaultProps.setProperty("keysize", "2048");
		defaultProps.setProperty("passwort", "passwort1234");
		return defaultProps;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}