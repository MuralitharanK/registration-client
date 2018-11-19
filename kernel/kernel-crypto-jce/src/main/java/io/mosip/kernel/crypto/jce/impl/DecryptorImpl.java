/*
 * 
 * 
 * 
 * 
 * 
 */
package io.mosip.kernel.crypto.jce.impl;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.mosip.kernel.core.crypto.spi.Decryptor;
import io.mosip.kernel.core.exception.NoSuchAlgorithmException;
import io.mosip.kernel.crypto.jce.constant.SecurityExceptionCodeConstant;
import io.mosip.kernel.crypto.jce.constant.SecurityMethod;
import io.mosip.kernel.crypto.jce.processor.AESProcessor;
import io.mosip.kernel.crypto.jce.processor.RSAProcessor;
import io.mosip.kernel.crypto.jce.util.CryptoUtils;

/**
 * Factory class for Mosip Decryptor
 * 
 * @author Urvil Joshi
 * @since 1.0.0
 */
@Component
public class DecryptorImpl implements Decryptor<PrivateKey, PublicKey, SecretKey, SecurityMethod> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.mosip.kernel.core.spi.security.Decryptor#asymmetricPrivateDecrypt(
	 * java. lang.Object, byte[], java.lang.Object)
	 */
	@Override
	public byte[] asymmetricPrivateDecrypt(PrivateKey privateKey, byte[] data,
			SecurityMethod mosipSecurityMethod) {
		CryptoUtils.checkMethod(mosipSecurityMethod);
		if (mosipSecurityMethod == SecurityMethod.RSA_WITH_PKCS1PADDING) {
			return RSAProcessor.rsaWithPKCS1Padding(privateKey, data, Cipher.DECRYPT_MODE);
		} else {
			throw new NoSuchAlgorithmException(
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorCode(),
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.mosip.kernel.core.spi.security.Decryptor#asymmetricPublicDecrypt(java.
	 * lang .Object, byte[], java.lang.Object)
	 */
	@Override
	public byte[] asymmetricPublicDecrypt(PublicKey publicKey, byte[] data, SecurityMethod mosipSecurityMethod) {
		CryptoUtils.checkMethod(mosipSecurityMethod);
		if (mosipSecurityMethod == SecurityMethod.RSA_WITH_PKCS1PADDING) {
			return RSAProcessor.rsaWithPKCS1Padding(publicKey, data, Cipher.DECRYPT_MODE);
		} else {
			throw new NoSuchAlgorithmException(
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorCode(),
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.mosip.kernel.core.spi.security.Decryptor#symmetricDecrypt(java.lang.
	 * Object, byte[], java.lang.Object)
	 */
	@Override
	public byte[] symmetricDecrypt(SecretKey key, byte[] data, SecurityMethod mosipSecurityMethod) {
		CryptoUtils.checkMethod(mosipSecurityMethod);
		if (mosipSecurityMethod == SecurityMethod.AES_WITH_CBC_AND_PKCS5PADDING) {
			return AESProcessor.aesWithCBCandPKCS5Padding(key, data, Cipher.DECRYPT_MODE);
		} else {
			throw new NoSuchAlgorithmException(
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorCode(),
					SecurityExceptionCodeConstant.MOSIP_NO_SUCH_ALGORITHM_EXCEPTION.getErrorMessage());
		}
	}

}
