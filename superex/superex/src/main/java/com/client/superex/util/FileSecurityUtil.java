package com.client.superex.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class FileSecurityUtil {

    private static final String SECRET_KEY = "1234567890123456";

    // 2MB validation
    public static void validateSize(long size) throws Exception {
        if (size > (2 * 1024 * 1024)) {
            throw new Exception("File exceeds 2MB limit");
        }
    }

    // Virus scan simulation
    public static void virusScan(byte[] fileBytes) throws Exception {
        String content = new String(fileBytes);
        if (content.contains("virus") || content.contains("malware")) {
            throw new Exception("File contains suspicious content!");
        }
    }

    // Image compression
    public static byte[] compressImage(byte[] imageBytes) throws Exception {

        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bis);

        if (image == null) return imageBytes;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);

        return bos.toByteArray();
    }

    // Encryption
    public static byte[] encrypt(byte[] data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // Decryption
    public static byte[] decrypt(byte[] data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}