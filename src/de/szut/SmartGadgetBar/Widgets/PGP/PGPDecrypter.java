package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.ByteArrayInputStream;
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

public class PGPDecrypter {

	
	

	

	
	/**
	 * 
	 * @param in
	 * @param keyIn
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(InputStream in, InputStream keyIn, String outfile, char[] passwd) throws Exception {
        in = PGPUtil.getDecoderStream(in);
        try {
                PGPObjectFactory pgpF = new PGPObjectFactory(in, new BcKeyFingerprintCalculator());
                PGPEncryptedDataList enc;
                Object o = pgpF.nextObject();
                
                // the first object might be a PGP marker packet.
                
                if (o instanceof PGPEncryptedDataList) {
                        enc = (PGPEncryptedDataList) o;
                } else {
                        enc = (PGPEncryptedDataList) pgpF.nextObject();
                }
                
                // find the secret key
                
                Iterator it = enc.getEncryptedDataObjects();
                PGPPrivateKey sKey = null;
                PGPPublicKeyEncryptedData pbe = null;
                while (sKey == null && it.hasNext()) {
                        pbe = (PGPPublicKeyEncryptedData) it.next();
                        sKey = findSecretKey(keyIn, pbe.getKeyID(), passwd);
                }
                if (sKey == null) {
                       throw new IllegalArgumentException("secret key for message not found.");
                }
                InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
                PGPObjectFactory plainFact = new PGPObjectFactory(clear, new BcKeyFingerprintCalculator());
                Object message = plainFact.nextObject();
                if (message instanceof PGPCompressedData) {
                        PGPCompressedData cData = (PGPCompressedData) message;
                        PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), new BcKeyFingerprintCalculator());
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
                        throw new PGPException("encrypted message contains a signed message - not literal data.");
                } else {
                        throw new PGPException("message is not a simple encrypted file - type unknown.");
                }
                if (pbe.isIntegrityProtected()) {
                        if (!pbe.verify()) {
                        	throw new PGPException("message failed integrity check");
                        } else {
                        }
                } else {
                }
                return baos.toString();
        } catch (PGPException e) {
                if (e.getUnderlyingException() != null) {
                        e.getUnderlyingException().printStackTrace();
                }
        }
        return null;
}

	//
	// Private class method findSecretKey
	//
	private static PGPPrivateKey findSecretKey(InputStream keyIn, long keyID,
			char[] pass) throws IOException, PGPException,
			NoSuchProviderException {
		PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(keyIn), new BcKeyFingerprintCalculator());
		PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);
		if (pgpSecKey == null) {
			return null;
		}
		BcPBESecretKeyDecryptorBuilder skcb= new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider());
		PBESecretKeyDecryptor pskd = skcb.build(pass);
		return pgpSecKey.extractPrivateKey(pskd);

	}

	//
	// Public class method readFile
	//
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

	//
	// Public main method
	//
	

}