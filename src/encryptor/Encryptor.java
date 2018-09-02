package encryptor;

import java.math.BigDecimal;

/*
 * This class is responsible to Encrypt messages 
 */
		
public class Encryptor {
	private long publicKeyN;
	private long publicKeyE;
	private String msg;
	
	private long[] convertMessage(String msg) {
		return null;
	}
	
	public long[] encrypt(long[] msg) {
		long[] eMsg = new long[msg.length];
		for(int i = 0 ; i<eMsg.length ; i++) {
			eMsg[i] = (long) ((Math.pow((double)msg[i],publicKeyE))%publicKeyN);
		}
		return  null;
	}
	
}
