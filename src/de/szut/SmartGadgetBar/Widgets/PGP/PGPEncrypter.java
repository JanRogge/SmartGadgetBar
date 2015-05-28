package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Iterator;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;

/**
 * Klasse, die die Funktionalitaet zum verschluesseln bereit stellt Sollte ueber
 * die Klasse PGP benutzt werden
 * 
 * @author Fabian Brinkmann
 */
public class PGPEncrypter {

	private PGPPublicKey pukey;
	private String pukeyfile;

	/**
	 * Methode zum Verschluesseln von Dateien
	 * 
	 * @param fileName
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws PGPException
	 */
	public void encrypt(String fileName) throws IOException,
			NoSuchProviderException, PGPException {

		Security.addProvider(new BouncyCastleProvider());

		OutputStream out = new ArmoredOutputStream(new FileOutputStream(
				new File(fileName + ".pgp")));

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(
				PGPCompressedData.ZIP);

		PGPUtil.writeFileToLiteralData(comData.open(bOut),
				PGPLiteralData.BINARY, new File(fileName));

		comData.close();

		BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(
				PGPEncryptedData.TRIPLE_DES);
		dataEncryptor.setWithIntegrityPacket(true);
		dataEncryptor.setSecureRandom(new SecureRandom());

		PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
				dataEncryptor);
		encryptedDataGenerator
				.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(pukey));

		byte[] bytes = bOut.toByteArray();
		OutputStream cOut = encryptedDataGenerator.open(out, bytes.length);
		cOut.write(bytes);
		cOut.close();
		out.close();
	}

	public void setPublicKeyFile(String keyFile) {

		pukeyfile = keyFile;
		try {
			pukey = readKeyfromFile(new FileInputStream(new File(pukeyfile)));
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private PGPPublicKey readKeyfromFile(InputStream in) throws IOException,
			PGPException {
		PGPPublicKeyRing pkRing = new PGPPublicKeyRing(
				PGPUtil.getDecoderStream(in), new BcKeyFingerprintCalculator());
		Iterator<PGPPublicKey> pkIt = pkRing.getPublicKeys();
		while (pkIt.hasNext()) {
			PGPPublicKey key = (PGPPublicKey) pkIt.next();
			if (key.isEncryptionKey())
				return key;
		}

		return null;

	}

}
