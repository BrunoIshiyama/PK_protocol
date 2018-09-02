package decryptor;

import java.io.InputStream;
import java.math.BigDecimal;

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
			s  = s.trim();
			String[] eChars = s.split(",");
			long[] chars = new long[eChars.length];
			for(int i = 0;i<eChars.length;i++) {
				if(eChars[i].isEmpty()) continue;
				long charValue = new Long(eChars[i]);
				BigDecimal bd = new BigDecimal(charValue);
				bd = bd.pow((int)dValue);
				long g = bd.divideAndRemainder(new BigDecimal(prod))[1].longValue();
				chars[i] = g;
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
