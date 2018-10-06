package com.snpm.util;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.internal.Base64Encoder;

import com.snpm.test.TestBase;
import org.apache.logging.log4j.Logger;

public class AESCrypt 
{
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
	private static Logger logger = TestBase.logger;
    
    public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new Base64Encoder().encode(encryptedByteValue);
        return encryptedValue64;             
    }
    
    public static String decrypt(String value) 
    {
        final String methodName = "decrypt";
        logger.entry( methodName);
        logger.debug(methodName, "Decrypting password :"+value);
    	Key key;
        String decryptedValue = null;
		try {
			key = generateKey();
	        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
	        
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte [] decryptedValue64 = new Base64Encoder().decode(value);
	        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
	        decryptedValue = new String(decryptedByteValue,"utf-8");
		} catch (Exception e) {
			logger.fatal( methodName, e.getMessage());
		}
		 logger.exit( methodName);
        return decryptedValue;
                
    }
    
    private static Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(AESCrypt.KEY.getBytes(),AESCrypt.ALGORITHM);
        return key;
    }
}