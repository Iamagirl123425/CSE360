package application;
import java.util.ArrayList;

public class EncryptDecrypt {
	public EncryptDecrypt() {
		
	}
	public int[] encrypt(String inputString) {			//encrypt. I'M NOT REVEALING ENCRYPTION LOGIC FOR SAKE OF SECURITY
		int[] codes = new int[inputString.length()];
		for (int i = 0 ; i < inputString.length(); i++) {
			char character = inputString.charAt(i);
			int destinationInt = 255 - (int)character;
			codes[i] = destinationInt;
		}
		return codes;
	}
	public int[] decrypt(int[] encryptedCodes){				//decrypt. YOU GUESSED IT
		int[] codeASCII = new int[encryptedCodes.length];
		for(int i = 0; i < encryptedCodes.length; i++) {
			int destinationInt = 255 - encryptedCodes[i];
			codeASCII[i] = destinationInt;
		}
		return codeASCII;
	}
}
