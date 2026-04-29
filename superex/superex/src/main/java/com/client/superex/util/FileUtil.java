package com.client.superex.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    // 👉 project root (where pom.xml exists)
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    // 👉 resources folder paths
    private static final String PHOTO_DIR =
            PROJECT_DIR + "/src/main/resources/photo/";

    private static final String AADHAAR_DIR =
            PROJECT_DIR + "/src/main/resources/aadhar/";

    public static String saveFile(MultipartFile file, String type, String userFName) throws Exception {

        String dir = type.equals("photo") ? PHOTO_DIR : AADHAAR_DIR;

        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + userFName;
        String filePath = dir + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(file.getBytes());
        }

        // 🔍 debug log
        System.out.println("Saved at: " + new File(filePath).getAbsolutePath());

        return filePath;
    }

    public static String convertToBase64(MultipartFile file) throws Exception {
        return Base64.getEncoder().encodeToString(file.getBytes());
    }
}