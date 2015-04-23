package de.szut.SmartGadgetBar.PGP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.PacketTags;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;

public class PGPEncrypter {

	private PGPPublicKey pukey;
	private String pukeyfile;
	
	public void encrypt(String fileName) throws IOException, NoSuchProviderException, PGPException{
	
				
				
		Security.addProvider(new BouncyCastleProvider());
		
		OutputStream out = new ArmoredOutputStream(new FileOutputStream(new File(fileName+".pgp")));
		
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
		
		PGPUtil.writeFileToLiteralData(
				comData.open(bOut),
				PGPLiteralData.BINARY,
				new File(fileName) );
		
		comData.close();
		
		BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(PGPEncryptedData.TRIPLE_DES);
		dataEncryptor.setWithIntegrityPacket(true);
		dataEncryptor.setSecureRandom(new SecureRandom());
		
		PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptor);
		encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(pukey));
				 
		byte[] bytes = bOut.toByteArray();
		OutputStream cOut = encryptedDataGenerator.open(out, bytes.length);
		cOut.write(bytes);
		cOut.close();
		out.close();
	}
		
	public void setPublicKeyFile(String keyFile){
		
		pukeyfile = keyFile;
		try {
			pukey = readKeyfromFile(new FileInputStream(new File(pukeyfile)));
		} catch (IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private PGPPublicKey readKeyfromFile(InputStream in) throws IOException, PGPException{
		PGPPublicKeyRing pkRing = new PGPPublicKeyRing(PGPUtil.getDecoderStream(in), new BcKeyFingerprintCalculator());
		Iterator pkIt = pkRing.getPublicKeys();
		while (pkIt.hasNext()) {
			PGPPublicKey key = (PGPPublicKey) pkIt.next();
			System.out.println("Encryption key = " + key.isEncryptionKey() + ", Master key = " + 
					key.isMasterKey());
			if (key.isEncryptionKey())
				return key;
        	}
        
        return null;

	}
	
	public static void main(String[] ar)  throws IOException, NoSuchAlgorithmException, NoSuchProviderException, PGPException{
		
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator  keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey k = keyPair.getPublic();
        System.out.println(((Base64.toBase64String(new PGPEncrypter().readKeyfromFile(new FileInputStream(new File("tempKey.asc"))).getPublicKeyPacket().getEncodedContents()))));
		
      
	}
	
}
