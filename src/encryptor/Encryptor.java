package encryptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/*
 * This class is responsible to Encrypt messages 
 */
		
public class Encryptor {
	private long publicKeyN;
	private long publicKeyE;
	
	private long[] convertMessage(String msg) {
		long[] convMsg = new long[msg.length()];
		for(int i = 0;i<convMsg.length;i++) {
			convMsg[i]= new Integer(msg.charAt(i));
		}
		return convMsg;
	}
	
	public InputStream encrypt(String msg) {
		long[] convMsg = convertMessage(msg);
		long[] eMsg = new long[convMsg.length];
		for(int i = 0 ; i<eMsg.length ; i++) {
			eMsg[i] = (long) ((Math.pow((double)convMsg[i],publicKeyE))%publicKeyN);
		}
		StringBuilder sendString = new StringBuilder();
		sendString.append(eMsg[0]);
		for(int i = 1;i<eMsg.length;i++) {
			sendString.append(","+eMsg[i]);
		}
		return new ByteArrayInputStream(sendString.toString().getBytes());
	}
	
}
