package decryptor;

import java.io.InputStream;

public class Decryptor {
	private long primeP, primeQ, primeE;
	private long prod;
	private long dValue;
	private Thread pinCalc;
	public Decryptor(long primeP, long primeQ, long keyE) {
		this.primeE = keyE;
		this.primeP = primeP;
		this.primeQ = primeQ;
		prod = primeP * primeQ;
		pinCalc = new Thread(new Runnable() {
			
			@Override
			public void run() {
				calculateDValue();
			}
		});
		pinCalc.start();
	}

	private void calculateDValue() {
		boolean foundD = false;
		long d = 0;
		while (!foundD) {
			d++;
			foundD = ((primeE * d) % ((primeP - 1) * (primeQ - 1)) == 1);
		}
		dValue = d;
	}

	public String decrypt(InputStream is) {
		try {
			if(pinCalc.isAlive()) {
				pinCalc.join();
			}
			byte[] info = new byte[is.available()];
			is.read(info);
			String s = new String(info);
			String[] eChars = s.split(",");
			long[] chars = new long[eChars.length];
			for(int i = 0;i<eChars.length;i++) {
				long charValue = new Long(eChars[i]);
				chars[i] = (long) ((Math.pow(charValue,dValue))%prod);
			}
			StringBuilder sb = new StringBuilder();
			for(long letter : chars) {
				sb.append((char)(letter));
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
