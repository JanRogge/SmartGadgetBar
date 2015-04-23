package de.szut.SmartGadgetBar.PGP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Iterator;

import org.bouncycastle.bcpg.BCPGKey;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyPacket;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jcajce.provider.asymmetric.dh.KeyPairGeneratorSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.util.encoders.Base64;

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
public class PGP {

	private PGPPrivateKey prikey;
	private PGPPublicKey pubkey;
	private File toEncrypt;
	private String prikeyfile;
	private String pubkeyfile;
	private String encryptionkeyfile;
	
	public PGP(){
		
		Security.addProvider(new BouncyCastleProvider());
	
	}
	

	public String getPrikeyfile() {
		return prikeyfile;
	}
	
	
	/**
	 * @param prikeyfile
	 * Setzt die Datei für den privaten Schluessel
	 */
	public void setPrikeyfile(String prikeyfile) {
		this.prikeyfile = prikeyfile;
	}
	
	
	public String getPubkeyfile() {
		return pubkeyfile;
	}
	
	
	/**
	 * Setzt die Datei für den eigenen Oeffentlichen Schluessel,
	 * mit dem Andere Dateien verschluesseln koennen
	 * @param pubkeyfile
	 */
	public void setPubkeyfile(String pubkeyfile) {
		this.pubkeyfile = pubkeyfile;
	}
	
	/**
	 * Setzt die Datei mit dem oeffentlichen Schluessel,
	 * der fuer die Verschluesselung benutzt wird
	 * @param enkf
	 */
	public void setEncryptionKeyFile(String enkf){
		this.encryptionkeyfile = enkf;
	}
	
	public void setPrivateKey(PGPPrivateKey k){
		prikey = k;
	}
	
	public PGPPrivateKey getPrivateKey(){
		return prikey;
	}
	
	public void setPublicKey(PGPPublicKey k){
		pubkey = k;
	}
	
	public PGPPublicKey getPublicKey(){
		return pubkey;
	}
	
	
	public void readPublicKeyFromFile() throws FileNotFoundException, IOException{
	
		PGPPublicKeyRing pkRing = new PGPPublicKeyRing(PGPUtil.getDecoderStream(new FileInputStream(pubkeyfile)), new BcKeyFingerprintCalculator());
		Iterator pkIt = pkRing.getPublicKeys();
		while (pkIt.hasNext()) {
			PGPPublicKey key = (PGPPublicKey) pkIt.next();
			System.out.println("Encryption key = " + key.isEncryptionKey() + ", Master key = " + 
					key.isMasterKey());
			if (key.isEncryptionKey()){
				pubkey = key;
				
			}
		}
        
		
        
	}
	
	/**
	 * Generiert ein Schlüsselpaar und speichert 
	 * dieses in zwei Dateien
	 */
	public void generateKeyPair() {
		KeyGenerator kgen = new KeyGenerator();
		kgen.setPrivateKeyFile("private.skr");
		kgen.setPublicKeyFile("public.asc");
		kgen.setPass("DiesIstEinePassPhrase".toCharArray());
		kgen.setEmail("example@mail.com");
		try {
			kgen.generateKeyPair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void encryptFile(String fileName, String keyFile){
		encryptFile(new File(fileName), keyFile);
	}
	
	public void encryptFile(File f, String keyFile){
		try {
			PGPEncrypter enc = new PGPEncrypter();
			enc.setPublicKeyFile(keyFile);
			enc.encrypt(f.getAbsolutePath());
		} catch (NoSuchProviderException | IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] ar){
	}


}