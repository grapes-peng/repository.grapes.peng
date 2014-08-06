import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import net.easipay.wgq.ep.dto.LoginInfo;
import net.easipay.wgq.ep.dto.ReturnInfo;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author pengtao_test 
 * 
 * 		         第一步执行getKey，从打印出的日志分别copy出module 和exponent
 * 
 *         第二部执行encryptLoginInfo,用第一步得到值作为参数,copy 出执行结果日志中加密的密码字符串
 * 
 *         第三部执行lognin用第二部的结果作为参数
 */
public class RESTClient {

	private static final String baseAddress = "http://192.168.6.33:8001";

	@Test
	public void getkey() {

		WebClient client = WebClient.create(baseAddress);
		client.path("WGQ/webservice/userinfo/getkey/cc")
				.accept("application/xml").type("application/xml");
		ReturnInfo info = client.get(ReturnInfo.class);
		System.out.println(info.getRemark());
	}

	@Test
	public void encryptLoginInfo(String pwd, String module, String exponent)
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

		System.out.println(enPwd);
	}

	@Test
	public void login() {
		WebClient client = WebClient.create(baseAddress);
		client.path("WGQ/webservice/userinfo/login/").accept("application/xml")
				.type("application/xml");
		LoginInfo logInfo = new LoginInfo();
		logInfo.setUserName("cc");
		logInfo.setPwd("qo86Pyz1XOdPqEIrJVJsEu2O8x+2BNEOTIVO4dlPwqKh78O63DQM1/06Hq1sjbZ598phVBcvbj/Ni+PbaVrIgOd3f5OrTjjBVSJBK+5RBAb9zAjMwdynGFFRsZ5QcMYUWbISWUGqPtGxWgIeittpXUyjJ7TBJM4TCTZVnt3rWfE=");
		ReturnInfo info = client.post(logInfo, ReturnInfo.class);
		System.out.println(info.getRemark());
	}
}
