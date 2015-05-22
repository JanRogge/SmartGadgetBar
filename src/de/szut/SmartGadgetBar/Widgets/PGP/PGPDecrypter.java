package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.util.Iterator;

import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;

/**
 * Die Klasse PGPDecrypter stellt die Funktionalitaet
 * fuer das entschluesseln bereit
 * Sollte ueber die Klasse PGP benutzt werden
 */
public class PGPDecrypter {
	
	/**
	 * Methode zum entschluesseln von Dateien
	 * @param in
	 * @param keyIn
	 * @param outfile
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(InputStream in, InputStream keyIn,
			String outfile, char[] passwd) throws Exception {
		in = PGPUtil.getDecoderStream(in);
		
		try {
			
			PGPObjectFactory pgpF = new PGPObjectFactory(in,
					new BcKeyFingerprintCalculator());
			PGPEncryptedDataList enc;
			Object o = pgpF.nextObject();

			if (o instanceof PGPEncryptedDataList) {
				enc = (PGPEncryptedDataList) o;
			} else {
				enc = (PGPEncryptedDataList) pgpF.nextObject();
			}

			Iterator<?> it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;
			
			while (sKey == null && it.hasNext()) {
				pbe = (PGPPublicKeyEncryptedData) it.next();
				sKey = findSecretKey(keyIn, pbe.getKeyID(), passwd);
			}
			
			if (sKey == null) {
				throw new IllegalArgumentException(
						"secret key for message not found.");
			}
			
			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			PGPObjectFactory plainFact = new PGPObjectFactory(clear,
					new BcKeyFingerprintCalculator());
			Object message = plainFact.nextObject();
			
			if (message instanceof PGPCompressedData) {
				PGPCompressedData cData = (PGPCompressedData) message;
				PGPObjectFactory pgpFact = new PGPObjectFactory(
						cData.getDataStream(), new BcKeyFingerprintCalculator());
				message = pgpFact.nextObject();
			}
			
			FileOutputStream baos = new FileOutputStream(new File(outfile));
			if (message instanceof PGPLiteralData) {
				PGPLiteralData ld = (PGPLiteralData) message;
				InputStream unc = ld.getInputStream();
				int ch;
				while ((ch = unc.read()) >= 0) {
					baos.write(ch);
				}

			} else if (message instanceof PGPOnePassSignatureList) {
				baos.close();
				throw new PGPException(
						"encrypted message contains a signed message - not literal data.");
			} else {
				baos.close();
				throw new PGPException(
						"message is not a simple encrypted file - type unknown.");
			}
			if (pbe.isIntegrityProtected()) {
				if (!pbe.verify()) {
					baos.close();
					throw new PGPException("message failed integrity check");
				} else {
				}
			} else {
			}
			baos.close();
			return baos.toString();
		} catch (PGPException e) {
			if (e.getUnderlyingException() != null) {
				e.getUnderlyingException().printStackTrace();
			}
		}
		return null;
	}

	private static PGPPrivateKey findSecretKey(InputStream keyIn, long keyID,
			char[] pass) throws IOException, PGPException,
			NoSuchProviderException {
		PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
				PGPUtil.getDecoderStream(keyIn),
				new BcKeyFingerprintCalculator());
		
		PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);
		
		if (pgpSecKey == null) {
			return null;
		}
		BcPBESecretKeyDecryptorBuilder skcb = new BcPBESecretKeyDecryptorBuilder(
				new BcPGPDigestCalculatorProvider());
		
		PBESecretKeyDecryptor pskd = skcb.build(pass);
		return pgpSecKey.extractPrivateKey(pskd);

	}

	public byte[] readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[4096];
		int numRead = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		while ((numRead = fis.read(buf)) > 0) {
			baos.write(buf, 0, numRead);
		}
		
		fis.close();
		byte[] returnVal = baos.toByteArray();
		baos.close();
		
		return returnVal;
	}

}