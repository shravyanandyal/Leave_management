package com.lms.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for handling file uploads.
 */
public class FileUploadUtil {

    private static final String UPLOAD_DIR = "uploads/";

    /**
     * Saves the uploaded file bytes to the file system.
     *
     * @param fileBytes     the byte array of the uploaded file
     * @param originalName  the original file name
     * @return String path of the stored file
     */
    public static String saveFile(byte[] fileBytes, String originalName) throws IOException {
        // Create uploads directory if not exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Define target path
        Path targetPath = Paths.get(UPLOAD_DIR + System.currentTimeMillis() + "_" + originalName);

        // Write file to system
        Files.write(targetPath, fileBytes);

        return targetPath.toString();
    }

    /**
     * Deletes a file from the file system.
     *
     * @param filePath path to delete
     */
    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
