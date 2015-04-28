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

	
	
	public PGP(){
		Security.addProvider(new BouncyCastleProvider());
		generateKeyPair();
	}
	

	
	
	
	
	
	/**
	 * Generiert ein Schlüsselpaar und speichert 
	 * dieses in zwei Dateien
	 */
	public void generateKeyPair() {
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
	
	public void encryptFile(String fileName, String keyFile){
		encryptFile(new File(fileName), keyFile);
	}
	
	public void encryptFile(File f, String keyFile){
		try {
			PGPEncrypter enc = new PGPEncrypter();
			enc.setPublicKeyFile(keyFile);
			enc.encrypt(f.getAbsolutePath());
		} catch (NoSuchProviderException | IOException | PGPException e) {
			// 
			e.printStackTrace();
		}
	}
	
	public void decryptFile(String encryptFileName, String prikeyFile){
		try{
		FileInputStream inp = new FileInputStream(new File(encryptFileName));
		String msg = PGPDecrypter.decrypt(inp, new FileInputStream(new File(prikeyFile)), "ent.txt", "DiesIstEinePassPhrase".toCharArray());
		}catch (Exception e){
			e.printStackTrace();
		}

	
	}

}