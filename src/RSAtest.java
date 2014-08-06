import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAtest {

	@Test
	public void test() throws Exception {
		String s = encryptLoginInfo(
				"111111",
				"jLd5ZablV+hEuV3aub9wSIw36UdXIPoYbUMq6FKLmh/OB2Vjw6hAFbiflyxcOjCla7LoQFGr2J/txI0Ps73ltpH+9u5thBfCNwaiHAX+zLigq2JuPLFQO+WxlZKCKxRsGN2t1seYYogsDhQm1dj9zC9sIUe2Q7echPT9Z4JEB9E=",
				"AQAB");
		System.out.println(s);
	}

	public String encryptLoginInfo(String pwd, String module, String exponent)
			throws Exception {
		byte[] modulusBytes = new BASE64Decoder().decodeBuffer(module);
		byte[] exponentBytes = new BASE64Decoder().decodeBuffer(exponent);
		BigInteger integreModulu = new BigInteger(1, modulusBytes);
		BigInteger integreExponent = new BigInteger(1, exponentBytes);

		RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(integreModulu,
				integreExponent);
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PublicKey pubKey = fact.generatePublic(rsaPubKey);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		byte[] cipherData = cipher.doFinal(pwd.getBytes());
		String enPwd = new BASE64Encoder().encodeBuffer(cipherData);

		return enPwd;
	}
}
