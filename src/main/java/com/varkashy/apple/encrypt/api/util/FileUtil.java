package com.varkashy.apple.encrypt.api.util;

import java.io.*;

/**
 * Utility for reading and writing from file.
 */
public class FileUtil {

    /**
     * Write to file utility method
     * @param data data to write
     * @param path file path
     * @throws IOException Exception thrown for IO error
     */
    public static void writeToFile(byte[] data, String path) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(data);
        }
    }

    /**
     * Reads from file path
     * @param path file path to read from
     * @return empty array if file not found
     */
    public static byte[] readFromFile(String path){
        File file = new File(path);

        byte [] fileData = new byte[(int) file.length()];

        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(fileData);
        } catch (Exception e) {
            return new byte[]{};
        }
        return fileData;
    }
}
