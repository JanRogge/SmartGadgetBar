package de.szut.SmartGadgetBar.Widgets.PGP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;

/**
 * Klasse zum generieren von Keys
 *Sollte ueber die Klasse PGP benutzt werden
 */
public class KeyGenerator {

	private String publicKeyFile;
	private String privateKeyFile;
	private char[] pass;
	private String email;
	private int keySize;

	
	
	/**
	 * Generiert die Keys und speicher sie im verzeichnis
	 *\keys\
	 * @throws Exception
	 */
	public void generateKeyPair() throws Exception {
		FileWriter w = new FileWriter(new File(publicKeyFile));
		w = new FileWriter(new File(privateKeyFile));
		PGPKeyRingGenerator krgen = generateKeyRingGenerator(email, pass);

		// Generate public key ring, dump to file.
		PGPPublicKeyRing pkr = krgen.generatePublicKeyRing();
		ArmoredOutputStream pubout = new ArmoredOutputStream(
				new BufferedOutputStream(new FileOutputStream(publicKeyFile)));
		pkr.encode(pubout);
		pubout.close();

		// Generate private key, dump to file.
		PGPSecretKeyRing skr = krgen.generateSecretKeyRing();
		BufferedOutputStream secout = new BufferedOutputStream(
				new FileOutputStream(privateKeyFile));
		skr.encode(secout);
		secout.close();
	}

	private PGPKeyRingGenerator generateKeyRingGenerator(String id, char[] pass)
			throws Exception {
		return generateKeyRingGenerator(id, pass, 0xc0);
	}

	private PGPKeyRingGenerator generateKeyRingGenerator(String id,
			char[] pass, int s2kcount) throws Exception {
		// This object generates individual key-pairs.
		RSAKeyPairGenerator kpg = new RSAKeyPairGenerator();

		kpg.init(new RSAKeyGenerationParameters(BigInteger.valueOf(0x10001),
				new SecureRandom(), this.keySize, 12));

		PGPKeyPair rsakp_sign = new BcPGPKeyPair(PGPPublicKey.RSA_SIGN,
				kpg.generateKeyPair(), new Date());
		PGPKeyPair rsakp_enc = new BcPGPKeyPair(PGPPublicKey.RSA_ENCRYPT,
				kpg.generateKeyPair(), new Date());

		PGPSignatureSubpacketGenerator signhashgen = new PGPSignatureSubpacketGenerator();

		signhashgen.setKeyFlags(false, KeyFlags.SIGN_DATA
				| KeyFlags.CERTIFY_OTHER);
		signhashgen.setPreferredSymmetricAlgorithms(false, new int[] {
				SymmetricKeyAlgorithmTags.AES_256,
				SymmetricKeyAlgorithmTags.AES_192,
				SymmetricKeyAlgorithmTags.AES_128 });
		signhashgen.setPreferredHashAlgorithms(false, new int[] {
				HashAlgorithmTags.SHA256, HashAlgorithmTags.SHA1,
				HashAlgorithmTags.SHA384, HashAlgorithmTags.SHA512,
				HashAlgorithmTags.SHA224, });
		signhashgen.setFeature(false, Features.FEATURE_MODIFICATION_DETECTION);

		PGPSignatureSubpacketGenerator enchashgen = new PGPSignatureSubpacketGenerator();
		enchashgen.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS
				| KeyFlags.ENCRYPT_STORAGE);

		// Objects used to encrypt the secret key.
		PGPDigestCalculator sha1Calc = new BcPGPDigestCalculatorProvider()
				.get(HashAlgorithmTags.SHA1);
		PGPDigestCalculator sha256Calc = new BcPGPDigestCalculatorProvider()
				.get(HashAlgorithmTags.SHA256);

		// bcpg 1.48 exposes this API that includes s2kcount. Earlier versions
		// use a default of 0x60.
		PBESecretKeyEncryptor pske = (new BcPBESecretKeyEncryptorBuilder(
				PGPEncryptedData.AES_256, sha256Calc, s2kcount)).build(pass);

		// Finally, create the keyring itself. The constructor takes parameters
		// that allow it to generate the self signature.
		PGPKeyRingGenerator keyRingGen = new PGPKeyRingGenerator(
				PGPSignature.POSITIVE_CERTIFICATION, rsakp_sign, id, sha1Calc,
				signhashgen.generate(), null, new BcPGPContentSignerBuilder(
						rsakp_sign.getPublicKey().getAlgorithm(),
						HashAlgorithmTags.SHA1), pske);

		// Add our encryption subkey, together with its signature.
		keyRingGen.addSubKey(rsakp_enc, enchashgen.generate(), null);
		return keyRingGen;
	}

	public String getPublicKeyFile() {
		return publicKeyFile;
	}

	public void setPublicKeyFile(String publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public char[] getPass() {
		return pass;
	}

	public void setPass(char[] pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setKeySize(String keysize) {
		try{
			
			this.keySize = Integer.parseInt(keysize);
		}catch(Exception e){
			this.keySize=2048;
		}
		
	}
}