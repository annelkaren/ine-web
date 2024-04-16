package com.svi.app.util;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;

public class CommonMethods {

	public static String decodePassword(String password) {
		byte[] decodedBytes = Base64.getDecoder().decode(password);
		return new String(decodedBytes, Charset.defaultCharset());
	}

	public static String generatePassword() {
		String newPassword = "";
		Random indexpass = new Random();
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 1; i <= 10; i++) {
			int numeroRandom = indexpass.nextInt(caracteres.length());
			newPassword += caracteres.substring(numeroRandom, numeroRandom + 1);
		}
		return newPassword;
	}
}
