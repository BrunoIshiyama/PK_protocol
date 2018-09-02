import java.io.IOException;
import java.io.InputStream;

import decryptor.Decryptor;
import encryptor.Encryptor;

public class Tester {
	public static void main(String[] args) {
		long primeP = 17;
		long primeQ = 19;
		long keyE = 7;
		Decryptor dec = new Decryptor(primeP, primeQ, keyE);
		Encryptor enc = new Encryptor(primeP*primeQ, keyE);
		InputStream is = enc.encrypt("Hello World!");
		
		try {
			byte[] info;
			info = new byte[is.available()];
			is.read(info);
			String s = new String(info);
			System.out.println("Encrypted Message: "+s);
//			System.out.println("Decrypted Message: "+dec.decrypt(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
