package com.svi.app.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

public class CommonMethods {

	private static final Logger LOG = LoggerFactory.getLogger(CommonMethods.class);

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

	public static String compressionAndUploadFile(InputStream file, String name) {
		Path path = Paths.get(System.getProperty("user.home"), "ine", "files");

		String finalName = "1" + name;
		try {
			if (!name.endsWith(".pdf")) {
				Files.copy(file, path.resolve(name));
				File input = new File(path.toAbsolutePath().toString() + "/" + name);
				File output = new File(path.toAbsolutePath().toString() + "/" + finalName);
				Thumbnails.of(input).scale(1).outputQuality(0.5).toFile(output);
				Files.deleteIfExists(path.resolve(name));
			} else {
				Files.copy(file, path.resolve(finalName));
			}
			GCSManage.uploadObject(finalName);
			Files.deleteIfExists(path.resolve(finalName));
			return finalName;
		} catch (IOException e) {
			LOG.error("compressionAndUploadFile", e);
			return "";
		}
	}
}
